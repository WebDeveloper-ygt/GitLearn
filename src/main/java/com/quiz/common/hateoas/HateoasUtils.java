package com.quiz.common.hateoas;

import com.quiz.common.exception.ExceptionBean;
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
        //System.out.println((uriInfo.charAt(uriInfo.length()-1) == '/' )+" == " + uriInfo.substring(uriInfo.length()-1) + " === " +uriInfo);
        uriInfo=(uriInfo.charAt(uriInfo.length()-1) == '/') ? uriInfo : uriInfo+"/";
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

    public static Response ResourceNotFound(String className, String uriInfo, int id){
        LOG.error("ResourceNotFound Exception thrown in class ==> " + className);
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
        LOG.error("DBConnection Exception thrown ==> " + message);
            ExceptionBean exceptionBean = new ExceptionBean();
            exceptionBean.setDescription(message);
            exceptionBean.setStatusCode(500);
            exceptionBean.setMessage("Could not connect to database or We found some Database Exception");
            exceptionBean.setLinks(null);
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exceptionBean).type(MediaType.APPLICATION_JSON).build();
    }

    public static Response ResourceFound(String className, String uriInfo, String emailId) {
        LOG.error("ResourceFound Exception thrown in class ==> " + className);
        exceptionLink = new ArrayList<>();
        exceptionLink.add(getSelfDetails(uriInfo));
        if(className.equalsIgnoreCase(UserServiceDAO.class.getName())){
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ExceptionBean("User Is already present", 400, "User with identification id " + emailId + " is already present", exceptionLink))
                    .build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ExceptionBean("Resource is already present", 400, "Resource with identical identifications is already present", exceptionLink))
                    .build();
        }
    }

    public static Response badPostRequest() {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                new ExceptionBean("Input is invalid", 400, "Please check the input provided for resource creation/update", exceptionLink))
                .build();
    }
}
