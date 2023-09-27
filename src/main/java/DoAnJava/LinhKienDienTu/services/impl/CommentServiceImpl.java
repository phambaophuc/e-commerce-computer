package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.entity.Comment;
import DoAnJava.LinhKienDienTu.repository.CommentRepository;
import DoAnJava.LinhKienDienTu.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentByProductId(Long id) {
        return commentRepository.findCommentByProductId(id);
    }

    @Override
    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void saveComment(Comment comment) {
        comment.setCreatedAt(LocalDate.now());
        commentRepository.save(comment);
    }

    @Override
    public void removeComment(UUID id) {
        commentRepository.deleteById(id);
    }
}
