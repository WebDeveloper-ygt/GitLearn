package com.quiz.hateoas;

import com.quiz.utils.Links;

import org.apache.log4j.Logger;

public class HateoasUtils {
    private static final Logger LOG = Logger.getLogger(HateoasUtils.class);
    private static Links links;
    public static Links getDetailsById(String uriInfo, int userId, String relMessage) {
        links = new Links();
        links.setLink(uriInfo +"/" + userId);
        links.setRef(relMessage);
        return links;
    }

    public static Links getSelfDetails(String uriInfo) {
        links = new Links();
        links.setLink(uriInfo);
        links.setRef("self");
        return links;
    }
}
