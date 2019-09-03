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

import com.quiz.user.model.TestModel;
import com.quiz.user.model.UserBean;
import com.quiz.utils.Links;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
    @Context
    UriInfo uriInfo;
    List<Links> links;
    Links link;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        links = new ArrayList<>();
        link = new Links();
        TestModel testModel = new TestModel();
        testModel.setAge(20);
        testModel.setName("Openshidt");
        testModel.setJob("container");
        link.setAction("GET");
        link.setLink(uriInfo.getAbsolutePath().toString());
        link.setRef("self");
        links.add(link);
        testModel.setLinks(links);
        UserBean.print(uriInfo.getAbsolutePath().toString());
        return Response.status(Status.OK).entity(testModel).build();
    }
}
