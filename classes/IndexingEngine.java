package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;


import java.io.IOException;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class IndexingEngine {
    Dicionario dicionario = new Dicionario();

    final List<String> stopWords =
        Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is",
            "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there",
            "these", "they", "this", "to", "was", "will", "with");
    
    public void indexDocuments() {
        String folderPath = "database/Documentos";

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String document = readFileToString(file.getPath());
                        String docName = file.getName();
                        dicionario.readFromJsonFile();
                        HashMapDocs docsHashMap = new HashMapDocs();
                        docsHashMap.readFromJsonFile();
                        int hashkey = docName.hashCode();

                        if (!docsHashMap.containsKey(hashkey)) {
                            docsHashMap.addDocument(docName);
                            ArrayList<Pair<String, Integer>> termsList = new ArrayList<Pair<String, Integer>>();
                            // Processar os termos no documento
                            String[] terms = document.split("\\s+");  // Termos são separados por espaços
                            Integer position = 0;
                            for (String term : terms) {
                                term = term.toLowerCase(); // Normalizar
                                term = term.replaceAll("[áàâã]", "a");
                                term = term.replaceAll("[éèê]", "e");
                                term = term.replaceAll("[íìî]", "i");
                                term = term.replaceAll("[óôõò]", "o");
                                term = term.replaceAll("[úùû]", "u");
                                term = term.replaceAll("[^a-z0-9]", "");
                                if (term == "slovak") {
                                    System.out.println(term);
                                }
                                
                                
                                
                                if (!term.isEmpty()) {
                                    position += 1;
                                    if (!stopWords.contains(term)){
                                        indexTerm(term, position, termsList);
                                    }
                                    
                                }
                            }

                            for (Pair<String, Integer> pair : termsList) {
                                if (!dicionario.containsKey(pair.getKey())) {
                                    // ArrayList<Integer> pos = new ArrayList<Integer>(pair.getValue());
                                    ArrayList<Integer> pos = new ArrayList<>(Collections.singletonList(pair.getValue()));
                                    PostingList postList = new PostingList();
                                    postList.add_doc(hashkey, pos);
                                    dicionario.add_term(pair.getKey(), postList);
                                }
                                else {
                                    PostingList postList = dicionario.getPostingList(pair.getKey());
                                    if (postList.containsKey(hashkey)) {
                                        ArrayList<Integer> positions = postList.getPositionsList(hashkey);
                                        positions.add(pair.getValue());
                                        postList.add_doc(hashkey, positions);
                                    }
                                    else {
                                        ArrayList<Integer> positions = new ArrayList<>();
                                        positions.add(pair.getValue());
                                        postList.add_doc(hashkey, positions);
                                        Pair<Integer, PostingList> termPair = dicionario.getPair(pair.getKey());
                                        int docf = termPair.getKey() + 1;
                                        termPair = new Pair<>(docf, postList);
                                        dicionario.add_term(pair.getKey(), termPair.getKey(), postList);
                                        // Integer docf = dicionario.getPair(pair.getKey()).getKey();
                                        // docf += 1;
                                        // dicionario.add_term(pair.getKey(), postList);
                                    }

                                }
                            }
                            docsHashMap.saveToJsonFile();
                            dicionario.saveToJsonFile();
                            System.out.println("Documento indexado: " + docName);
                        } else {
                            System.out.println("Documento já indexado: " + docName);
                        }
                    } catch (IOException e) {
                        System.err.println("An error occurred while reading the file: " + e.getMessage());
                    }
                }
            }
        }
        
    }
    
    private void indexTerm(String term, Integer position, ArrayList<Pair<String, Integer>> termsList) {
        Pair<String, Integer> newPair = new Pair<>(term, position);
        if (term == "again") {
            System.out.println(position);
        }
        termsList.add(newPair);
    }

    public static String readFileToString(String filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
}

