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

import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;

public class ChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private ChannelService channelService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllChannels() {
        // Mocking repository behavior
        List<Channel> channels = new ArrayList<>();
        Channel channel1 = new Channel();
        channel1.setId("1");
        channel1.setName("Channel 1");
        channel1.setDescription("Description 1");
        channel1.setCreatedTime("2024-01-01");
        channels.add(channel1);
        
        Channel channel2 = new Channel();
        channel2.setId("2");
        channel2.setName("Channel 2");
        channel2.setDescription("Description 2");
        channel2.setCreatedTime("2024-01-02");
        channels.add(channel2);
        
        when(channelRepository.findAll()).thenReturn(channels);

        // Calling service method
        List<Channel> result = channelService.getAllChannels();

        // Verifying result
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
