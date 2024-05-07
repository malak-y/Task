package Model;
<<<<<<< Updated upstream
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;

>>>>>>> Stashed changes
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

<<<<<<< Updated upstream
    private String comments;

=======
>>>>>>> Stashed changes
    @ManyToOne
    private User assignedUser;

    @ManyToOne
    private TaskList list;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public TaskList getList() {
        return list;
    }
    
    public void setList(TaskList list) {
        this.list = list;
    }

<<<<<<< Updated upstream
=======
   


>>>>>>> Stashed changes
   
}
