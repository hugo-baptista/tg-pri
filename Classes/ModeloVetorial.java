package classes;

import java.util.*;

public class ModeloVetorial {

    Dicionario dicionario = new Dicionario();

    // Mapa com docID: score
    private HashMap<Integer, Double> scores;

    // construtor
    public ModeloVetorial () {
        HashMap<Integer, Double> scores = new HashMap<>();
        this.scores = scores;
    }
    
    // métodos
    public HashMap<String, Integer> queryTermFreq (String query) {
        query = query.toLowerCase();
        String[] terms = query.split("[,\\s]+");
        HashMap<String, Integer> t_freq = new HashMap<>();
        for (String i: terms) {
            if (t_freq.keySet().contains(i)) {
                Integer new_value = t_freq.get(i) + 1;
                t_freq.put(i, new_value);
            } else {
                t_freq.put(i, 1);
            }
        }
        return t_freq;
    }
    //dicionario -> HashMap<String,Pair<Integer,PostingList>>
    public HashMap<String, Double> queryScore (HashMap<String, Integer> t_freq) {
        dicionario.readFromJsonFile("./database/dicionario.json");
        HashMap<String, Double> score = new HashMap<>();
        HashMap<String, Double> q_score = new HashMap<>();
        // Set<String> set1 = t_freq.keySet();
        // Set<String> set2 = dicionario.getTermos();
        // Set<String> commonKeys = new HashSet<>(set1);
        // commonKeys.retainAll(set2);
        Double wt = 0.0;
        for (String i: t_freq.keySet()) {
            if (dicionario.containsKey(i)){
                Pair<Integer, PostingList> pair = dicionario.getPair(i);
                Integer df = pair.getKey();
                Integer tf = t_freq.get(i);
                // Será que ainda temos de ter este if??
                if (tf == 0 || df == 0) {
                    score.put(i, 0.0);
                } else {
                    wt = (1 + Math.log10(tf)) * Math.log10(10/df);
                    score.put(i, wt);
                }
            } else {
                score.put(i, 0.0);
            }
            
        }
        Double sum = 0.0;
        for (String key: score.keySet()) {
            Double value = score.get(key);
            sum += Math.pow(value, 2);
        }
        for (String key: score.keySet()) {
            Double value = score.get(key);
            Double new_value = value/sum;
            q_score.put(key, new_value);
        }
        return q_score;
    }
    
    public HashMap<Integer, HashMap<String, Double>> scoresDocs(String query) {
        dicionario.readFromJsonFile("./database/dicionario.json");

        HashMap<Integer, HashMap<String, Double>> provScores = new HashMap<Integer, HashMap<String, Double>>();
        HashMap<String, Double> scoreTerm = new HashMap<>();

        query = query.toLowerCase();
        String[] terms = query.split("[,\\s]+");

        for (String term : terms) {
            if (dicionario.containsKey(term)) {
                int df = dicionario.getDocFreq(term);
                PostingList postingslist = dicionario.getPostingList(term);
                for (Pair<Integer, ArrayList<Integer>> posting : postingslist.getAllPairs()) {
                    int docId = posting.getKey();
                    ArrayList<Integer> positions = posting.getValue();
                    int tf = positions.size();;

                    if (tf == 0 || df == 0) {
                        scores.put(docId, 0.0);

                    } else {
                        double wt = (1 + Math.log10(tf)) * Math.log10(8/df);
                        System.out.println(wt);
                        scoreTerm.put(term, wt);
                        provScores.put(docId, scoreTerm);
                        // if (provScores.containsKey(docId)) {
                        //     // The docID is already present in the provScores
                            // double currentScore = provScores.get(docId).get(term);
                            // double updatedScore = currentScore + wt;
                        //     scoreTerm.put(term, wt);
                        //     provScores.put(docId, scoreTerm);
                        // } else {
                        //     // The docID is not present in the provScores
                        //     scoreTerm.put(term, wt);
                        //     provScores.put(docId, scoreTerm);
                        // }
                    }

                }
            
            }
        }
        // scores = provScores;
        return provScores;
    }
}
