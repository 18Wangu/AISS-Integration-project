package aiss.videominer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public Channel getChannelById(String id) {
        return channelRepository.findById(id).orElse(null);
    }

    public Channel createChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    public void deleteChannel(String id) {
        channelRepository.deleteById(id);
    }

    public Channel updateChannel(String id, Channel updatedChannel) {
        Channel channel = channelRepository.findById(id).orElse(null);
        if (channel != null) {
            channel.setName(updatedChannel.getName());
            channel.setDescription(updatedChannel.getDescription());
            channel.setCreatedTime(updatedChannel.getCreatedTime());
            channel.setVideos(updatedChannel.getVideos());
            return channelRepository.save(channel);
        }
        return null;
    }
}
