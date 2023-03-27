package Pesquisa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LinkExtractor {

    public static void main(String[] args) {
        String pageUrl = "https://en.wikipedia.org/wiki/Entrance_of_the_Gladiators";
        try {
            URL url = new URL(pageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
                String pageContent = scanner.useDelimiter("\\A").next();
                scanner.close();
                Pattern linkPattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
                Matcher matcher = linkPattern.matcher(pageContent);
                while (matcher.find()) {
                    String linkUrl = matcher.group(2);
                    URL linkUrlObj = new URL(url, linkUrl);
                    linkUrlObj = new URL(linkUrlObj.getProtocol(), linkUrlObj.getHost(), linkUrlObj.getPort(), linkUrlObj.getPath());
                    String linkUrlString = linkUrlObj.toString();
                    HttpURLConnection linkConn = (HttpURLConnection) linkUrlObj.openConnection();
                    linkConn.setRequestMethod("GET");
                    linkConn.connect();
                    int linkResponseCode = linkConn.getResponseCode();
                    if (linkResponseCode == 200) {
                        Scanner linkScanner = new Scanner(linkConn.getInputStream(), StandardCharsets.UTF_8);
                        String linkContent = linkScanner.useDelimiter("\\A").next();
                        linkScanner.close();
                        if (linkContent.contains("World")) {
                            System.out.println(linkUrlString + " contains the specific value.");
                        }
                    }
                }
            } else {
                System.out.println("Failed to retrieve page content. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
