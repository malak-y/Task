package Service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import Model.Card;
import Model.Comment;
import Model.User;

@Stateless
public class CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    public Comment addCommentToCard(Long cardId, String text, User user) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setCard(entityManager.find(Card.class, cardId));
        comment.setUser(user);
        entityManager.persist(comment);
        return comment;
    }

    public void editComment(Long commentId, String newText, User user) {
        Comment comment = entityManager.find(Comment.class, commentId);
        if (comment != null && comment.getUser().equals(user)) {
            comment.setText(newText);
            entityManager.merge(comment);
        }
    }

    public List<Comment> getAllUserComments(User user) {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.user = :user", Comment.class)
                            .setParameter("user", user)
                            .getResultList();
    }

    public Comment getCommentById(Long commentId) {
        return entityManager.find(Comment.class, commentId);
    }
}
