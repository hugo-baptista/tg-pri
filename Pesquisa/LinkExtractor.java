package Pesquisa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

public class LinkExtractor {

    public static ArrayList<String> extractLinks(String pageContent, String baseUrl) {
        ArrayList<String> lista = new ArrayList<>();
        Pattern linkPattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
        Matcher matcher = linkPattern.matcher(pageContent);
        while (matcher.find()) {
            String linkUrl = matcher.group(2);
            try {
                URL linkUrlObj = new URL(new URL(baseUrl), linkUrl);
                if (linkUrlObj.getProtocol().startsWith("http")) {
                    String linkUrlString = linkUrlObj.toString();
                    HttpURLConnection linkConn = (HttpURLConnection) linkUrlObj.openConnection();
                    linkConn.setRequestMethod("GET");
                    linkConn.connect();
                    int linkResponseCode = linkConn.getResponseCode();
                    TimeUnit.MILLISECONDS.sleep(5);
                    if (linkResponseCode == 200) {
                        Scanner linkScanner = new Scanner(linkConn.getInputStream(), StandardCharsets.UTF_8);
                        String linkContent = linkScanner.useDelimiter("\\A").next();
                        linkScanner.close();
                        if (linkContent.contains("World") && !lista.contains(linkUrlString)) {
                            System.out.println(linkUrlString + " contains the specific value.");
                            lista.add(linkUrlString);
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