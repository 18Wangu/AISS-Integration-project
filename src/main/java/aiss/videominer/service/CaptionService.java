package aiss.videominer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;

@Service
public class CaptionService {

    @Autowired
    private CaptionRepository captionRepository;

    public List<Caption> getAllCaptions() {
        return captionRepository.findAll();
    }

    public Caption getCaptionById(String id) {
        return captionRepository.findById(id).orElse(null);
    }
}


