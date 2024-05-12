package aiss.vimeominer.service;

import aiss.vimeominer.model.channel.SimpleChannel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VimeoAPIService {

    private final String BASE_URL = "https://api.vimeo.com/channels/";
    private final String ACCESS_TOKEN = "bb831efed2fdd1fd9e03fd78831f8c47";

    private RestTemplate restTemplate = new RestTemplate();

    public void SimpleChannelService() {
        this.restTemplate = new RestTemplate();
    }

    public SimpleChannel getChannelInfo(String channelId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ACCESS_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleChannel> response = restTemplate.exchange(
                BASE_URL + channelId,
                HttpMethod.GET,
                entity,
                SimpleChannel.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.err.println("Failed to retrieve SimpleChannel. Status code: " + response.getStatusCode());
            return null;
        }
    }
}
