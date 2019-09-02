package com.quiz.exception.mappers;

import com.quiz.exception.CustomException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMappers implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException exception) {
        return Response.status(exception.getStatusCode()).type(MediaType.APPLICATION_JSON).entity(exception).build();
    }
}
