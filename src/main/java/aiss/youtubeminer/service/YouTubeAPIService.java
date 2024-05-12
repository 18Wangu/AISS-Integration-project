package aiss.youtubeminer.service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import aiss.youtubeminer.model.caption.Caption;
import aiss.youtubeminer.model.caption.CaptionSearch;
import aiss.youtubeminer.model.channel.Channel;
import aiss.youtubeminer.model.channel.ChannelSearch;
import aiss.youtubeminer.model.comment.Comment;
import aiss.youtubeminer.model.comment.CommentSearch;
import aiss.youtubeminer.model.videoSnippet.VideoSnippet;
import aiss.youtubeminer.model.videoSnippet.VideoSnippetSearch;
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

    private static final String API_KEY = "AIzaSyBWtZnB7KrKh3jXH6g6HdND-s8-NrOuHtQ";

    public String getFormattedChannelInfo(String channelId) throws IOException {
        // Récupérer les détails de la chaîne
        Channel channel = getChannelInfo(channelId);

        // Récupérer les détails des vidéos de la chaîne
        List<VideoSnippet> videos = getVideoDetails(channelId);

        // Récupérer les légendes pour chaque vidéo
        for (VideoSnippet video : videos) {
            List<Caption> captions = getCaptionsForVideo(video.getId().getVideoId());
            video.setCaptions(captions);
        }
        
        String description = channel.getSnippet().getDescription();
     // Remplacer les caractères de saut de ligne par des espaces
     description = description.replace("\n", " ").replace("\r", " ");
     // Échapper les guillemets dans la description
     description = escapeJsonString(description);

     // Créer le format JSON final
     StringBuilder jsonBuilder = new StringBuilder();
     jsonBuilder.append("{\n");
     jsonBuilder.append("    \"id\": \"").append(channelId).append("\",\n");
     jsonBuilder.append("    \"name\": \"").append(channel.getSnippet().getTitle()).append("\",\n");
     jsonBuilder.append("    \"description\": ").append(description).append(",\n");
     jsonBuilder.append("    \"createdTime\": \"").append(channel.getSnippet().getPublishedAt()).append("\",\n");
     jsonBuilder.append("    \"videos\": [\n");


     for (VideoSnippet video : videos) {
         List<Comment> comments = getCommentsForVideo(video.getId().getVideoId());
         video.setComments(comments);
     }
        // Ajouter les détails des vidéos
        for (VideoSnippet video : videos) {
            jsonBuilder.append("        ").append(getVideoJson(video)).append(",\n");
        }

        // Supprimer la virgule finale
        if (!videos.isEmpty()) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 2);
        }

        jsonBuilder.append("    ]\n}");
        
        return jsonBuilder.toString();
    }


    private Channel getChannelInfo(String channelId) throws IOException {
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
            return channelSearch.getItems().get(0);
        } else {
            // Gérer la réponse d'erreur
            System.out.println("Error: " + responseCode);
            return null;
        }
    }

    private List<VideoSnippet> getVideoDetails(String channelId) throws IOException {
        // Récupérer les détails des vidéos de la chaîne
        String apiUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&channelId=" + channelId + "&key=" + API_KEY;
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

            // Convert JSON response to VideoSnippetSearch object
            VideoSnippetSearch videoSearch = objectMapper.readValue(response.toString(), VideoSnippetSearch.class);
            return videoSearch.getItems();
        } else {
            // Gérer la réponse d'erreur
            System.out.println("Error: " + responseCode);
            return null;
        }
    }
    
    private List<VideoSnippet> getVideoDetails1(String channelId) throws IOException {
        String apiUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&channelId=" + channelId + "&key=" + API_KEY;
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

            // Convert JSON response to VideoSnippetSearch object
            VideoSnippetSearch videoSearch = objectMapper.readValue(response.toString(), VideoSnippetSearch.class);
            return videoSearch.getItems();
        } else {
            // Gérer la réponse d'erreur
            System.out.println("Error: " + responseCode);
            return null;
        }
    }
    
    private List<Comment> getCommentsForVideo(String videoId) throws IOException {
        String apiUrl = "https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&videoId=" + videoId + "&key=" + API_KEY;
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

            // Convert JSON response to CommentSearch object
            CommentSearch commentSearch = objectMapper.readValue(response.toString(), CommentSearch.class);
            return commentSearch.getItems();
        } else {
            // Gérer la réponse d'erreur
            System.out.println("Error: " + responseCode);
            return null;
        }
    }

    private List<Caption> getCaptionsForVideo(String videoId) throws IOException {
        String apiUrl = "https://www.googleapis.com/youtube/v3/captions?part=snippet&videoId=" + videoId + "&key=" + API_KEY;
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

            // Convert JSON response to CaptionSearch object
            CaptionSearch captionSearch = objectMapper.readValue(response.toString(), CaptionSearch.class);
            return captionSearch.getItems();
        } else {
            // Gérer la réponse d'erreur
            System.out.println("Error: " + responseCode);
            return null;
        }
    }
    
    private String getCommentsJson(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return "[]";
        }

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");
        for (Comment comment : comments) {
            jsonBuilder.append("                ").append(getCommentJson(comment)).append(",\n");
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 2);
        jsonBuilder.append("            ]");
        return jsonBuilder.toString();
    }

    private String getCommentJson(Comment comment) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("    \"id\": \"").append(escapeString1(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorChannelId().getValue())).append("\",\n");
        // Récupérer le texte original du commentaire
        String text = comment.getCommentSnippet().getTopLevelComment().getSnippet().getTextOriginal();
        // Remplacer les caractères de saut de ligne par des espaces dans le champ "text"
        text = text.replace("\n", " ").replace("\r", " ");
        // Échapper les caractères spéciaux dans le texte du commentaire
        text = escapeJsonString(text);
        jsonBuilder.append("    \"text\": ").append(text).append(",\n");
        jsonBuilder.append("    \"author\": \"").append(escapeString1(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName())).append("\",\n");
        jsonBuilder.append("    \"authorImage\": \"").append(escapeString1(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl())).append("\",\n");
        jsonBuilder.append("    \"publishedAt\": \"").append(escapeString1(comment.getCommentSnippet().getTopLevelComment().getSnippet().getPublishedAt())).append("\"\n");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    // Méthode pour échapper une chaîne de caractères JSON
    private String escapeJsonString(String str) {
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '"') {
                // Échapper les guillemets doubles en les remplaçant par \" dans la chaîne JSON
                escaped.append("\\\"");
            } else {
                escaped.append(c);
            }
        }
        return "\"" + escaped.toString() + "\"";
    }

    // Méthode pour échapper une chaîne de caractères JSON (en fonction de votre environnement)
    private String escapeString1(String str) {
        return str.replace("\"", "\\\"");
    }




    
    

    private String getCaptionsJson(List<Caption> captions) {
        if (captions == null || captions.isEmpty()) {
            return "[]";
        }

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");
        for (Caption caption : captions) {
            jsonBuilder.append("                ").append(getCaptionJson(caption)).append(",\n");
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 2);
        jsonBuilder.append("            ]");
        return jsonBuilder.toString();
    }
    
    private String getCaptionJson(Caption caption) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("                    \"id\": \"").append(caption.getId()).append("\",\n");
        jsonBuilder.append("                    \"name\": \"").append(caption.getSnippet().getName()).append("\",\n");
        jsonBuilder.append("                    \"language\": \"").append(caption.getSnippet().getLanguage()).append("\"\n");
        jsonBuilder.append("                }");
        return jsonBuilder.toString();
    }
    
    private String getVideoJson(VideoSnippet video) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("            \"id\": \"").append(video.getId().getVideoId()).append("\",\n");
        jsonBuilder.append("            \"name\": \"").append(video.getSnippet().getTitle()).append("\",\n");
        jsonBuilder.append("            \"description\": \"").append(video.getSnippet().getDescription()).append("\",\n");
        jsonBuilder.append("            \"releaseTime\": \"").append(video.getSnippet().getPublishedAt()).append("\",\n");
        jsonBuilder.append("    		\"comments\": [],\n");
        jsonBuilder.append("            \"captions\": ").append(getCaptionsJson(video.getCaptions())).append("\n");
        jsonBuilder.append("        }");
        return jsonBuilder.toString();
    }
    
    private String escapeString(String input) {
        // Échapper les guillemets et les caractères de contrôle
        String escaped = input
                .replaceAll("\"", "\\\\\"") // Échapper les guillemets doubles
                .replaceAll("\\\\", "\\\\\\\\") // Échapper les barres obliques inverses
                .replaceAll("\b", "\\\\b") // Échapper le caractère de retour arrière
                .replaceAll("\f", "\\\\f") // Échapper le caractère de saut de page
                .replaceAll("\n", "\\\\n") // Échapper le caractère de nouvelle ligne
                .replaceAll("\r", "\\\\r") // Échapper le caractère de retour chariot
                .replaceAll("\t", "\\\\t"); // Échapper le caractère de tabulation horizontale
        // Échapper les caractères Unicode qui peuvent causer des problèmes de formatage JSON
        return escaped.replaceAll("[\\p{C}]", "");
    }
}