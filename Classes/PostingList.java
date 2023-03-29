package classes;

import java.util.ArrayList;

import java.util.*;
import javafx.util.Pair;

public class PostingList {
    // Posting List - < DocID, PositionList >
    private ArrayList<Pair<Integer,ArrayList<Integer>>> posting_list;

    // construtores
    public PostingList() {
        ArrayList<Pair<Integer,ArrayList<Integer>>> posting_list = new ArrayList<>();
        this.posting_list = posting_list;
    }

    public PostingList(ArrayList<Pair<Integer,ArrayList<Integer>>> posting_list) {
        this.posting_list = posting_list;
    }

    public PostingList(Integer doc_id, ArrayList<Integer> position_list) {
        Pair<Integer,ArrayList<Integer>> post = new Pair<>(doc_id, position_list);
        ArrayList<Pair<Integer,ArrayList<Integer>>> posting_list = new ArrayList<>(Arrays.asList(post));
        this.posting_list = posting_list;
    }

    // métodos
    public void add_doc(Integer doc_id, ArrayList<Integer> position_list) {
        Pair<Integer,ArrayList<Integer>> new_post = new Pair<>(doc_id, position_list);
        this.posting_list.add(new_post);
    }

    public void add_doc(Pair<Integer, ArrayList<Integer>> new_post) {
        this.posting_list.add(new_post);
    }

    public Integer size() {
        return this.posting_list.size();
    }

    @Override
    public String toString() {
        String posting_list_string = new String();
        for (Pair<Integer,ArrayList<Integer>> document : this.posting_list ) {
            int doc_id = document.getKey();
            ArrayList<Integer> position_list = document.getValue();
            posting_list_string = (posting_list_string + "Document: " + doc_id + " - Positions: " + position_list + "\n");
        }
        return posting_list_string;
    }

    public String toJSON() {
        String json = new String();
        for (Pair<Integer,ArrayList<Integer>> doc : this.posting_list ) {
            json = json + "\t\t{\n";
            int docID = doc.getKey();
            ArrayList<Integer> positions = doc.getValue();
            json = json + "\t\t\"docid\" : " + docID + ",\n";
            json = json + "\t\t\"postions\" : " + positions + "\n";
            json = json + "\t\t},\n";
        }
        return json;
    }
}
