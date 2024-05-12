package aiss.videominer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(String id) {
        return commentRepository.findById(id).orElse(null);
    }
}


