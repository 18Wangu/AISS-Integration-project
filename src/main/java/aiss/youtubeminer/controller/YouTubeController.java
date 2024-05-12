package aiss.youtubeminer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.youtubeminer.service.YouTubeAPIService;

import java.io.IOException;

@RestController
@RequestMapping("/videominer/channel")
public class YouTubeController {

    private final YouTubeAPIService youTubeAPIService;

    @Autowired
    public YouTubeController(YouTubeAPIService youTubeAPIService) {
        this.youTubeAPIService = youTubeAPIService;
    }

    @GetMapping("/{id}")
    public String getChannelInfo(@PathVariable String id) {
        try {
            return youTubeAPIService.getFormattedChannelInfo(id);
        } catch (IOException e) {
            e.printStackTrace();
            return null; 
        }
    }
}
