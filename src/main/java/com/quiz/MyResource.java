package com.quiz;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.quiz.user.UserServiceImpl;
import com.quiz.user.model.TestModel;
import com.quiz.user.model.UserBean;
import com.quiz.utils.Links;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    private static final UserServiceImpl userimpl = new UserServiceImpl();

    @Context
    UriInfo uriInfo;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt(){
        String get =userimpl.getdetails(uriInfo.getAbsolutePath().toString());
        System.out.println(get);
       return userimpl.getIt(uriInfo);
    }
}
