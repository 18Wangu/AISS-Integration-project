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

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;

public class CaptionServiceTest {

    @Mock
    private CaptionRepository captionRepository;

    @InjectMocks
    private CaptionService captionService;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCaptions() {
        // Mocking repository behavior
        List<Caption> captions = new ArrayList<>();
        Caption caption1 = new Caption();
        caption1.setId("1");
        caption1.setName("Caption 1");
        caption1.setLanguage("English");
        captions.add(caption1);
        
        Caption caption2 = new Caption();
        caption2.setId("2");
        caption2.setName("Caption 2");
        caption2.setLanguage("French");
        captions.add(caption2);
        
        when(captionRepository.findAll()).thenReturn(captions);

        // Calling service method
        List<Caption> result = captionService.getAllCaptions();

        // Verifying result
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
