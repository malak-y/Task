package Service;

<<<<<<< Updated upstream
=======
import java.util.Collections;
import java.util.List;

>>>>>>> Stashed changes
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import Model.Board;
import Model.Card;
import Model.TaskList;
import Model.User;

@Stateless
public class CardService {

    @PersistenceContext
    private EntityManager entityManager;

    public Card createCard(String description, TaskList list,User user ) {
        Card card = new Card();
        card.setDescription(description);
        card.setList(list);
        card.setCreator(user);
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
            return card; // Return the updated card
        }
        return null; // Return null if the card is not found
    }
<<<<<<< Updated upstream

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
            return card; // Return the updated card
        }
        return null; // Return null if the card is not found
    }


=======
   public Card assignDeadline(String date,Long cardId) {
	   Card card = entityManager.find(Card.class, cardId);
       if (card != null) {
           card.setdeadline(date);
           entityManager.merge(card);
           return card; // Return the updated card
       }
       return null; // Return null if the card is not found
   }
<<<<<<< Updated upstream
public List<Comment> getAllCommentsForCard(Long cardId){
	TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.card.id = :cardId", Comment.class); 
	query.setParameter("cardId", cardId); 
   return query.getResultList();
}
>>>>>>> Stashed changes
=======
	public List<Comment> getAllCommentsForCard(Long cardId){
		TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.card.id = :cardId", Comment.class); 
		query.setParameter("cardId", cardId); 
	   return query.getResultList();
	}
	
	public List<Card> getAllCardsForList(Long listId) {
        try {
            TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.list.id = :listId", Card.class);
            query.setParameter("listId", listId);
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList(); 
        }
    }
>>>>>>> Stashed changes
}
