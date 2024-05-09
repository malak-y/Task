package Service;

import Model.Board;
import Model.Sprint;
import Model.Status;
import Model.TaskList;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class listService {

    @PersistenceContext
    private EntityManager entityManager;

    
    public TaskList createList(String name, Board board , Sprint sprint) {
//      Long id =(long) 1;
  	TaskList list = new TaskList();
//      list =getListById(id);
//      if(list.equals(null)) {
//      	Sprint sprint = new Sprint(); 
//      	sprint= sprint.startNewSprint();
//      	list.setSprint(sprint);
//      }
      
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
      list.setSprint(sprint);
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
    
    public List<TaskList> getTaskListsBySprintId(Long id) {
        try {
            TypedQuery<TaskList> query = entityManager.createQuery("SELECT l FROM TaskList l WHERE l.sprint.id = :sprintId", TaskList.class);
            query.setParameter("sprintId", id);
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
    
    public TaskList updateList(TaskList updatedList) {
        TaskList list = entityManager.find(TaskList.class, updatedList.getId());
        if (list != null) {
            list.setStatus(updatedList.getStatus());
            list.setBoard(updatedList.getBoard());
            list.setSprint(updatedList.getSprint());
            entityManager.merge(list);
        }
        return list;
    }

    
}
