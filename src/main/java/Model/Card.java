package Model;

import javax.persistence.*;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    @ManyToOne
    private User assignedUser;

    private int storyPoints;
    @ManyToOne
    private TaskList list;
    private String deadline;
    @OneToOne(fetch = FetchType.EAGER)
    private User creator;
    private boolean completion=false;
    private String title;

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
    public String getCreatorname() {
    	return creator.getName();
    }
    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }
    public boolean getCardStatus() {
    	return completion;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
   
}
