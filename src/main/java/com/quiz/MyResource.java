package com.quiz;

import com.quiz.common.exception.CustomException;
import com.quiz.common.utils.ApiUtils;
import com.quiz.common.utils.ThreadExecutor;
import com.quiz.user.UserServiceImpl;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.Uri;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("test")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    private static final ExecutorService executorService = new ThreadExecutor().getExecutor();
    private static final Logger LOG = Logger.getLogger(MyResource.class);
    private static final UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {

        try{
            Connection connection = ApiUtils.getDbConnection();
            if(connection == null){
                throw  new CustomException("DBException Occurred");
            }
            return Response.status(Response.Status.OK).entity("successful").build();
        }catch (Exception exception){
            throw  new CustomException("DBException Occurred");
        }
    }

    @GET
    @Path("/controller")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAsync(@Suspended AsyncResponse asyncResponse) {
        CompletableFuture.supplyAsync(()->{
            TestModel testModel = new TestModel();
            testModel.setAge(20);
            testModel.setJob("Enginner");
            testModel.setName("Openshift");
            testModel.setLinks(null);
            return testModel;
        }).thenAccept(asyncResponse::resume);
    }

    @GET
    @Path("/context")
    @Produces(MediaType.TEXT_PLAIN)
    public String getContexts(@Context UriInfo uriInfo, @Context Request request){
        return uriInfo.getAbsolutePath() + " === "+ request.getMethod();
    }

    @Path("/test")
    @GET
    public void testUser(@Suspended AsyncResponse asyncResponse) throws NoSuchAlgorithmException {

        CompletableFuture.supplyAsync(() -> {
            Response response = userServiceImpl.getAllUsers(uriInfo.getAbsolutePath().toString());
            Response respo =ApiUtils.NotModiedChecker(response,request,MyResource.class.getName());
            return respo;
        },executorService).thenAccept(asyncResponse::resume);

    }
}
