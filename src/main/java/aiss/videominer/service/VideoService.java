package aiss.videominer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video getVideoById(String id) {
        return videoRepository.findById(id).orElse(null);
    }

    // Autres méthodes de service pour la gestion des vidéos...
}


