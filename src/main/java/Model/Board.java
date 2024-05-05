package Model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private User creator;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "BoardxUser",
        joinColumns = @JoinColumn(name = "boardID"),
        inverseJoinColumns = @JoinColumn(name = "userID")
    )
    private List<User> collaborators;
    public Board() {
        collaborators = new ArrayList<>();
       }

    
       public Long getId() {
        return board_id;
    }

    public void setId(Long id) {
        this.board_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
   public List<User> getcollaborators(){
	   return collaborators;
   }
    public void inviteCollaborator(User user) {
    	collaborators.add(user);
    }
    
}