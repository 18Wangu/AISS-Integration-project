package aiss.youtubeminer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import aiss.youtubeminer.service.YouTubeAPIService;

import java.io.IOException;


@RestController
@RequestMapping("/youtubeminer/channel")
public class YouTubeController {

    private final YouTubeAPIService youTubeAPIService;
    
    private final RestTemplate restTemplate;

    @Autowired
    public YouTubeController(YouTubeAPIService youTubeAPIService, RestTemplate restTemplate) {
        this.youTubeAPIService = youTubeAPIService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public String getChannelInfo(@PathVariable String id) {
        try {
            return youTubeAPIService.getFormattedChannelInfo(id);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // You might want to handle this differently, like returning an error message
        }
    }
    
    @PostMapping("/sendToOtherApp/{id}")
    public ResponseEntity<String> sendChannelInfoToOtherApp(@PathVariable String id) {
        // Get channel info
        String channelInfo = getChannelInfo(id);

        // If channel info is null, return bad request
        if (channelInfo == null) {
            return ResponseEntity.badRequest().body("Failed to get channel information");
        }

        // Set headers for the POST request to the other application
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(channelInfo, headers);

        // Define the URL of the other application
        String otherAppUrl = "http://localhost:8080/videominer/channels/";

        // Send POST request to the other application
        ResponseEntity<String> responseEntity = restTemplate.exchange(otherAppUrl, HttpMethod.POST, requestEntity, String.class);

        // Return the response received from the other application
        return responseEntity;
    }
}
