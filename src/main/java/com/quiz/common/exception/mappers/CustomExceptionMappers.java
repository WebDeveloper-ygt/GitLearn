package com.quiz.common.exception.mappers;

import com.quiz.common.exception.CustomException;
import com.quiz.common.utils.ExceptionBean;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMappers implements ExceptionMapper<CustomException> {

    private static final Logger LOG = Logger.getLogger(CustomExceptionMappers.class);

    @Override
    public Response toResponse(CustomException exception) {
        ExceptionBean exceptionBean = new ExceptionBean();
        exceptionBean.setMessage("Found some technical issues");
        exceptionBean.setStatusCode(500);
        exceptionBean.setDescription(exception.getMessage());
        exceptionBean.setLinks(null);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(exceptionBean).build();
    }
}
