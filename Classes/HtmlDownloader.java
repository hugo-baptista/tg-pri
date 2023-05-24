package classes;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class HtmlDownloader {
    Cleaner cleaner = new Cleaner();

    public void downloader(String url, String keyword) {
        
        ArrayList<String> links;
        try {
            URL website = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine + "\n");
            }

            in.close();
<<<<<<< HEAD
=======
            // FileWriter writer = new FileWriter(fileName);
            // writer.write(content.toString());
            // writer.close();
>>>>>>> 6bea9b0c42a2b21b72aa0ea176500a602641d97b

            links = LinkExtractor.extractLinks(content.toString(), url, keyword);
            System.out.println(links.size() + " links found that contain the keyword.");

            for (String link : links) {
                String linkName = link.replaceAll("[^a-zA-Z0-9.-]", "_") + ".html"; // replace invalid characters with underscores
                URL fileUrl = new URL(link);
                HttpURLConnection fileConnection = (HttpURLConnection) fileUrl.openConnection();
                fileConnection.setRequestMethod("GET");
                fileConnection.connect();

                BufferedInputStream bis = new BufferedInputStream(fileConnection.getInputStream());
                String fullpath = "database\\Downloads" + "\\" + linkName;
                FileOutputStream fos = new FileOutputStream(fullpath);

                byte[] buffer = new byte[1024];
                int count;
                while ((count = bis.read(buffer, 0, 1024)) != -1) {
                    fos.write(buffer, 0, count);
                }

                fos.close();
                bis.close();

                System.out.println("File " + linkName + " downloaded successfully.");
            }
            cleaner.htmlCleaner();
            deleteFilesInFolder("database\\Downloads");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
}