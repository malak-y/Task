package Service;

import Model.Board;
import Model.User;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Stateless
public class BoardService {

    @PersistenceContext
    private EntityManager entityManager;

    public Board createBoard(String name, User creator) {
        Board board = new Board();
        board.setName(name);
        board.setCreator(creator);
        board.inviteCollaborator(creator);
        entityManager.persist(board);
        return board;
    }
    public Board getBoardById(Long boardId) {
        return entityManager.find(Board.class, boardId);
    }
    public void deleteBoard(Long boardId) {
        Board board = entityManager.find(Board.class, boardId);
        if (board != null) {
            entityManager.remove(board);
        }
    }
    public Board updateBoard(Board board ) {
    	return entityManager.merge(board);
    	
    }
    public void invite (Board board, User user) {
    	board.inviteCollaborator(user);
    }
    
    public List<Board> getBoardsByUser(User user) {
        TypedQuery<Board> query = entityManager.createQuery(
                "SELECT b FROM Board b WHERE :user MEMBER OF b.collaborators", Board.class)
                .setParameter("user", user);
        return query.getResultList();
    }
    public boolean isCollaboratorOnBoard(Long userId ,Long boardId) {
        Board board = getBoardById(boardId);
        if (board != null) {
            for (User collaborator : board.getcollaborators()) {
                if (collaborator.getId().equals(userId)) {
                    return true; 
                }
            }
        }
        return false; 
    }
}