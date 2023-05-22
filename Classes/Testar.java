package classes;

import java.util.*;

public class Testar {

    public static void main(String[] args) {
        // Criação do dicionário
        Dicionario dic = new Dicionario();
        ModeloVetorial scores = new ModeloVetorial();
        IndexingEngine engine = new IndexingEngine();

        // Posting List do termo  "be"
        // PostingList postinglist_be = new PostingList();
        
        // ArrayList<Integer> pos1 = new ArrayList<>(Arrays.asList(7, 18, 33, 72, 86, 231));
        // postinglist_be.add_doc(1, pos1);

        // ArrayList<Integer> pos2 = new ArrayList<>(Arrays.asList(3, 149));
        // postinglist_be.add_doc(2, pos2);

        // ArrayList<Integer> pos4 = new ArrayList<>(Arrays.asList(17, 191, 291, 430, 434));
        // postinglist_be.add_doc(4, pos4);
        
        // ArrayList<Integer> pos5 = new ArrayList<>(Arrays.asList(363, 367));
        // postinglist_be.add_doc(5, pos5);
        
        // dic.add_term("be", postinglist_be);
        // // Posting List do termo  "to"
        // PostingList postinglist_to = new PostingList();

        // ArrayList<Integer> pos6 = new ArrayList<>(Arrays.asList(17, 191, 291));
        // postinglist_to.add_doc(1, pos6);
        
        // ArrayList<Integer> pos7 = new ArrayList<>(Arrays.asList(17, 191, 291));
        // postinglist_to.add_doc(3, pos7);

        // dic.add_term("to", postinglist_to);
        // // Ver o dicionário
        // System.out.println(dic.toString());
        // System.out.println("----------\n");
        // System.out.println(dic.toJSON());

        // dic.saveToJsonFile();

        // Dicionario dic2 = new Dicionario();
        // System.out.println("----------\n");
        // dic2.readFromJsonFile();
        // System.out.println(dic2.toJSON());

        // PostingList postings = new PostingList();
        // postings = (dic2.getPostingList("be"));
        // System.out.println(postings);

        // HashMap<Integer, HashMap<String, Double>> dscores = scores.scoresDocs("be to from");
        // System.out.println(scores.scoresDocs("be to from"));


        // HashMap<String, Integer> n = scores.queryTermFreq("be to from");
        // HashMap<String, Double> qscore = scores.queryScore(n);
        // System.out.println(scores.queryScore(n));

        // System.out.println(scores.similiaridadeFinal(dscores, qscore));

        // ------------------------------------------------------------------------------------------
        System.out.println("\nTestar indexação\n");
        engine.indexDocuments();

        HashMap<Integer, HashMap<String, Double>> dscores2 = scores.scoresDocs("Music to fight");
        System.out.println(scores.scoresDocs("Music to fight"));

        HashMap<String, Integer> n2 = scores.queryTermFreq("Music to fight");
        HashMap<String, Double> qscore2 = scores.queryScore(n2);
        System.out.println(scores.queryScore(n2));

        System.out.println("\nResultados:");
        System.out.println(scores.similiaridadeFinal(dscores2, qscore2));
    }
}
