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

import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllComments() {
        // Mocking repository behavior
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setId("1");
        comment1.setText("This is comment 1");
        comment1.setCreatedOn("2024-05-09");
        comments.add(comment1);
        
        Comment comment2 = new Comment();
        comment2.setId("2");
        comment2.setText("This is comment 2");
        comment2.setCreatedOn("2024-05-10");
        comments.add(comment2);
        
        when(commentRepository.findAll()).thenReturn(comments);

        // Calling service method
        List<Comment> result = commentService.getAllComments();

        // Verifying result
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
