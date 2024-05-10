package Service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import AutomaticMessage.FixedTimerDelay;
import MessageSystem.JMSClient;
import Model.Card;
import Model.User;
@Stateless
public class SearchService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Inject 
	private JMSClient client;
	@Inject
	private FixedTimerDelay fixedtimerdelay;
	
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
	public List<Card> SearchForCardBydeadline(String deadline) throws NullPointerException  {
		TypedQuery<Card> query =  entityManager.createQuery(
				"SELECT c FROM Card c WHERE c.deadline = :deadline", Card.class);
		         query.setParameter("deadline", deadline);
		         for(Card card:query.getResultList()) {
			        	try {
							fixedtimerdelay.doSchedule();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			        	if (card.getAssignedUser()!=null)
			        	client.sendMessage("Notification -> There is a new message to "+card.getAssignedUser().getName()+" please finish this task with this id ::"+card.getId()+" before the deadline "+card.getDeadline());
			        }
		         return query.getResultList();
				}
	public List<Card> SearchForCardByStatus(boolean completion) {
		TypedQuery<Card> query =  entityManager.createQuery(
				"SELECT c FROM Card c WHERE c.completion = :completion", Card.class);
		         query.setParameter("completion", completion);
		        for(Card card:query.getResultList()) {
		        	try {
						fixedtimerdelay.doSchedule();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        	client.sendMessage("Notification -> There is a new message to "+card.getCreatorname()+" Take care this task with this id ::"+card.getId()+" is not completed");
		        }
		         return query.getResultList();
				}
	public List<Card> SearchForCardBytitle(String title) {
		TypedQuery<Card> query =  entityManager.createQuery(
				"SELECT c FROM Card c WHERE c.title = :title", Card.class);
		         query.setParameter("title", title);
		         return query.getResultList();
				}
	
	

}
