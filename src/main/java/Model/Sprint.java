package Model;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Sprint {
<<<<<<< Updated upstream
    private String id;
    private List<Task> tasks;
    // Other fields

    // Getters and setters
    public String getId() {
=======
	
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private SprintStatus sprintStatus; 
    
    public Long getId() {
>>>>>>> Stashed changes
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SprintStatus getSprintStatus() {
        return sprintStatus;
    }

    public void setSprintStatus(SprintStatus sprintStatus) {
        this.sprintStatus = sprintStatus;
    }
	

//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }
}