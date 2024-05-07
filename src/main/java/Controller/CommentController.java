package Controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.Card;
import Model.Comment;
import Model.User;
import Service.CommentService;
import Service.UserService;
import Service.BoardService;
import Service.CardService;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentController {

    @Inject
    private CommentService commentService;

    @Inject
    private UserService userService;

    @Inject
    private BoardService boardService;
    
    @Inject
    private CardService cardService;

    @POST
    @Path("/{cardId}")
    public Response addCommentToCard(@PathParam("cardId") Long cardId, String text, @QueryParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

        if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to add comment to this card.").build();
        }

        
        Comment comment = commentService.addCommentToCard(cardId, text, user);
        if (comment != null) {
            return Response.status(Response.Status.CREATED).entity(comment).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }
    }



    @PUT
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editComment(@PathParam("commentId") Long commentId, String newText, @QueryParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Comment not found").build();
        }

        if (!comment.getUser().getId().equals(userId)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to edit this comment.").build();
        }

        commentService.editComment(commentId, newText, user);

        Comment updatedComment = commentService.getCommentById(commentId);
        if (updatedComment != null) {
            return Response.ok(updatedComment).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to retrieve updated comment.").build();
        }
    }
    
    @GET
    @Path("/card/{cardId}")
    public Response getAllCommentsForCard(@PathParam("cardId") Long cardId, @QueryParam("userId") Long userId) {
       
    	User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }
    	
        if (!boardService.isCollaboratorOnBoard(userId,card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to view comments for this card.").build();
        }

        List<Comment> comments = cardService.getAllCommentsForCard(cardId);
        if (comments != null) {
            return Response.ok(comments).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }
    }


}
