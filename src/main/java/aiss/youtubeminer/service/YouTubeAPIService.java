package aiss.youtubeminer.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import aiss.youtubeminer.model.channel.Channel;
import aiss.youtubeminer.model.channel.ChannelSearch;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class YouTubeAPIService {

    // Clé d'API YouTube
    private static final String API_KEY = "AIzaSyAY0uqhKIlD_wrOYBNP8pj-mLKRu4SAGic";

    // Méthode pour obtenir les informations formatées d'une chaîne YouTube
    public String getFormattedChannelInfo(String channelId) throws IOException {
        // Construction de l'URL de l'API YouTube
        String apiUrl = "https://www.googleapis.com/youtube/v3/channels?part=snippet&id=" + channelId + "&key=" + API_KEY;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        // Vérification de la réponse HTTP
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Lecture de la réponse
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Configuration de l'ObjectMapper pour ignorer les propriétés inconnues
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Conversion de la réponse JSON en objet ChannelSearch
            ChannelSearch channelSearch = objectMapper.readValue(response.toString(), ChannelSearch.class);
            List<Channel> items = channelSearch.getItems();
            if (items != null && !items.isEmpty()) {
                // Formatage des informations de la chaîne
                Channel channel = items.get(0);
                String formattedInfo = "{\n" +
                        "    \"id\": \"" + channel.getId() + "\",\n" +
                        "    \"name\": \"" + channel.getSnippet().getTitle() + "\",\n" +
                        "    \"description\": \"" + channel.getSnippet().getDescription() + "\",\n" +
                        "    \"createdTime\": \"" + channel.getSnippet().getPublishedAt() + "\"\n" +
                        "}";
                return formattedInfo;
            } else {
                // Gestion de la réponse vide ou nulle
                return null;
            }
        } else {
            // Gestion de la réponse d'erreur
            System.out.println("Error: " + responseCode);
            return null;
        }
    }
}
