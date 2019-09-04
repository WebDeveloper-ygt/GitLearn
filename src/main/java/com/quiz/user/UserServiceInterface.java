package com.quiz.user;

import javax.ws.rs.core.Response;

public interface UserServiceInterface {

    Response getAllUsers(String uriInfo);
    Response getUser(String uriInfo, int userId);
}
