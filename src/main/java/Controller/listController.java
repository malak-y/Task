package Controller;

import Model.Board;
import Model.TaskList;
import Model.User;
import Model.UserRole;
import Service.BoardService;
import Service.UserService;
import Service.listService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/lists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class listController {

    @Inject
    private listService listservice;

    @Inject
    private UserService userService;

    @Inject
    private BoardService boardService;
    
    private Response checkTeamLeaderAuthorization(User user) {
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized access").build();
        }

        if (user.getRole() != UserRole.TEAM_LEADER) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Only team leaders can perform this action").build();
        }

        return null;
    }

    @POST
    public Response createList(@QueryParam("name") String name, @QueryParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        Response authorizationResponse = checkTeamLeaderAuthorization(user);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
        }

        TaskList newList = listservice.createList(name, board);
        return Response.ok(newList).build();
    }

    @DELETE
    @Path("/{listId}")
    public Response deleteList(@PathParam("listId") Long listId, @QueryParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User with ID " + userId + " not found.")
                    .build();
        }

        Response authorizationResponse = checkTeamLeaderAuthorization(user);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        // Check if the list exists before attempting to delete
        TaskList listToDelete = listservice.getListById(listId);
        if (listToDelete == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("List with ID " + listId + " not found.")
                    .build();
        }

        listservice.deleteList(listId);
        return Response.ok().entity("List with ID " + listId + " deleted successfully.").build();
    }

}
