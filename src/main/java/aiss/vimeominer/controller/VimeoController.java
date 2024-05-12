package aiss.vimeominer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.vimeominer.model.channel.SimpleChannel;
import aiss.vimeominer.service.VimeoAPIService;

@RestController
@RequestMapping("/vimeominer/channel")
public class VimeoController {

    private final VimeoAPIService vimeoAPIService;

    public VimeoController(VimeoAPIService vimeoAPIService) {
        this.vimeoAPIService = vimeoAPIService;
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<SimpleChannel> getChannelInfo(@PathVariable String channelId) {
        SimpleChannel channelInfo = vimeoAPIService.getChannelInfo(channelId);
        
        if (channelInfo != null) {
            return new ResponseEntity<>(channelInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
