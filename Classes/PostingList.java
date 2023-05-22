package classes;

import java.util.ArrayList;

import java.util.*;

public class PostingList {
    // Posting List - < DocID, PositionList >
    private HashMap<Integer,ArrayList<Integer>> posting_list;

    // construtores
    public PostingList() {
        HashMap<Integer,ArrayList<Integer>> posting_list = new HashMap<>();
        this.posting_list = posting_list;
    }

    public PostingList(HashMap<Integer,ArrayList<Integer>> posting_list) {
        this.posting_list = posting_list;
    }

    public PostingList(Integer doc_id, ArrayList<Integer> position_list) {
        this.posting_list.put(doc_id, position_list);
    }

    // métodos
    public void add_doc(Integer doc_id, ArrayList<Integer> position_list) {
        posting_list.put(doc_id, position_list);
    }

    public Integer size() {
        return posting_list.size();
    }

    public ArrayList<Integer> getPositionsList(Integer docId) {
        if (posting_list.containsKey(docId)) {
            return posting_list.get(docId);
        }
        return null;
    }

    public HashMap<Integer,ArrayList<Integer>> getAllPairs() {
        return posting_list;
    }

    public Set<Map.Entry<Integer,ArrayList<Integer>>> entrySet() {
        return posting_list.entrySet();
    }

    public boolean containsKey(Integer key) {
        return posting_list.containsKey(key);
    }

    @Override

    

    public String toString() {
        String posting_list_string = new String();
        for (Map.Entry<Integer,ArrayList<Integer>> entry : posting_list.entrySet()) {
            Integer doc_id = entry.getKey();
            ArrayList<Integer> position_list = entry.getValue();
            posting_list_string = (posting_list_string + "Document: " + doc_id + " - Positions: " + position_list + "\n");
        }
        return posting_list_string;
    }

    public String toJSON() {
        String json = new String();
        Integer i = 0;
        Integer i_max = this.posting_list.size();
        for (Map.Entry<Integer,ArrayList<Integer>> entry : posting_list.entrySet()) {
            json = json + "\t\t{\n";
            int docID = entry.getKey();
            ArrayList<Integer> positions = entry.getValue();
            json = json + "\t\t\"docid\" : " + docID + ",\n";
            json = json + "\t\t\"positions\" : " + positions + "\n";
            i++;
            if(i==i_max){
                json = json + "\t\t}\n";
            } else {
                json = json + "\t\t},\n";
            }
        }
        return json;
    }
}
