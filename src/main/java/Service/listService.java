package Service;

import Model.Board;
import Model.Status;
import Model.TaskList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class listService {

    @PersistenceContext
    private EntityManager entityManager;

    
    public TaskList createList(String name, Board board) {
        TaskList list = new TaskList();
       name = name.toLowerCase();
        if( name.equals("done")) {
        	list.setStatus(Status.DONE);
        }
        else if(name.equals("in_progress")) {
        	list.setStatus(Status.IN_PROGRESS);
        }
        else if(name.equals("testing")) {
        	list.setStatus(Status.TESTING);
        }
        else {// by default the list created as TODO list
        	list.setStatus(Status.TODO);
        }
        list.setBoard(board);
        entityManager.persist(list);
        return list;
    }

    public void deleteList(Long listId) {
        TaskList list = entityManager.find(TaskList.class, listId);
        if (list != null) {
            entityManager.remove(list);
        }
    }
    public TaskList getListById(Long listId) {
        try {
            TypedQuery<TaskList> query = entityManager.createQuery("SELECT l FROM TaskList l WHERE l.id = :listId", TaskList.class);
            query.setParameter("listId", listId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no list found with the given ID
        }
    }
}
