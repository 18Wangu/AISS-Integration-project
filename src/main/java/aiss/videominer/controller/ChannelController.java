package aiss.videominer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import aiss.videominer.model.Channel;
import aiss.videominer.service.ChannelService;


@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @GetMapping("/")
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }

    @GetMapping("/{id}")
    public Channel getChannelById(@PathVariable String id) {
        return channelService.getChannelById(id);
    }

    @PostMapping("/")
    public Channel createChannel(@RequestBody Channel channel) {
        return channelService.createChannel(channel);
    }

    @PutMapping("/{id}")
    public Channel updateChannel(@PathVariable String id, @RequestBody Channel updatedChannel) {
        return channelService.updateChannel(id, updatedChannel);
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable String id) {
        channelService.deleteChannel(id);
    }
}
