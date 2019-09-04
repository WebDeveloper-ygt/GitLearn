package com.quiz;

import com.google.common.base.Supplier;
import com.quiz.common.exception.CustomException;
import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.ApiUtils;
import com.quiz.common.utils.Constants;
import com.quiz.common.utils.Links;
import com.quiz.common.utils.ThreadExecutor;
import com.quiz.user.UserController;
import com.quiz.user.UserServiceDAO;
import com.quiz.user.UserServiceImpl;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {

       /* userList = new ArrayList<>();
        try {
            Connection dbConnection = ApiUtils.getDbConnection();
            LOG.info("Retuned connection object is ==> " + ((dbConnection != null) ? dbConnection.toString() : null));
            if (dbConnection == null) {
                LOG.info("DBConnection Failure => ");
                throw new CustomException("Failed to connect databse");
            } else {

            }
        }catch (Exception exe){
            throw new CustomException(exe.getMessage());
        }
*/
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
    @Path("/async")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAsync(@Suspended AsyncResponse asyncResponse) throws ExecutionException, InterruptedException {
        CompletableFuture<Response> completableFuture = CompletableFuture.supplyAsync(()->userServiceImpl.getAllUsers(uriInfo.getAbsolutePath().toString()));
      // CompletableFuture.supplyAsync(MyResource::getUsers);

            Response response = completableFuture.get();
        asyncResponse.resume(response);

    }
    public static Response getUsers(){
        return userServiceImpl.getAllUsers("");
    }
}
