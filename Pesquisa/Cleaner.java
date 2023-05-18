package Pesquisa;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Cleaner {

    public static void main(String[] args) throws IOException {
        File dir = new File("texto");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null){
            for(File file: directoryListing){
                Document document = Jsoup.parse(file);
                Elements elements = document.select("p");
                String filename = String.valueOf(file);
                filename = filename.replaceAll("texto", "");
                String filepath = "limpo" + "\\" + filename;
                writeElementsToFile(elements, filepath);

            }
            }
        }
        public static void writeElementsToFile(Elements elements, String filepath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for(Element element: elements){
                String text = element.text();
                writer.write(text);
                writer.newLine();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        }


}
