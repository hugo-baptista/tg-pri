package trabalho_grupo;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;

public class Dicionario {
    // dicionário de termos - < Termo: < Frequência, < PostingList: DocID,PositionList > > >
    private HashMap<String,Pair<Integer,ArrayList<Pair<Integer,ArrayList<Integer>>>>> dicionario;

    //constructores
    public Dicionario (HashMap<String,Pair<Integer,ArrayList<Pair<Integer,ArrayList<Integer>>>>> dic) {
        this.dicionario = dic;
    }

    public Dicionario (String termo, Integer freq, ArrayList<Pair<Integer,ArrayList<Integer>>> postinglist) {
        Pair<Integer,ArrayList<Pair<Integer,ArrayList<Integer>>>> value = new Pair<>(freq, postinglist);
        HashMap<String,Pair<Integer,ArrayList<Pair<Integer,ArrayList<Integer>>>>> dic = new HashMap<>();
        dic.put(termo, value);
        this.dicionario = dic;
    }

    public void add(String termo, Integer freq, ArrayList<Pair<Integer,ArrayList<Integer>>> postinglist) {
        Pair<Integer,ArrayList<Pair<Integer,ArrayList<Integer>>>> value = new Pair<>(freq, postinglist);
        this.dicionario.put(termo, value);
    }

    @Override
    public String toString() {
        // return this.dicionario.toString();
        
        String dict = new String("Dicionario:\n");
        for (HashMap.Entry<String,Pair<Integer,ArrayList<Pair<Integer,ArrayList<Integer>>>>> entry : dicionario.entrySet()) {
            String term = entry.getKey();
            int freq = entry.getValue().getKey();
            dict = (dict + term + ":" + freq + ":\n");
            ArrayList<Pair<Integer,ArrayList<Integer>>> docs = entry.getValue().getValue();
            for (Pair<Integer,ArrayList<Integer>> doc : docs ) {
                int docID = doc.getKey();
                ArrayList<Integer> positions = doc.getValue();
                dict = (dict + docID + ":" + positions + "\n");
            }
        } 
        return dict;
    }
    
    public String toJSON() {
        String json = new String("[\n");
        for (HashMap.Entry<String,Pair<Integer,ArrayList<Pair<Integer,ArrayList<Integer>>>>> entry : dicionario.entrySet()) {
            json = json + "\t{\n";
            String term = entry.getKey();
            int freq = entry.getValue().getKey();
            json = json + "\t\"term\" : \"" + term + "\",\n";
            json = json + "\t\"document_frequency\" : " + freq + ",\n";
            json = json + "\t\"posting_list\" : [\n";
            ArrayList<Pair<Integer,ArrayList<Integer>>> docs = entry.getValue().getValue();
            for (Pair<Integer,ArrayList<Integer>> doc : docs ) {
                json = json + "\t\t{\n";
                int docID = doc.getKey();
                ArrayList<Integer> positions = doc.getValue();
                json = json + "\t\t\"docid\" : " + docID + ",\n";
                json = json + "\t\t\"postions\" : " + positions + "\n";
                json = json + "\t\t},\n";
            }
            json = json + "\t]\n";
            json = json + "\t},\n";
        } 
        json = json + "]";
        return json;
    }
}


