package com.quiz.user;

import com.quiz.exception.CustomException;
import com.quiz.exception.ExceptionOccurred;
import com.quiz.utils.ThreadExecutor;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        CompletableFuture.supplyAsync(() -> {
            try {
                userCrud = userServiceImpl.getAllUsers(uriInfo);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
            return userCrud;
        }, executorService).thenAccept(response -> asyncResponse.resume(userCrud));
    }
}
