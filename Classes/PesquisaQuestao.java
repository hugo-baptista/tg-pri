package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PesquisaQuestao {
    
    private static Map<String, Integer> QTermFrequency (String query) {
        String[] terms = query.split("[,\\s]+");
        Map<String, Integer> t_freq = new HashMap<>();
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

    private static Map<String, Double> QueryScore (Map<String, Pair<Integer, Integer>> vector,Map<String, Integer> t_freq ,Integer N) {
        Map<String, Double> scores = new HashMap<>();
        Set<String> keySet1 = vector.keySet();
        Set<String> keySet2 = t_freq.keySet();
        Set<String> commonKeys = new HashSet<>(keySet1);
        commonKeys.retainAll(keySet2);
        Map<String, Pair<Integer, Integer>> new_vector = new HashMap<>();
        Double wt = 0.0;
        for (String key: vector.keySet()) {
            Pair<Integer, Integer> pair = vector.get(key);
            int df = pair.getValue();
            if (commonKeys.contains(key)) {
                new_vector.put(key, new Pair<>(t_freq.get(key), df));
            } else {
                new_vector.put(key, new Pair<>(0, df));
            }
        } 
        for (String key: new_vector.keySet()) {
            Pair<Integer, Integer> pair = new_vector.get(key);
            int tf = pair.getKey();
            int df = pair.getValue();
            if (tf == 0 || df == 0) {
                scores.put(key, 0.0);
            } else {
                wt = (1 + Math.log10(tf)) * Math.log10(N/df);
                scores.put(key, wt);
            }
        }
        Double sum = 0.0;
        for (String key: scores.keySet()) {
            Double value = scores.get(key);
            sum += Math.pow(value, 2);
        }
        Map<String, Double> q_scores = new HashMap<>();
        Double normalized = Math.sqrt(sum);
        for (String key: scores.keySet()) {
            Double value = scores.get(key);
            Double new_value = value/normalized;
            q_scores.put(key, new_value);
        }
        return q_scores;
    }

    private static Map<String, Double> DocumentScore (Map<String, Pair<Integer, Integer>> vector, Integer N) {
        Double wt = 0.0;
        Map<String, Double> scores = new HashMap<>();
        for (String key: vector.keySet()) {
            Pair<Integer, Integer> pair = vector.get(key);
            int tf = pair.getKey();
            int df = pair.getValue();
            if (tf == 0 || df == 0) {
                scores.put(key, 0.0);
            } else {
                wt = (1 + Math.log10(tf)) * Math.log10(N/df);
                scores.put(key, wt);
            }
        }
        Double sum = 0.0;
        for (String key: scores.keySet()) {
            Double value = scores.get(key);
            sum += Math.pow(value, 2);
        }
        Map<String, Double> d_scores = new HashMap<>();
        Double normalized = Math.sqrt(sum);
        for (String key: scores.keySet()) {
            Double value = scores.get(key);
            Double new_value = value/normalized;
            d_scores.put(key, new_value);
        }
        return d_scores;
    }

    private static Double similiaridadeFinal (Map<String, Double> d_scores, Map<String, Double> q_scores) {
        Double total = 0.0;
        for (String keyd:d_scores.keySet()) {
            for (String keyq:q_scores.keySet()) {
                if (keyq == keyd) { 
                    Double mult = d_scores.get(keyd) * q_scores.get(keyq);
                    total += mult;
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        Map<String, Pair<Integer, Integer>> vector = new HashMap<>();
        vector.put("lavar", new Pair<>(10, 20));
        vector.put("banana", new Pair<>(7, 17));
        vector.put("carro", new Pair<>(6, 15));
        System.out.println(QTermFrequency("quero lavar lavar o carro, o"));
        System.out.println(DocumentScore(vector, 50));
        System.out.println(QueryScore(vector,QTermFrequency("quero lavar lavar o carro, o"), 50));
        System.out.println(similiaridadeFinal(DocumentScore(vector, 50),QueryScore(vector,QTermFrequency("quero lavar lavar o carro, o"), 50)));
    }
}