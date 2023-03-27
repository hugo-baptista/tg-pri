package Pesquisa;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class HtmlDownloader {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Coloque um URL para fazer download:");
        String url = sc.nextLine();
        String output = "output3.html";
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
                FileWriter writer = new FileWriter(output);
                writer.write(content.toString());
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    }

