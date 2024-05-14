
package Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import Model.Sprint;

import Model.SprintStatus;
import Model.Status;
import Model.Task;
import Model.TaskList;

@Stateless

public class SprintService {
    @PersistenceContext
    private EntityManager entityManager;
    
    public Sprint startNewSprint() {
       
        Sprint sprint = new Sprint();
        sprint.setSprintStatus(SprintStatus.OPENED);
        entityManager.persist(sprint);
        
        return sprint;
    }
    
    public Sprint getSprintById(Long sprintId) {
        try {
            TypedQuery<Sprint> query = entityManager.createQuery("SELECT s FROM Sprint s WHERE s.id = :sprintId", Sprint.class);
            query.setParameter("sprintId", sprintId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        }
    }
    
    
    public Sprint getOpenedStatus() {
        try {
            TypedQuery<Sprint> query = entityManager.createQuery("SELECT s FROM Sprint s WHERE s.sprintStatus = :status", Sprint.class);
            query.setParameter("status", SprintStatus.OPENED);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        }
    }

   
}
