package Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Classes.Dicionario;
import Classes.PostingList;

public class IndexingEngine {
    private Map<String, Integer> documentMap;  // HashMap dos documentos
    private Map<String, Integer> termMap;      // HashMap dos termos
    
    public IndexingEngine() {
        documentMap = new HashMap<>();
        termMap = new HashMap<>();
    }

    List<String> stopWords = new ArrayList<String>();
    stopWords.add("and");
    stopWords.add("or");
    stopWords.add("it");
    
    public void indexDocument(String document) {
        if (!documentMap.containsKey(document)) {
            int documentId = documentMap.size() + 1;  // ID com autoincrement. !!Isto dá barraco com novas pesquisas!!
            documentMap.put(document, documentId);
            
            // Process the terms in the document
            String[] terms = document.split("\\s+");  // Termos são separados por espaços
            for (String term : terms) {
                term = term.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // Normalizar
                if (!term.isEmpty()) {
                    indexTerm(term);
                }
            }
            
            System.out.println("Documento indexado: " + document);
        } else {
            System.out.println("Documento já indexado: " + document);
        }
    }
    
    private void indexTerm(String term) {
        if (!termMap.containsKey(term)) {
            int termId = termMap.size() + 1;  // ID com autoincrement !!Isto dá barraco com novas pesquisas!!
            termMap.put(term, termId);
        }
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

