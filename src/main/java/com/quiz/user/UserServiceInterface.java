package com.quiz.user;

import com.quiz.user.model.UserBean;

import javax.ws.rs.core.Response;

public interface UserServiceInterface {

    Response getAllUsers(String uriInfo);
    Response getUser(String uriInfo, int userId);
    Response addUser(String uriPath, UserBean userBean);
    Response updateUSer(String uriPath, UserBean userBean,int userId);
    Response deleteUser(String uriPath, int userId);
}
