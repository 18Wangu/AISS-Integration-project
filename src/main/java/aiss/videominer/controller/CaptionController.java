package aiss.videominer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.videominer.model.Caption;
import aiss.videominer.service.*;

@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    private final CaptionService captionService;

    @Autowired
    public CaptionController(CaptionService captionService) {
        this.captionService = captionService;
    }

    @GetMapping("/")
    public List<Caption> getAllCaption() {
        return captionService.getAllCaptions();
    }

    @GetMapping("/{id}")
    public Caption getCaptionById(@PathVariable String id) {
        return captionService.getCaptionById(id);
    }

}
