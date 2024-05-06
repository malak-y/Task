package Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Model.Sprint;
import Model.SprintReport;
import Service.SprintService;

@Path("/sprints")
public class SprintController {
    private SprintService sprintService;

    public SprintController() {
        this.sprintService = new SprintService();
    }

    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startNewSprint(Sprint sprint) {
        Sprint newSprint = sprintService.startNewSprint(sprint.getId());
        return Response.status(Response.Status.CREATED).entity(newSprint).build();
    }

    @POST
    @Path("/{sprintId}/end")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endSprint(@PathParam("sprintId") int sprintId) {
        Sprint endedSprint = sprintService.endSprint(sprintId);
        return Response.status(Response.Status.OK).entity(endedSprint).build();
    }

    @GET
    @Path("/{sprintId}/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateReport(@PathParam("sprintId") int sprintId) {
        SprintReport report = sprintService.generateSprintReport(sprintId);
        return Response.status(Response.Status.OK).entity(report).build();
    }
}
