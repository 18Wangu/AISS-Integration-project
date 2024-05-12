package aiss.youtubeminer.service;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YouTubeAPIServiceTest {

    @Test
    public void testGetChannelInfo() {
        try {
            String channelId = "UCyAwjKog9hNCWV6nuA-CYvw"; // Remplacez par l'ID de la chaîne YouTube que vous souhaitez tester
            String apiUrl = "https://www.googleapis.com/youtube/v3/channels?part=snippet&id=" + channelId + "&key=AIzaSyAY0uqhKIlD_wrOYBNP8pj-mLKRu4SAGic";
            System.out.println(apiUrl);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Afficher la réponse brute dans la console
                System.out.println("Raw Response from YouTube API:");
                System.out.println(response.toString());
            } else {
                // Gérer la réponse d'erreur
                System.out.println("Error: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

