package aiss.youtubeminer.service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import aiss.youtubeminer.model.channel.Channel;
import aiss.youtubeminer.model.channel.ChannelSearch;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.List;

@Service
public class YouTubeAPIService {

    private static final String API_KEY = "AIzaSyAY0uqhKIlD_wrOYBNP8pj-mLKRu4SAGic";

    public String getFormattedChannelInfo(String channelId) throws IOException {
        String apiUrl = "https://www.googleapis.com/youtube/v3/channels?part=snippet&id=" + channelId + "&key=" + API_KEY;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Convert JSON response to ChannelSearch object
            ChannelSearch channelSearch = objectMapper.readValue(response.toString(), ChannelSearch.class);
            List<Channel> items = channelSearch.getItems();
            if (items != null && !items.isEmpty()) {
                // Format channel information
                Channel channel = items.get(0);

                String description = channel.getSnippet().getDescription();
             // Remplacer les caractères de saut de ligne par des espaces
             description = description.replace("\n", " ").replace("\r", " ");
             String formattedInfo = "{\n" +
                     "    \"id\": \"" + channel.getId() + "\",\n" +
                     "    \"name\": \"" + channel.getSnippet().getTitle() + "\",\n" +
                     "    \"description\": \"" + description + "\",\n" +
                     "    \"createdTime\": \"" + channel.getSnippet().getPublishedAt() + "\",\n" +
                     "    \"videos\": []\n" +
                     "}";




                return formattedInfo;
            } else {
                // Handle empty or null response
                return null;
            }
        } else {
            // Handle error response
            System.out.println("Error: " + responseCode);
            return null;
        }
    }
}

