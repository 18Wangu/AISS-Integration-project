package aiss.videominer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;

public class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVideos() {
        // Mocking repository behavior
        List<Video> videos = new ArrayList<>();
        Video video1 = new Video();
        video1.setId("1");
        video1.setName("Video 1");
        video1.setDescription("Description 1");
        video1.setReleaseTime("2024-01-01");
        videos.add(video1);
        
        Video video2 = new Video();
        video2.setId("2");
        video2.setName("Video 2");
        video2.setDescription("Description 2");
        video2.setReleaseTime("2024-01-02");
        videos.add(video2);
        
        when(videoRepository.findAll()).thenReturn(videos);

        // Calling service method
        List<Video> result = videoService.getAllVideos();

        // Verifying result
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

