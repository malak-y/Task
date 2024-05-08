package Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import Model.Card;
import Model.User;

public class SearchService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Card> SearchForCardByAssignee(User user) {
		TypedQuery<Card> query =  entityManager.createQuery(
				"SELECT c FROM Card c WHERE c.assignedUser = :assignedUser", Card.class);
		         query.setParameter("assignedUser", user);
		         return query.getResultList();
				}
	public List<Card> SearchForCardBydescription(String description) {
		TypedQuery<Card> query =  entityManager.createQuery(
				"SELECT c FROM Card c WHERE c.description = :description", Card.class);
		         query.setParameter("description", description);
		         return query.getResultList();
				}
	public List<Card> SearchForCardByReporter(User user) {
		TypedQuery<Card> query =  entityManager.createQuery(
				"SELECT c FROM Card c WHERE c.creator = :creator", Card.class);
		         query.setParameter("creator", user);
		         return query.getResultList();
				}
	public List<Card> SearchForCardBydeadline(String deadline) {
		TypedQuery<Card> query =  entityManager.createQuery(
				"SELECT c FROM Card c WHERE c.deadline = :deadline", Card.class);
		         query.setParameter("deadline", deadline);
		         return query.getResultList();
				}
	
	

}
