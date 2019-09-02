package com.quiz.hateoas;

import com.quiz.user.UserController;
import com.quiz.user.UserServiceDAO;
import com.quiz.utils.Links;
import org.apache.log4j.Logger;

import javax.ws.rs.core.UriInfo;

public class HateoasUtils {
    private static final Logger LOG = Logger.getLogger(HateoasUtils.class);
    private static Links links;
    public static Links getDetailsById(UriInfo uriInfo, int userId, String relMessage) {
        LOG.info(uriInfo.getAbsolutePath() + " "+ uriInfo.getBaseUri() + " " + uriInfo.getPath());
        links = new Links();
        links.setLink(uriInfo.getAbsolutePath().toString() +userId);
        links.setRef(relMessage);
        return links;
    }

    public static Links getSelfDetails(UriInfo uriInfo) {
        LOG.info(uriInfo.getAbsolutePath() + " "+ uriInfo.getBaseUri() + " " + uriInfo.getPath());
        links = new Links();
        links.setLink(uriInfo.getAbsolutePath().toString());
        links.setRef("self");
        return links;
    }
}
