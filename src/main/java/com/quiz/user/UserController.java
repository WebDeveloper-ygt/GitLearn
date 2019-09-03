package com.quiz.user;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.quiz.utils.ThreadExecutor;

import org.apache.log4j.Logger;

@Path("/users")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class);
    private static final UserServiceImpl userServiceImpl = new UserServiceImpl();
    private ExecutorService executorService = new ThreadExecutor().getExecutor(5);

    private Response userCrud;

    @Context
    UriInfo uriInfo;
    
    
    public UserController() {
        LOG.info("Invoked :: " + this.getClass().getName());
    }

    @GET
    public void getAllUsers(@Suspended AsyncResponse asyncResponse) {
        String info=uriInfo.getAbsolutePath().toString();
        System.out.println(info);
        CompletableFuture.supplyAsync(() -> {
            try {
                userCrud = userServiceImpl.getAllUsers(info);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
            return userCrud;
        }, executorService).thenAccept(response -> asyncResponse.resume(userCrud));
    }

    @GET
    @Path("{userId : [0-9]*}")
    public void getUsers(@Suspended AsyncResponse asyncResponse, @PathParam("userId") int userId) {
        String info=uriInfo.getAbsolutePath().toString();
        System.out.println(info);
        CompletableFuture.supplyAsync(() -> {
            try {
                userCrud = userServiceImpl.getUser(info,userId);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
            return userCrud;
        }, executorService).thenAccept(response -> asyncResponse.resume(userCrud));
    }

    @GET
    @Path("/exams")
    public void getQuery(@QueryParam("start") int start, @QueryParam("end") int end){
        
    }
}
