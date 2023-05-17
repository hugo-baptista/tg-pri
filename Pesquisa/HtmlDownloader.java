package Pesquisa;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class HtmlDownloader {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a URL to download:");

        String url = sc.nextLine();
        String fileName = url.replaceAll("[^a-zA-Z0-9.-]", "_") + ".html"; // replace invalid characters with underscores
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
            FileWriter writer = new FileWriter(fileName);
            writer.write(content.toString());
            writer.close();

            links = LinkExtractor.extractLinks(content.toString(), url);
            System.out.println(links.size() + " links found that match the rule.");

            for (String link : links) {
                String linkName = link.replaceAll("[^a-zA-Z0-9.-]", "_") + ".html"; // replace invalid characters with underscores
                URL fileUrl = new URL(link);
                HttpURLConnection fileConnection = (HttpURLConnection) fileUrl.openConnection();
                fileConnection.setRequestMethod("GET");
                fileConnection.connect();

                BufferedInputStream bis = new BufferedInputStream(fileConnection.getInputStream());
                String fullpath = "texto" + "\\" + linkName;
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}