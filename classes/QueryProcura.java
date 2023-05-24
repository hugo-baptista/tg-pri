package classes;


import java.io.File;
import java.util.*;


public class QueryProcura {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IndexingEngine engine = new IndexingEngine();
        HtmlDownloader htmlDownloader = new HtmlDownloader();
        

        while (true) {
            
            System.out.println("\nPress:");
            System.out.println("q - quit");
            System.out.println("i - index documents in Documentos folder of database");
            System.out.println("l - to perform a web pages extraction (cleaner is also applied and cleaned documents are saved in the Documentos folder)");
            System.out.println("s - to perform a query search");
            System.out.println("d - to delete all the documents in the Documentos folder");
            System.out.println("c - to clean the dictionary (also cleans the HashMapDocs)");

            String userInput = scanner.nextLine();
            
            
            if (userInput.equals("q")) {
                break;
            }
            else if (userInput.equals("s")) {
                
                System.out.print("Enter query: ");
                String query = scanner.nextLine();

                searchDocs(query);

            }
            else if (userInput.equals("i")) {
                engine.indexDocuments();
            }

            else if (userInput.equals("l")) {
                System.out.println("Enter the main URL where the search for the keyword must start:");
                String url = scanner.nextLine();
                System.out.print("Enter the keyword to limit the search for documents: ");
                String keyword = scanner.nextLine();
                htmlDownloader.downloader(url,keyword);

            } 
            
            else if (userInput.equals("d")) {
                deleteFilesInFolder("database\\Documentos");

            } 
            else if (userInput.equals("c")) {
                Dicionario.cleanDicionario();

            } else {
                System.out.println("Invalid choice. Please try again.\n");
            }
                
        }
        scanner.close();
        
    }

    private static LinkedHashMap<String, Double> sortHashMapDescending(HashMap<String,Double> hashMap) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(hashMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private static HashMap<String, Double> scoresFileNames (HashMap<Integer, Double> scores) {
        HashMapDocs docsHashMap = new HashMapDocs();
        docsHashMap.readFromJsonFile();
        HashMap<String, Double> scoresnames = new HashMap<>();
        for (Integer docId : scores.keySet()) {
            if (docsHashMap.containsKey(docId)) {
                String newkey = docsHashMap.getDocName(docId);
                scoresnames.put(newkey, scores.get(docId));
            }
            else {
                scoresnames.put("Documento não localizável", scores.get(docId));
            }
        }
        return scoresnames;
    }

    private static void deleteFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

    private static void searchDocs(String query) {
        ModeloVetorial scores = new ModeloVetorial();
        HashMap<Integer, HashMap<String, Double>> dscores2 = scores.scoresDocs(query);
        System.out.println("\nDocs score:");
        System.out.println(dscores2);

        HashMap<String, Integer> n2 = scores.queryTermFreq(query);
        HashMap<String, Double> qscore2 = scores.queryScore(n2);
        System.out.println("\nQuery score:");
        System.out.println(qscore2);
        

        System.out.println("\nFinal results:");
        HashMap<Integer, Double> finalscores = scores.similiaridadeFinal(dscores2, qscore2);
        System.out.println(finalscores);
        System.out.println("\nFinal results with docnames:");
        System.out.println(scoresFileNames(finalscores));

        LinkedHashMap<String, Double> sortedMap = sortHashMapDescending(scoresFileNames(finalscores));

        System.out.println("\nSorted final results with docnames:");
        System.out.println(sortedMap);
        
        System.out.println("\nBest matches:");
        int i = 0;
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String docname = entry.getKey();
            Double score = entry.getValue();
            if (i < 5 && score > 0.316) {
                System.out.println("Document: " + docname + " (score= " + score + ")\n");
                i += 1;
            }
        }

    }

}
