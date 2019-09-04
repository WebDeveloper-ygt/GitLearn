package com.quiz.common.hateoas;

import com.quiz.common.utils.ExceptionBean;
import com.quiz.common.utils.Links;
import com.quiz.user.UserServiceDAO;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class HateoasUtils {

    private static final Logger LOG = Logger.getLogger(HateoasUtils.class);
    private static Links links;
    private static List<Links> exceptionLink;

    public static Links getDetailsById(String uriInfo, int userId, String relMessage) {
        //LOG.info("getDetailsById ==> " + uriInfo);
        links = new Links();
        links.setLink(uriInfo+"/"+userId);
        links.setRef(relMessage);
        return links;
    }

    public static Links getSelfDetails(String uriInfo) {
        //LOG.info("getSelfDetails ==> "+ uriInfo);
        links = new Links();
        links.setLink(uriInfo);
        links.setRef("self");
        return links;
    }

    public static Response ResourceNotFound(String className, String uriInfo, int id){
        exceptionLink = new ArrayList<>();
        exceptionLink.add(getSelfDetails(uriInfo));
        if(className.equalsIgnoreCase(UserServiceDAO.class.getName())){
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ExceptionBean("User Not Found", 404, "User with identification id " + ((id > 0)? id: "all") + " not found", exceptionLink))
                    .build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ExceptionBean("Resource Not Found", 404, "Resource with identification id" + (id) + " not found", exceptionLink))
                    .build();
        }
    }

    public static Response DbConnectionException(String message) {
            ExceptionBean exceptionBean = new ExceptionBean();
            exceptionBean.setDescription(message);
            exceptionBean.setStatusCode(500);
            exceptionBean.setMessage("Could not connect to database or We found some Database Exception");
            exceptionBean.setLinks(null);
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exceptionBean).type(MediaType.APPLICATION_JSON).build();
    }
}
