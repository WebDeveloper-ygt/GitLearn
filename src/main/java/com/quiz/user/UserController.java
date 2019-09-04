package com.quiz.user;

import com.quiz.common.exception.CustomException;
import com.quiz.common.utils.ThreadExecutor;
import org.apache.log4j.Logger;

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
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserController {

    private static final ExecutorService executorService = new ThreadExecutor().getExecutor();
    private static final Logger LOG = Logger.getLogger(UserController.class);
    private static final UserServiceImpl userServiceImpl = new UserServiceImpl();

    private Response userCrud;

    @Context
    UriInfo uriInfo;

    public UserController() {
        LOG.info("Invoked ==> " + this.getClass().getName());
    }

    @GET
    public void getAllUsers(@Suspended AsyncResponse asyncResponse) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        CompletableFuture<Void> future =CompletableFuture.supplyAsync(() -> {
            userCrud=userServiceImpl.getAllUsers(uriPath);
            return userCrud;
        }, executorService).thenAccept(response -> asyncResponse.resume(response));
    }
}
