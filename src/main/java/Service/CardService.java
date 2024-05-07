package Service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import Model.Card;
import Model.Comment;
import Model.TaskList;

@Stateless
public class CardService {

    @PersistenceContext
    private EntityManager entityManager;

    public Card createCard(String description, TaskList list ) {
        Card card = new Card();
        card.setDescription(description);
        card.setList(list);
        entityManager.persist(card);
        return card;
    }

    public void deleteCard(Long cardId) {
        Card card = entityManager.find(Card.class, cardId);
        if (card != null) {
            entityManager.remove(card);
        }
    }
    
    public void updateCard(Card card) {
        entityManager.merge(card);
    }
    
    public Card getCardById(Long cardId) {
        return entityManager.find(Card.class, cardId);
    }
    
    public void updateCardList(Long cardId, TaskList newList) {
        Card card = entityManager.find(Card.class, cardId);
        if (card != null) {
            card.setList(newList);
            entityManager.merge(card);
        }
    }
    
    public Card updateCardDescription(Long cardId, String description) {
        Card card = entityManager.find(Card.class, cardId);
        if (card != null) {
            card.setDescription(description);
            entityManager.merge(card);
<<<<<<< Updated upstream
            return card; 
        }
        return null; 
    }

    public Card addCommentToCard(Long cardId, String comment) {
        Card card = entityManager.find(Card.class, cardId);
        if (card != null) {
            String existingComments = card.getComments();
            if (existingComments != null ) {
                existingComments += "\n"; 
            }
            existingComments += comment;
            card.setComments(existingComments);
            entityManager.merge(card);
            return card; 
        }
        return null; 
=======
            return card;
        }
        return null; 
    }
    
    public List<Comment> getAllCommentsForCard(Long cardId) {
        TypedQuery<Comment> query = entityManager.createQuery(
            "SELECT c FROM Comment c WHERE c.card.id = :cardId", Comment.class);
        query.setParameter("cardId", cardId);
        return query.getResultList();
>>>>>>> Stashed changes
    }

}