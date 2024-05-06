package Controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.User;
import Service.UserService;

//UserController class
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class UserController {

 @Inject
 private UserService userService;

 
 @POST
 @Path("/register")
 public Response registerUser(User user) {
     // Check if a user with the provided email already exists
     User existingUser = userService.getUserByEmail(user.getEmail());
     if (existingUser != null) {
         return Response.status(Response.Status.CONFLICT)
                 .entity("User with email " + user.getEmail() + " already registered.")
                 .build();
     }

     User registeredUser = userService.registerUser(user);
     // Return the registered user object in the response
     return Response.ok(registeredUser).build();
 }


 @POST
 @Path("/login")
 public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
     User user = userService.login(email, password);
     if (user != null) {
         // User successfully logged in
         return Response.ok()
                 .entity(user) // Return the user object in the response
                 .build();
     } else {
         // Check if the email exists in the database
         User existingUser = userService.getUserByEmail(email);
         if (existingUser != null) {
             // Email exists, so the password must be incorrect
             return Response.status(Response.Status.UNAUTHORIZED)
                     .entity("Incorrect password.")
                     .build();
         } else {
             // Email doesn't exist, so the user is not registered
             return Response.status(Response.Status.UNAUTHORIZED)
                     .entity("User with email " + email + " not registered.")
                     .build();
         }
     }
 }




 @PUT
 @Path("/{userId}")
 public Response updateUser(@PathParam("userId") Long userId, User user) {
     // Check if the user exists
     User existingUser = userService.getUserById(userId);
     if (existingUser == null) {
         // User not found, return 404 Not Found response
         return Response.status(Response.Status.NOT_FOUND)
                 .entity("User with ID " + userId + " not found.")
                 .build();
     }

     // Ensure that the ID in the request body matches the ID in the path
     if (!userId.equals(user.getId())) {
         // If IDs don't match, return 400 Bad Request response
         return Response.status(Response.Status.BAD_REQUEST)
                 .entity("Invalid request. User ID in the path does not match the ID in the request body.")
                 .build();
     }

     // Update the user
     User updatedUser = userService.updateUser(user);
     // Return 200 OK response with the updated user
     return Response.ok(updatedUser).build();
 }


 @GET
 @Path("/{userId}")
 public Response getUserById(@PathParam("userId") Long userId) {
     User user = userService.getUserById(userId);
     if (user != null) {
         return Response.ok(user).build();
     } else {
         return Response.status(Response.Status.NOT_FOUND)
                 .entity("User with ID " + userId + " not registered.")
                 .build();
     }
 }


 @DELETE
 @Path("/{userId}")
 public Response deleteUser(@PathParam("userId") Long userId) {
     userService.deleteUser(userId);
     return Response.ok().build();
 }
}
