package Model;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String comments;

    @ManyToOne
    private User assignedUser;

    @ManyToOne
    private TaskList list;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
    
>>>>>>> Stashed changes
    private String deadline;
    @OneToOne(fetch = FetchType.EAGER)
    private User creator;
    private boolean completion=false;

<<<<<<< Updated upstream
>>>>>>> Stashed changes

//    @ManyToOne
//    private Board board; 
=======
    private int storyPoints;
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

//    public void setBoard(Board board) {
//        this.board = board;
//    }
//    
//    public Board getBoard() {
//        return board;
//    }

=======
    public void setTaskDone() {
    	completion = true;
    }
    public void setdeadline(String deadline) {
    	this.deadline=deadline;
    }
    public String getDeadline() {
    	return deadline;
    }
    public void setCreator(User creator) {
    	this.creator = creator;
    }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
    
    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }
>>>>>>> Stashed changes
   
}