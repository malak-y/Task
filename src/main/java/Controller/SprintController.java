package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Model.Card;
import Model.Sprint;
import Model.SprintReport;
import Model.SprintStatus;
import Model.Status;
import Model.Task;
import Model.TaskList;
import Service.CardService;
import Service.SprintService;
import Service.listService;

@Path("/sprints")
public class SprintController {
	
	@Inject
    private SprintService sprintService;

	@Inject
	private listService listservice;
	
	@Inject
	private CardService cardService;
	
	
    public SprintController() {
        this.sprintService = new SprintService();
    }



    @POST
    @Path("/{sprintId}/end")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endSprint(@PathParam("sprintId") Long sprintId) {
        try {
            Sprint oldSprint = sprintService.getSprintById(sprintId);
            if (oldSprint == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Sprint not found").build();
            }

            oldSprint.setSprintStatus(SprintStatus.CLOSED);
            Sprint newSprint = sprintService.startNewSprint();
            if (newSprint == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to start a new sprint").build();
            }

            List<TaskList> sprintLists = listservice.getTaskListsBySprintId(sprintId);
            if (sprintLists.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No task lists found for the sprint").build();
            }

            for (TaskList list : sprintLists) {
                if (list.getStatus() != Status.DONE) {
                    list.setSprint(newSprint);
                }
            }

            return Response.status(Response.Status.OK).entity(newSprint).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred: " + e.getMessage()).build();
        }
    }

    

    @GET
    @Path("/{sprintId}/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateSprintReport(@PathParam("sprintId") Long sprintId) {
        try {
            Sprint sprint = sprintService.getSprintById(sprintId);
            if (sprint == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Sprint not found").build();
            }

            List<TaskList> taskLists = listservice.getTaskListsBySprintId(sprintId);
            if (taskLists.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No task lists found for the sprint").build();
            }

            int totalCompletedStoryPoints = 0;
            int totalUncompletedStoryPoints = 0;

            for (TaskList taskList : taskLists) {
                for (Card task : cardService.getAllCardsForList(taskList.getId())) {
                    switch (taskList.getStatus()) {
                        case DONE:
                            totalCompletedStoryPoints ++;
                            break;
                        default:
                            totalUncompletedStoryPoints ++;
                            break;
                    }
                }
            }


            Map<String, Integer> taskCounts = new HashMap<>();
            taskCounts.put("totalCompletedStoryPoints", totalCompletedStoryPoints);
            taskCounts.put("totalUncompletedStoryPoints", totalUncompletedStoryPoints);

            return Response.status(Response.Status.OK).entity(taskCounts).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred: " + e.getMessage()).build();
        }
    }


}
