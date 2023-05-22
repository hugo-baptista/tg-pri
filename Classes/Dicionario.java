package classes;

import java.util.HashMap;
import java.util.*;
 
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import classes.PostingList;

public class Dicionario {
    // dicionário de termos - < Termo: < DocFreq, PostingList > >
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

    public void add_term(String termo, Integer freq, PostingList postinglist) {
        Pair<Integer,PostingList> value = new Pair<>(freq, postinglist);
        this.dicionario.put(termo, value);
    }

    public PostingList getPostingList(String term) {
        Pair<Integer, PostingList> pair = dicionario.get(term);
        if (pair != null) {
            return pair.getValue();
        }
        PostingList empty_postList = new PostingList();
        return empty_postList;
    }
    public Pair<Integer, PostingList> getPair(String term) {
        Pair<Integer, PostingList> pair = dicionario.get(term);
        if (pair != null) {
            return pair;
        }
        return null;
    }


    public Integer getDocFreq(String term) {
        Pair<Integer, PostingList> pair = dicionario.get(term);
        if (pair != null) {
            return pair.getKey();
        }
        return null;
    }

    public Set<String> getTermos() {
        Set<String> set = dicionario.keySet();
        return set;
    }

    // public ArrayList<Integer> getPositionsList(String term, Integer docId) {
    //     PostingList postlist = dicionario.getPostingList(term);
    //     ArrayList<Integer> posList = postlist.getPositionsList(docId);
    //     return posList;
    // }

    // public void updatePositionsList(String term, Integer docId, Integer pos) {
    //     ArrayList<Integer> positions = dicionario.getPositionsList(term, docId);
    //     positions.add(pos);
        
    //     dicionario.put(term);
    // }


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
        Integer i_max = dicionario.keySet().size();
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
            if(i==i_max){
                json = json + "\t}\n";
            } else {
                json = json + "\t},\n";
            }
        } 
        json = json + "]";
        return json;
    }

    public void saveToJsonFile() {
        String filename = "./database/dicionario.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dicionario);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromJsonFile() {
        String filename = "./database/dicionario.json";
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<HashMap<String, Pair<Integer, PostingList>>>() {}.getType();
            dicionario = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsKey(String key) {
        return dicionario.containsKey(key);
    }

    public int size() {
        return dicionario.size();
    }
}


