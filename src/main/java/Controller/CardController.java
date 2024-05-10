package Controller;

import java.util.HashMap;
import java.util.Map;

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
    @Path("/{listId}")
    public Response createCard(@PathParam("listId") Long listId, @QueryParam("description") String description, @QueryParam("title")String title ,@QueryParam("userId") Long userId ,@QueryParam("storyPoints") int storyPoints ) {
        TaskList list = listService.getListById(listId);
        if (list == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("List not found").build();
        }
        User creator = userService.getUserById(userId);
        if (creator == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        if (!boardService.isCollaboratorOnBoard(list.getBoard().getId(),userId)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to create card in this list.").build();
        }
        

        Card newCard = cardService.createCard(description, list,creator , storyPoints , title);
        
        Map<String, Object> CardReport = new HashMap<>();
        CardReport.put("Card Id", newCard.getId());
        CardReport.put("Description", newCard.getDescription());
        CardReport.put("TaskList Id", newCard.getList().getId());
        CardReport.put("TaskList status",newCard.getList().getStatus());
        CardReport.put("sprint ",newCard.getList().getSprint());
        CardReport.put("Board Id",newCard.getList().getBoard().getId());
        CardReport.put("Assigned User", newCard.getAssignedUser());
        CardReport.put("Title", newCard.getTitle());
        CardReport.put("StoryPoints", newCard.getStoryPoints());

        return Response.status(Response.Status.OK).entity(CardReport).build();
       // return Response.ok(newCard).build();
    }

    @PUT
    @Path("/move/{cardId}/{newListId}")
    public Response moveCardToList(@PathParam("cardId") Long cardId, @PathParam("newListId") Long newListId, @QueryParam("userId") Long userId) {
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
    @Path("/{cardId}")
    public Response deleteCard(@PathParam("cardId") Long cardId, @QueryParam("userId") Long userId) {
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
    @Path("assign/{cardId}")
    public Response assignCard(@PathParam("cardId") Long cardId, @QueryParam("userId") Long userId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        // Check if the user is authorized (a collaborator on the associated board)
        if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to assign this card.").build();
        }

        card.setAssignedUser(user);
        cardService.updateCard(card);
        return Response.ok().entity("Card assigned to user " + user.getName()).build();
    }

    @PUT
    @Path("/description/{cardId}")
    public Response updateCardDescription(@PathParam("cardId") Long cardId, String description, @QueryParam("userId") Long userId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

         if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to update card description.").build();
        }

        card.setDescription(description);
        cardService.updateCard(card);
        return Response.ok().entity("Card description updated successfully.").build();
    }
    @PUT
    @Path("/setdeadline")
    public Response setDeadline(@QueryParam("cardId") Long cardId, @QueryParam("userId") Long userId,@QueryParam("deadline") String date) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("There is no team leader with this id").build();
        }
        // Check if the user is the creator of this board
        if ( !card.getList().getBoard().getCreator().getId().equals(user.getId())){
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to set the deadline of this card.").build();
        }
        card.setdeadline(date);
        cardService.updateCard(card);
        return Response.ok().entity("Card's deadline is " + card.getDeadline()).build();
    }
    @PUT
    @Path("/title/{cardId}")
    public Response addCardTitle(@PathParam("cardId") Long cardId, @QueryParam("title")String title, @QueryParam("userId") Long userId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

         if (!boardService.isCollaboratorOnBoard(userId, card.getList().getBoard().getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authorized to update card description.").build();
        }
        card.setTitle(title);
        cardService.updateCard(card);
        return Response.ok().entity("Card title added successfully.").build();
    }
    
    @GET
    @Path("/{cardId}")
    public Response getCardById(@PathParam("cardId") Long cardId) {
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Card not found").build();
        }

        Map<String, Object> cardInfo = new HashMap<>();
        cardInfo.put("Card Id", card.getId());
        cardInfo.put("Description", card.getDescription());
        cardInfo.put("TaskList Id", card.getList().getId());
        cardInfo.put("TaskList status", card.getList().getStatus());
        cardInfo.put("Sprint", card.getList().getSprint());
        cardInfo.put("Board Id", card.getList().getBoard().getId());
        cardInfo.put("Assigned User", card.getAssignedUser());
        cardInfo.put("Title", card.getTitle());
        cardInfo.put("StoryPoints", card.getStoryPoints());


        return Response.ok(cardInfo).build();
    }

}
