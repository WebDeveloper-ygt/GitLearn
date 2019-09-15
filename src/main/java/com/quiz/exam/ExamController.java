package com.quiz.exam;

import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.ApiUtils;
import com.quiz.common.utils.ThreadExecutor;
import com.quiz.exam.model.ExamBean;
import com.quiz.user.UserController;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExamController {

    private ExamServiceImpl examServiceImpl = new ExamServiceImpl();
    private ExecutorService executorService = new ThreadExecutor().getExecutor();
    private static final Logger LOG = Logger.getLogger(UserController.class);

    public ExamController() {
        LOG.info("Invoked ==> " + this.getClass().getName());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getExamByUserId(@Context Request request, @Suspended AsyncResponse asyncResponse, @PathParam("userId") int userId, @Context UriInfo uriInfo) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> {
            Response response = examServiceImpl.getExamByUserId(uriPath, userId);
            return ApiUtils.NotModiedChecker(response, request,"examBean");
        }, executorService).thenAccept(asyncResponse::resume);
    }

    @GET
    @Path("/{examId : [0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getExamByUserIdandExamId(@Context Request request, @Suspended AsyncResponse asyncResponse, @PathParam("userId") int userId, @PathParam("examId") int examId,@Context UriInfo uriInfo) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> {
            Response response = examServiceImpl.getExamByUserIdandExamId(uriPath, userId, examId);
            return ApiUtils.NotModiedChecker(response, request,"examBean");
        }, executorService).thenAccept(asyncResponse::resume);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addExamByUserId(@Context Request request, @Suspended AsyncResponse asyncResponse, @PathParam("userId") int userId, ExamBean examBean,@Context UriInfo uriInfo) {
       String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> examServiceImpl.addExamByUserId(uriPath, userId, examBean), executorService).thenAccept(asyncResponse::resume);
    }

    @PUT
    @Path("{examId : [0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateExamByUserId(@Context Request request, @Suspended AsyncResponse asyncResponse, @PathParam("userId") int userId,@PathParam("examId") int examId, ExamBean examBean, @Context UriInfo uriInfo) {
       String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() ->examServiceImpl.updateExamByUserId(uriPath, userId, examId,examBean), executorService).thenAccept(asyncResponse::resume);
    }


    @DELETE
    @Path("/{examId : [0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteExamByUserIdandExamId(@Context Request request, @Suspended AsyncResponse asyncResponse, @PathParam("userId") int userId, @PathParam("examId") int examId,@Context UriInfo uriInfo) {
        String uriPath = uriInfo.getAbsolutePath().toString();
        LOG.info("Called URI ==> " + uriPath);
        asyncResponse.setTimeoutHandler((asyncResponse1 -> asyncResponse1.resume(HateoasUtils.timeoutException())));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> examServiceImpl.deleteExamByUserIdandExamId(uriPath, userId, examId), executorService).thenAccept(asyncResponse::resume);
    }
}
