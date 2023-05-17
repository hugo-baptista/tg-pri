package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import classes.Dicionario;
import classes.PostingList;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;



public class IndexingEngine {
    Dicionario dicionario = new Dicionario();
    ArrayList<Pair<String, Integer>> termsList = new ArrayList<Pair<String, Integer>>();
    
    // public IndexingEngine() {
    //     dicionario = new HashMap<>();
    //     termMap = new HashMap<>();
    // }

    final List<String> stopWords =
        Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is",
            "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there",
            "these", "they", "this", "to", "was", "will", "with");
    
    public void indexDocument(String document) {
        dicionario.readFromJsonFile("./database/dicionario.json");

        if (!dicionario.containsKey(document)) {
            int documentId = dicionario.size() + 1;  // ID com autoincrement. !!Isto dá barraco com novas pesquisas!!
            // dicionario.put(document, documentId);
            
            // Processar os termos no documento
            String[] terms = document.split("\\s+");  // Termos são separados por espaços
            Integer position = 0;
            for (String term : terms) {
                term = term.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // Normalizar
                if (!term.isEmpty()) {
                    position += 1;
                    indexTerm(term, position);
                }
            }

            for (Pair<String, Integer> pair : termsList) {
                if (!dicionario.containsKey(pair.getKey())) {
                    ArrayList<Integer> pos = new ArrayList<Integer>(pair.getValue());
                    PostingList postList = new PostingList(documentId, pos);
                    dicionario.add_term(pair.getKey(), postList);
                }
                else {
                    
                }
            }
            
            System.out.println("Documento indexado: " + document);
        } else {
            System.out.println("Documento já indexado: " + document);
        }
    }
    
    private void indexTerm(String term, Integer position) {
        Pair<String, Integer> newPair = new Pair<>(term, position);
        termsList.add(newPair);
    }
    
    public static void main(String[] args) {
        IndexingEngine engine = new IndexingEngine();
        
        // Index some example documents
        engine.indexDocument("Document 1: This is a sample document.");
        engine.indexDocument("Document 2: Another document with sample content.");
        engine.indexDocument("Document 1: This is a sample document.");  // Duplicate document
        
        // Print the document and term maps
        System.out.println("\nMapa de documentos:");
        for (Map.Entry<String, Integer> entry : engine.documentMap.entrySet()) {
            System.out.println("Documento: " + entry.getKey() + ", Documento ID: " + entry.getValue());
        }
        
        System.out.println("\nMapa de termos:");
        for (Map.Entry<String, Integer> entry : engine.termMap.entrySet()) {
            System.out.println("Termo: " + entry.getKey() + ", Termo ID: " + entry.getValue());
        }
    }
}
