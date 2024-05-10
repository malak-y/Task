package Service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import Model.Card;
import Model.Comment;
import Model.TaskList;
import Model.User;

@Stateless
public class CardService {

    @PersistenceContext
    private EntityManager entityManager;

    public Card createCard(String description, TaskList list,User user ,int storyPoints , String title) {
        Card card = new Card();
        card.setDescription(description);
        card.setList(list);
        card.setCreator(user);
        card.setStoryPoints( storyPoints) ;
        card.setTitle(title);
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
   public Card assignDeadline(String date,Long cardId) {
	   Card card = entityManager.find(Card.class, cardId);
       if (card != null) {
           card.setdeadline(date);
           entityManager.merge(card);
           return card; // Return the updated card
       }
       return null; // Return null if the card is not found
   }
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
	public Card updateCardtitle(Long cardId, String title) {
        Card card = entityManager.find(Card.class, cardId);
        if (card != null) {
            card.setTitle(title);
            entityManager.merge(card);
            return card; // Return the updated card
        }
        return null; // Return null if the card is not found
    }
}


