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

//    @ManyToOne
//    private Board board; 

    
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

//    public void setBoard(Board board) {
//        this.board = board;
//    }
//    
//    public Board getBoard() {
//        return board;
//    }

   
}
