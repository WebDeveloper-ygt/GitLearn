package com.quiz.common.hateoas;

import com.quiz.common.exception.CustomException;
import com.quiz.common.utils.Links;
import com.quiz.user.UserServiceDAO;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class HateoasUtils {

    private static final Logger LOG = Logger.getLogger(HateoasUtils.class);
    private static Links links;
    private static List<Links> exceptionLink;

    public static Links getDetailsById(String uriInfo, int userId, String relMessage) {
        //LOG.info("getDetailsById ==> " + uriInfo);
        links = new Links();
        links.setLink(uriInfo+userId);
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

    public static Response ResourceNotFound(String className, String uriInfo, int resourceId){
        exceptionLink = new ArrayList<>();
        exceptionLink.add(getSelfDetails(uriInfo));
        if(className.equalsIgnoreCase(UserServiceDAO.class.getName())){
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new CustomException("User Not Found", 404, "User with identification id" + resourceId + " not found", exceptionLink))
                    .build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new CustomException("Resource Not Found", 404, "Resource with identification id" + resourceId + " not found", exceptionLink))
                    .build();
        }
    }
}
