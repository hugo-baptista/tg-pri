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
        System.out.println(scores.similiaridadeFinal(dscores2, qscore2));

        for (HashEntry<Integer,Double>k: scores.entrySet())


        LinkedHashMap<String, Double> sortedMap = sortHashMapDescending(scores.similiaridadeFinal(dscores2, qscore2));
        
        System.out.println(sortedMap);

        
    }

    private LinkedHashMap<String, Double> sortHashMapDescending(HashMap<String,Double> hashMap) {
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
}
