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

    private HashMap<String, Integer> documentHashes;

    public void DocumentHashMapping() {
        documentHashes = new HashMap<>();
    }

    public void addDocument(Document document) {
        int hashValue = document.hashCode();
        documentHashes.put(document.getName(), hashValue);
    }

    public Integer getHashValue(String documentName) {
        return documentHashes.get(documentName);
    }

    public void saveToJsonFile(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(documentHashes);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromJsonFile(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<HashMap<String, Pair<Integer, PostingList>>>() {}.getType();
            documentHashes = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
