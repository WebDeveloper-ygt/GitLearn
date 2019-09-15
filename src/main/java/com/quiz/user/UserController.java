package com.quiz.user;

import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.ApiUtils;
import com.quiz.common.utils.ThreadExecutor;
import com.quiz.exam.ExamController;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Path("/users")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserController {

    private static final ExecutorService executorService = new ThreadExecutor().getExecutor();
    private static final Logger LOG = Logger.getLogger(UserController.class);
    private UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    public UserController() {
        LOG.info("Invoked ==> " + this.getClass().getName());
    }

    @GET
    public void getAllUsers(@Suspended AsyncResponse asyncResponse) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() ->{
            Response response=userServiceImpl.getAllUsers(uriPath);
            return ApiUtils.NotModiedChecker(response,request,"userBean");
        }, executorService).thenAccept(asyncResponse::resume);
    }

    @GET
    @Path("{userId: [0-9]*}")
    public void getUser(@Suspended AsyncResponse asyncResponse,@PathParam("userId") int userId) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() ->{
            Response response = userServiceImpl.getUser(uriPath, userId);
            return ApiUtils.NotModiedChecker(response,request,"userBean");
        }, executorService).thenAccept(asyncResponse::resume);
    }

    @POST
    public void addUser(@Suspended AsyncResponse asyncResponse, UserBean userBean){
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() ->  userServiceImpl.addUser(uriPath, userBean), executorService).thenAccept(asyncResponse::resume);

    }

    @DELETE
    @Path("{userId : [0-9]*}")
    public void delateUser(@Suspended AsyncResponse asyncResponse,@PathParam("userId") int userId){
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> userServiceImpl.deleteUser(uriPath, userId), executorService).thenAccept(asyncResponse::resume);

    }

    @PUT
    @Path("{userId : [0-9]*}")
    public void updateUser(@Suspended AsyncResponse asyncResponse, UserBean userBean,@PathParam("userId") int userId){
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() ->userServiceImpl.updateUSer(uriPath, userBean, userId), executorService).thenAccept(asyncResponse::resume);

    }

    @Path("/{userId : [0-9]*}/exams")
    public ExamController getUserExams(){
        return new ExamController();
    }
}
