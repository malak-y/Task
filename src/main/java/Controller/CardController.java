package Controller;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.Card;
import Model.TaskList;
import Model.User;
import Service.CardService;
import Service.UserService;
import Service.BoardService;
import Service.listService;

@Path("/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardController {

    @Inject
    private listService listService;

    @Inject
    private CardService cardService;

    @Inject
    private UserService userService;
    
    @Inject
    private BoardService boardService;

    @POST
    @Path("/{listId}/{userId}")
    public Response createCard(@PathParam("listId") Long listId, String description, @PathParam("userId") Long userId) {
        TaskList list = listService.getListById(listId);
        if (list == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("List not found").build();
        }

        if (!boardService.isCollaboratorOnBoard(userId, list.getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to create card in this list.").build();
        }

        Card newCard = cardService.createCard(description, list);
        return Response.ok(newCard).build();
    }

    @PUT
    @Path("/move/{cardId}/{newListId}/{userId}")
    public Response moveCardToList(@PathParam("cardId") Long cardId, @PathParam("newListId") Long newListId, @PathParam("userId") Long userId) {
        TaskList newList = listService.getListById(newListId);
        if (newList == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("New list not found").build();
        }

        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

        if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to move card to this list.").build();
        }

        cardService.updateCardList(cardId, newList);
        return Response.ok().entity("Card moved to new list successfully.").build();
    }

    @DELETE
    @Path("/{cardId}/{userId}")
    public Response deleteCard(@PathParam("cardId") Long cardId, @PathParam("userId") Long userId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

         if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to delete this card.").build();
        }

        cardService.deleteCard(cardId);
        return Response.ok().entity("Card deleted successfully.").build();
    }

    @PUT
    @Path("assign/{cardId}/{userId}")
    public Response assignCard(@PathParam("cardId") Long cardId, @PathParam("userId") Long userId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to assign this card.").build();
        }

        card.setAssignedUser(user);
        cardService.updateCard(card);
        return Response.ok().entity("Card assigned to user " + user.getName()).build();
    }

    @PUT
    @Path("/description/{cardId}/{userId}")
    public Response updateCardDescription(@PathParam("cardId") Long cardId, String description, @PathParam("userId") Long userId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

         if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to update card description.").build();
        }

        card.setDescription(description);
        cardService.updateCard(card);
        return Response.ok(card).entity("Card description updated successfully.").build();
    }

    @PUT
    @Path("/comments/{cardId}/{userId}")
    public Response addCommentToCard(@PathParam("cardId") Long cardId, String comment, @PathParam("userId") Long userId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

        if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to add comment to card.").build();
        }

        String existingComments = card.getComments();
        if (existingComments != null) {
            existingComments += "\n";
        }
        existingComments += comment;
        card.setComments(existingComments);
        cardService.updateCard(card);
        return Response.ok(card).entity("Comment added to card successfully.").build();
    }

}
