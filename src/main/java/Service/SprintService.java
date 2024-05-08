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
import Model.SprintReport;
import Model.SprintStatus;
import Model.Status;
import Model.Task;
import Model.TaskList;

@Stateless

public class SprintService {
   // private Map<Integer, Sprint> sprints;
    private int currentSprintId;

    @PersistenceContext
    private EntityManager entityManager;
    
    

    public Sprint startNewSprint() {
       
        Sprint sprint = new Sprint();
        sprint.setSprintStatus(SprintStatus.CLOSED);
        entityManager.persist(sprint);
        
        return sprint;
    }

//    public Sprint endSprint(Long sprintId) {
//    	Sprint oldSprint = new Sprint();
//    	oldSprint = getSprintById(sprintId);
//    	TaskList list = new TaskList();
//    	List<TaskList> sprintLists = list.
//        return sprint;
//    }

    
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
//   
//    public SprintReport generateSprintReport(Long sprintId) {
//        Sprint sprint = getSprintById(sprintId);
//      // Map<Status, Integer> taskCountByStatus = new HashMap<>();
//        int totalCompletedStoryPoints = 0;
//        int totalUncompletedStoryPoints = 0;
//
//        for (Task task : sprint.getTasks()) {
//            switch (task.getStatus()) {
//                case DONE:
//                    totalCompletedStoryPoints += task.getStoryPoints();
//                    break;
//                default:
//                    totalUncompletedStoryPoints += task.getStoryPoints();
//                    break;
//            }
//            taskCountByStatus.put(task.getStatus(), taskCountByStatus.getOrDefault(task.getStatus(), 0) + 1);
//        }
//
//        return new SprintReport(sprintId, taskCountByStatus, totalCompletedStoryPoints, totalUncompletedStoryPoints);
//    }

   
}
