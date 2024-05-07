package Service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import Model.Board;
import Model.Card;
import Model.TaskList;

@Stateless
public class CardService {

    @PersistenceContext
    private EntityManager entityManager;

    public Card createCard(String description, TaskList list ) {
        Card card = new Card();
        card.setDescription(description);
        card.setList(list);
        //card.setBoard(board);
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

    public Card addCommentToCard(Long cardId) {
        Card card = entityManager.find(Card.class, cardId);
        if (card != null) {
           
    
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
   }


