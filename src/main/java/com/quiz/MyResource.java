package com.quiz;

import com.quiz.common.exception.CustomException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {

        /*int sum = 10 +20 ;
        if(sum < 0){
            return Response.status(Response.Status.OK).entity("successful").build();
        }else{
            throw  new CustomException("sum is greater than zero");
        }*/

        try{
            int divide = 10/0;
            return Response.status(Response.Status.OK).entity("successful").build();
        }catch (Exception exception){
            throw  new CustomException("divide by zero");
        }
    }
}
