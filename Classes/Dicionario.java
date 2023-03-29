package classes;

import java.util.HashMap;
import javafx.util.Pair;

public class Dicionario {
    // dicionário de termos - < Termo: < Frequência, PostingList > >
    private HashMap<String,Pair<Integer,PostingList>> dicionario;
    
    // construtores
    public Dicionario () {
        HashMap<String,Pair<Integer,PostingList>> dic = new HashMap<>();
        this.dicionario = dic;
    }

    public Dicionario (HashMap<String,Pair<Integer,PostingList>> dic) {
        this.dicionario = dic;
    }
    
    public Dicionario (String termo, Integer freq, PostingList postinglist) {
        Pair<Integer,PostingList> value = new Pair<>(freq, postinglist);
        HashMap<String,Pair<Integer,PostingList>> dic = new HashMap<>();
        dic.put(termo, value);
        this.dicionario = dic;
    }

    // métodos
    public void add_term(String termo, PostingList postinglist) {
        Integer freq = postinglist.size();
        Pair<Integer,PostingList> value = new Pair<>(freq, postinglist);
        this.dicionario.put(termo, value);
    }

    @Override
    public String toString() {
        String dictionary_string = new String("Dicionario:\n");
        for (HashMap.Entry<String,Pair<Integer,PostingList>> entry : dicionario.entrySet()) {
            String term = entry.getKey();
            int freq = entry.getValue().getKey();
            dictionary_string = (dictionary_string + "Term: " + term + "\nDocument Frequency: " + freq + "\n");
            PostingList posting_list = entry.getValue().getValue();
            dictionary_string = (dictionary_string + posting_list.toString());
        } 
        return dictionary_string;
    }
    
    public String toJSON() {
        String json = new String("[\n");
        Integer i = 0;
        for (HashMap.Entry<String,Pair<Integer,PostingList>> entry : dicionario.entrySet()) {
            json = json + "\t{\n";
            String term = entry.getKey();
            int freq = entry.getValue().getKey();
            json = json + "\t\"term\" : \"" + term + "\",\n";
            json = json + "\t\"document_frequency\" : " + freq + ",\n";
            json = json + "\t\"posting_list\" : [\n";
            PostingList posting_list = entry.getValue().getValue();
            json = json + posting_list.toJSON();
            json = json + "\t]\n";
            i++;
            if(i==dicionario.keySet().size()){
                json = json + "\t}\n";
            } else {
                json = json + "\t},\n";
            }
        } 
        json = json + "]";
        return json;
    }
}


