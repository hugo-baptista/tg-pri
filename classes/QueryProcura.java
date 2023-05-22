package classes;

import java.util.*;

public class QueryProcura {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ModeloVetorial scores = new ModeloVetorial();
        IndexingEngine engine = new IndexingEngine();


        System.out.print("Enter query: ");
        String query = scanner.nextLine();
        scanner.close();

        engine.indexDocuments();

        HashMap<Integer, HashMap<String, Double>> dscores2 = scores.scoresDocs(query);

        HashMap<String, Integer> n2 = scores.queryTermFreq(query);
        HashMap<String, Double> qscore2 = scores.queryScore(n2);

        System.out.println("\nResultados:");
        HashMap<Integer, Double> finalscores = scores.similiaridadeFinal(dscores2, qscore2);
        System.out.println(finalscores);
        System.out.println(scoresFileNames(finalscores));

        


        LinkedHashMap<String, Double> sortedMap = sortHashMapDescending(scoresFileNames(finalscores));

        System.out.println(sortedMap);
        
        int i = 0;
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String docname = entry.getKey();
            Double score = entry.getValue();
            if (i < 5 && score > 0.316) {
                System.out.println("Documento: " + docname + " (score= " + score + ")");
                i += 1;
            }
        }
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
}
