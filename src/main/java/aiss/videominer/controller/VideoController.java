package aiss.videominer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.videominer.model.Video;
import aiss.videominer.service.*;

@RestController
@RequestMapping("/videominer/videos")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/")
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable String id) {
        return videoService.getVideoById(id);
    }

}
