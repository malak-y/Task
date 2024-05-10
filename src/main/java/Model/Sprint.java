package Model;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sprint {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
    
    @Enumerated(EnumType.STRING)
    private SprintStatus sprintStatus; 
    
    public Long getId() {
        return id;
    }
    @OneToMany(mappedBy="sprint")
    private List<TaskList> tasklist;

    public void setId(Long id) {
        this.id = id;
    }

    public SprintStatus getSprintStatus() {
        return sprintStatus;
    }

    public void setSprintStatus(SprintStatus sprintStatus) {
        this.sprintStatus = sprintStatus;
    }
	

}