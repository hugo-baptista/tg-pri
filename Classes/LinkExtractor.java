package classes;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkExtractor {

    public static ArrayList<String> extractLinks(String pageContent, String baseUrl) {
        Scanner scanner = new Scanner(System.in);
        // Ler input da keyword a procurar
        System.out.print("Enter the keyword to limit the search for documents: ");
        String keyword = scanner.next();
        scanner.close();
        ArrayList<String> lista = new ArrayList<>();
        Pattern linkPattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
        Matcher matcher = linkPattern.matcher(pageContent);
        while (matcher.find()) {
            String linkUrl = matcher.group(2);
            try {
                URL linkUrlObj = new URL(new URL(baseUrl), linkUrl);
                URLConnection linkConn = linkUrlObj.openConnection();
                linkConn.connect();
                int linkResponseCode = 200; // Assume 200 response code for non-HTTP protocols
                if (linkConn instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) linkConn;
                    linkResponseCode = httpConn.getResponseCode();
                }
                TimeUnit.MILLISECONDS.sleep(5);
                if (linkResponseCode == 200) {
                    Scanner linkScanner = new Scanner(linkConn.getInputStream(), StandardCharsets.UTF_8);
                    String linkContent = linkScanner.useDelimiter("\\A").next();
                    Document document = Jsoup.parse(linkContent);
                    Elements content = document.select("p");
                    linkScanner.close();
                    for (Element element : content) {
                        if (element.text().contains(keyword) && !lista.contains(linkUrlObj.toString())) {
                            System.out.println(linkUrlObj.toString() + " contains the keyword that you are looking for.");
                            lista.add(linkUrlObj.toString());
                            Thread.sleep(50);
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
}