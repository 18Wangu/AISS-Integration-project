package aiss.vimeominer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.vimeominer.service.VimeoAPIService;

@RestController
@RequestMapping("/videominer/channel")
public class VimeoController {

    private final VimeoAPIService vimeoAPIService;

    public VimeoController(VimeoAPIService vimeoAPIService) {
        this.vimeoAPIService = vimeoAPIService;
    }
    
    
}
