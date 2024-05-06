package Model;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public static Map<String, User> comments = new HashMap<String,User >();

    @ManyToOne
    private User assignedUser;

    @ManyToOne
    private TaskList list;



    
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


   
}
