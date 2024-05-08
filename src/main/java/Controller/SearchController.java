package Controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.Card;
import Model.User;
import Service.SearchService;
import Service.UserService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchController {
	@Inject
	private SearchService searchService;
	
	@Inject
	private UserService userService;
	
	@GET
	@Path("/byAssignee")
	public Response getcardbyAssignee(@QueryParam("userId") Long userId) {
		User user=userService.getUserById(userId);
		if(user==null) {
			return Response.status(Response.Status.NOT_FOUND)
            .entity("User is not found.")
            .build();
		}
		List<Card> tasks = searchService.SearchForCardByAssignee(user);
		if(!tasks.isEmpty()) {
			return Response.ok(tasks).build();
		}
			return Response.status(Response.Status.NOT_FOUND).entity("No Results").build();
			}
		@GET
		@Path("/bydescription")
		public Response getcardbydescription(@QueryParam("description") String description) {
			List<Card> tasks = searchService.SearchForCardBydescription(description);
			if(!tasks.isEmpty()) {
				return Response.ok(tasks).build();
			}
				return Response.status(Response.Status.NOT_FOUND)
			            .entity("No results")
			            .build();
			
	}
		@GET
		@Path("/byCreator")
		public Response getcardbyCretor(@QueryParam("userId") Long userId) {
			User user=userService.getUserById(userId);
			if(user==null) {
				return Response.status(Response.Status.NOT_FOUND)
	            .entity("User is not found.")
	            .build();
			}
			List<Card> tasks = searchService.SearchForCardByReporter(user);
			if(!tasks.isEmpty()) {
				return Response.ok(tasks).build();
			}
				return Response.status(Response.Status.NOT_FOUND).entity("No Results").build();
				}
		@GET
		@Path("/bydeadline")
		public Response getcardbydeadline(@QueryParam("deadline") String deadline) {
			List<Card> tasks = searchService.SearchForCardBydeadline(deadline);
			if(!tasks.isEmpty()) {
				return Response.ok(tasks).build();
			}
				return Response.status(Response.Status.NOT_FOUND)
			            .entity("No results")
			            .build();
		}
			
		

}