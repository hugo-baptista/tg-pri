package classes;

import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class HashMapDocs {

    private HashMap<Integer, String> documentHashes;

    public HashMapDocs() {
        HashMap<Integer, String> map = new HashMap<>();
        this.documentHashes = map;
    }

    public void DocumentHashMapping() {
        documentHashes = new HashMap<>();
    }

    public void addDocument(String document) {
        int hashValue = document.hashCode();
        documentHashes.put(hashValue, document);
    }

    public String getDocName(Integer documentHash) {
        return documentHashes.get(documentHash);
    }

    public boolean containsKey(Integer key) {
        return documentHashes.containsKey(key);
    }

    public void saveToJsonFile() {
        String filename = "database/hashmapdocs.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(documentHashes);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromJsonFile() {
        String filename = "database/hashmapdocs.json";
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<HashMap<Integer, String>>() {}.getType();
            documentHashes = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanHashMapDocs() { 
        HashMapDocs docsHashMap = new HashMapDocs();
        docsHashMap.saveToJsonFile();
    }

    public int size() {
        return documentHashes.size();
    }
    
}
