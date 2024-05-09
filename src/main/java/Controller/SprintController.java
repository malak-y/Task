package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Model.Card;
import Model.Sprint;

import Model.SprintStatus;
import Model.Status;
import Model.Task;
import Model.TaskList;
import Service.CardService;
import Service.SprintService;
import Service.listService;

@Path("/sprints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
                    listservice.updateList(list);
                }
            }

            return Response.status(Response.Status.OK).entity(newSprint).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred: " + e.getMessage()).build();
        }
    }    

    @GET
    @Path("/{sprintId}/report")
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

            Long totalCompletedStoryPoints = (long) 0;
            Long totalUncompletedStoryPoints = (long) 0;

            for (TaskList taskList : taskLists) {
                for (Card task : cardService.getAllCardsForList(taskList.getId())) {
                    switch (taskList.getStatus()) {
                        case DONE:
                            totalCompletedStoryPoints += task.getStoryPoints();
                            break;
                        default:
                            totalUncompletedStoryPoints += task.getStoryPoints();
                            break;
                    }
                }
            }


            Map<String, Object> sprintReport = new HashMap<>();
            sprintReport.put("SprintId", sprint.getId());
            sprintReport.put("SprintName", sprint.getSprintStatus());
            sprintReport.put("TotalCompletedStoryPoints", totalCompletedStoryPoints);
            sprintReport.put("TotalUncompletedStoryPoints", totalUncompletedStoryPoints);


            return Response.status(Response.Status.OK).entity(sprintReport).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred: " + e.getMessage()).build();
        }
    }


}


