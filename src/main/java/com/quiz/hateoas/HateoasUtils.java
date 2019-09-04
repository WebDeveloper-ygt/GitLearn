package com.quiz.hateoas;

import com.quiz.common.utils.ExceptionBean;
import com.quiz.common.utils.Links;

import com.quiz.user.UserServiceDAO;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public class HateoasUtils {
    private static final Logger LOG = Logger.getLogger(HateoasUtils.class);
    private static Links links;
    private static List<Links> linksList;
    public static Links getDetailsById(String uriInfo, int userId, String relMessage) {
        links = new Links();
        links.setLink(uriInfo +userId);
        links.setRef(relMessage);
        return links;
    }

    public static Links getSelfDetails(String uriInfo) {
        links = new Links();
        links.setLink(uriInfo);
        links.setRef("self");
        return links;
    }

    public static Response ResourceNotFound(String name, String uriInfo, int id) {
        LOG.info("Injected class is ==> "+ name);
        ExceptionBean exceptionBean = new ExceptionBean();
        if(name.getClass().getName().equalsIgnoreCase(UserServiceDAO.class.getName())){
            exceptionBean.setDescription("User with user-id " +id +" not Found");
            exceptionBean.setStatusCode(404);
            exceptionBean.setMessage("Resource not found");
            links =getDetailsById(uriInfo,id,"getUserById");
            linksList.add(links);
            exceptionBean.setLinks(linksList);
        }
        return  Response.status(Response.Status.NOT_FOUND).entity(exceptionBean).type(MediaType.APPLICATION_JSON).build();
    }


}
