package classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import classes.Dicionario;

public class ReadJsonDic {
    public static void main(String[] args) {
        try (FileReader reader = new FileReader("./database/dicionario.json")) {
            // Read the JSON from the file
            Gson gson = new GsonBuilder().create();
            HashMap<String, Pair<Integer, PostingList>> map = gson.fromJson(reader, HashMap.class);



            // Now you have the map object instantiated from the JSON data
            // You can use the map object in your code
            // ...
        } catch (IOException e) {
            e.printStackTrace();
        }

        public HashMap<String, Pair<Integer, PostingList>> exportHashMap() {
            return map;
        }
    }
}
