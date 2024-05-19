package Controller;

import Model.Board;
import Model.User;
import Model.UserRole;
import Service.BoardService;
import Service.UserService;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardController {

    @Inject
    private BoardService boardService;

    @Inject
    private UserService userService;

   

    @POST
    public Response createBoard(@QueryParam("name") String name, @QueryParam("creatorId") Long creatorId) {
        User creator = userService.getUserById(creatorId);
        if (creator == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        Response authorizationResponse = boardService.checkTeamLeaderAuthorization(creator);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        Board board = boardService.createBoard(name, creator);
        return Response.ok(board).build();
    }
    @DELETE
    @Path("/{boardId}")
    public Response deleteBoard(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User with ID " + userId + " not found.")
                    .build();
        }

        Response authorizationResponse = boardService.checkTeamLeaderAuthorization(user);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Board with ID " + boardId + " not found.")
                    .build();
        }

        if (!board.getCreator().getId().equals(userId)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Only the creator of the board can delete it.")
                    .build();
        }

        boardService.deleteBoard(boardId);
        return Response.ok().entity("Board with ID " + boardId + " deleted successfully.").build();
    }
    
    @PUT
    @Path("")
    public Response updateBoard(@QueryParam("boardId") Long boardId, @QueryParam("creatorId") Long creatorId,@QueryParam("email") String email) {
    	Board updatedBoard = boardService.getBoardById(boardId);
    	if(updatedBoard ==null) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
    	}
    	 User creator = userService.getUserById(creatorId);
         if (creator == null) {
             return Response.status(Response.Status.NOT_FOUND).entity("There is no team leader with entered ID").build();
         }

         Response authorizationResponse = boardService.checkTeamLeaderAuthorization(creator);
         if (authorizationResponse != null) {
             return authorizationResponse;
         }
         User exsitingUser = userService.getUserByEmail(email) ;
         
         if( exsitingUser==null)
        	 return Response.status(Response.Status.NOT_FOUND).entity("User not found ").build();
         Response Isteamleader = boardService.checkTeamLeaderAuthorization(exsitingUser);
         if(Isteamleader==null) {
        	 return Response.status(Response.Status.UNAUTHORIZED).entity("User role is a team leader can not be invited").build();
         }
        // Adding collaborators to the Board
         boardService.invite(updatedBoard, exsitingUser);
         Board savingBoard = boardService.updateBoard(updatedBoard);
             return Response.ok(savingBoard).build();
    }
    @GET
    @Path("/user/{userId}")
    public Response getBoardsByUser(@PathParam("userId") Long userId, @QueryParam("requesterId") Long requesterId) {
        // Check if the requester is a team leader
        User requester = userService.getUserById(requesterId);
        if (requester == null || requester.getRole() != UserRole.TEAM_LEADER) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Only team leaders can view user boards.")
                    .build();
        }

        // Retrieve the user for whom the boards are being requested
        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User with ID " + userId + " not found.")
                    .build();
        }

        // Retrieve the boards associated with the user
        List<Board> boards = boardService.getBoardsByUser(user);
        if (!boards.isEmpty()) {
        return Response.ok(boards).build();
        }
        else {
        	 return Response.status(Response.Status.NOT_FOUND)
            .entity("This user doesn't collaborate board .")
            .build();
        }
    }
   
    
}