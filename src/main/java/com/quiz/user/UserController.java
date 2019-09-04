package com.quiz.user;

import com.quiz.common.utils.ThreadExecutor;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Path("/users")
@Consumes({MediaType.APPLICATION_JSON})
public class UserController {

    private static final ExecutorService executorService = new ThreadExecutor().getExecutor();
    private static final Logger LOG = Logger.getLogger(UserController.class);
    private UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Context
    UriInfo uriInfo;

    public UserController() {
        LOG.info("Invoked ==> " + this.getClass().getName());
    }

    @GET
    public void getAllUsers(@Suspended AsyncResponse asyncResponse) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        CompletableFuture<Void> future =CompletableFuture.supplyAsync(() -> userServiceImpl.getAllUsers(uriPath), executorService).thenAccept(asyncResponse::resume);
    }

    @GET
    @Path("{userId: [0-9]*}")
    public void getUser(@Suspended AsyncResponse asyncResponse,@PathParam("userId") int userId) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        CompletableFuture<Void> future =CompletableFuture.supplyAsync(() -> userServiceImpl.getUser(uriPath,userId), executorService).thenAccept(asyncResponse::resume);
    }

    @POST
    public void addUser(@Suspended AsyncResponse asyncResponse, UserBean userBean){
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        CompletableFuture<Void> future =CompletableFuture.supplyAsync(() -> userServiceImpl.addUser(uriPath,userBean), executorService).thenAccept(asyncResponse::resume);

    }

    @PUT
    @Path("{userId : [0-9]*}")
    public void updateUser(@Suspended AsyncResponse asyncResponse, UserBean userBean,@PathParam("userId") int userId){
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        CompletableFuture<Void> future =CompletableFuture.supplyAsync(() -> userServiceImpl.updateUSer(uriPath,userBean,userId), executorService).thenAccept(asyncResponse::resume);

    }
}
