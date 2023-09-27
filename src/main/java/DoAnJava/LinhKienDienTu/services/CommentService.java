package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<Comment> getCommentByProductId(Long id);

    Comment getCommentById(UUID id);

    void saveComment(Comment comment);

    void removeComment(UUID id);
}
