package classes;

import java.util.*;

public class ModeloVetorial {

    Dicionario dicionario = new Dicionario();
    HashMapDocs docsHashMap = new HashMapDocs();

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
        dicionario.readFromJsonFile();
        HashMap<String, Double> score = new HashMap<>();
        HashMap<String, Double> q_score = new HashMap<>();
        docsHashMap.readFromJsonFile();
        int N = docsHashMap.size();

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
                    wt = (1 + Math.log10(tf)) * Math.log10((double)N/df);
                    score.put(i, wt);
                }
            } else {
                score.put(i, 0.0);
            }
            
        }
        Double sum = 0.0;
        Double new_sum = 0.0;
        for (String key: score.keySet()) {
            Double value = score.get(key);
            sum += Math.pow(value, 2);
        new_sum = Math.sqrt(sum);
        }
        for (String key: score.keySet()) {
            Double value = score.get(key);
            Double new_value = value/new_sum;
            q_score.put(key, new_value);
        }
        return q_score;
    }
    
    public HashMap<Integer, HashMap<String, Double>> scoresDocs(String query) {
        dicionario.readFromJsonFile();
        docsHashMap.readFromJsonFile();
        int N = docsHashMap.size() - 1; // -1 por causa do DocTeste

        // DocId: term: score
        HashMap<Integer, HashMap<String, Double>> provScores = new HashMap<Integer, HashMap<String, Double>>();

        query = query.toLowerCase();
        String[] terms = query.split("[,\\s]+");

        for (String term : terms) {
            if (dicionario.containsKey(term)) {
                int df = dicionario.getDocFreq(term);
                PostingList postingslist = dicionario.getPostingList(term);
                for (Map.Entry<Integer,ArrayList<Integer>> posting : postingslist.entrySet()) {
                    int docId = posting.getKey();
                    ArrayList<Integer> positions = posting.getValue();
                    int tf = positions.size();

                    // term: score
                    HashMap<String, Double> scoreTerms = new HashMap<>();

                    if (provScores.containsKey(docId)) {
                        scoreTerms = provScores.get(docId);

                        double wt = (1 + Math.log10(tf)) * Math.log10((double)N/df);
                        scoreTerms.put(term, wt);
                        double soma_quadrados = provScores.get(docId).get("soma_quadrados");
                        double sq_atualizada = soma_quadrados + (wt * wt);
                        scoreTerms.put("soma_quadrados", sq_atualizada);
                        provScores.put(docId, scoreTerms);
                    
                        
                    } 
                    else {
                        double wt = (1 + Math.log10(tf)) * Math.log10((double)N/df);
                        scoreTerms.put(term, wt);
                        double soma_quadrados = wt * wt;
                        scoreTerms.put("soma_quadrados", soma_quadrados);
                        provScores.put(docId, scoreTerms);
        
                    }

                }
            
            }
        }
        scoresDocsNormalizer(provScores);
        return provScores;
    }

    public HashMap<Integer, HashMap<String, Double>> scoresDocsNormalizer(HashMap<Integer, HashMap<String, Double>> scores) {
        
        for (Integer docId : scores.keySet()) {
            double soma_quadrados = scores.get(docId).get("soma_quadrados");
            double raiz_sq = Math.sqrt(soma_quadrados);
            // term: score
            HashMap<String, Double> scoreTerms = new HashMap<>();
            scoreTerms = scores.get(docId);
            for (String term : scoreTerms.keySet()) {
                if (!term.equals("soma_quadrados")) {
                    double score = scoreTerms.get(term);
                    double score_norm = score / raiz_sq;
                    scoreTerms.put(term, score_norm);
                } 
            }
            scores.put(docId, scoreTerms);
        }
        return scores;
        
    }

    //HashMap(docid, similiridade final de cada doc com a query)
    public HashMap<Integer, Double> similiaridadeFinal (HashMap<Integer, HashMap<String, Double>> dscores, HashMap<String, Double> qscores) {
        HashMap<Integer, Double> qdscores = new HashMap<>();
        List<Double> s = new ArrayList<>();
        for (Integer key: dscores.keySet()) {
            s.clear();
            HashMap<String, Double> value = dscores.get(key);
            for (String i: value.keySet()) {
                if (qscores.containsKey(i)) {
                    s.add(value.get(i) * qscores.get(i));
                }
            }
            Double sum = 0.0;
            for (Double j: s) {
                sum += j;
            }
            scores.put(key, sum);
        }
        return scores;
    }
}
