package com.quiz.common.exception.mappers;

import com.quiz.common.exception.CustomException;
import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.ExceptionBean;
import com.quiz.common.utils.Links;
import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@Provider
public class WebExceptionMappers implements ExceptionMapper<WebApplicationException> {

    private static final Logger LOG = Logger.getLogger(WebExceptionMappers.class);

    ExceptionBean exceptionBean;
    Links links;
    List<Links> linksList ;
    @Override
    public Response toResponse(WebApplicationException exception) {
        exceptionBean = new ExceptionBean();
        links = new Links();
        linksList = new ArrayList<>();

        Response response = exception.getResponse();
        LOG.error("Exception is ==> " + response);

        if(response.getStatus() == 405){
            exceptionBean.setStatusCode(response.getStatus());
            exceptionBean.setMessage(response.getStatusInfo().getReasonPhrase());
            exceptionBean.setDescription("Method used Not allowed for this resource");
            //links = HateoasUtils.getDocumentationLink();
            //linksList.add(links);
            exceptionBean.setLinks(null);
            return Response.status(exception.getResponse().getStatus()).entity(exceptionBean).type(MediaType.APPLICATION_JSON).build();
        }
        return Response.serverError().type(MediaType.APPLICATION_JSON).build();
    }
}
