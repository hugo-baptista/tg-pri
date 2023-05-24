package classes;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Cleaner {

    public void htmlCleaner() throws IOException {
        File dir = new File("database/Downloads");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null){
            for(File file: directoryListing){
                Document document = Jsoup.parse(file);
                Elements elements = document.select("p");
                String filename = String.valueOf(file);
                System.out.println("\n" + filename);
                filename = filename.replace("database\\Downloads", "");
                String filepath = "database/Documentos" + "/" + filename;
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

    }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
