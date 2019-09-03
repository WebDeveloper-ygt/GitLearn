package com.quiz.user;

import javax.ws.rs.core.Response;

import com.quiz.exception.ExceptionOccurred;
import com.quiz.user.model.UserBean;

public interface UserServiceInterface {

    Response getAllUsers(String uriInfo) throws ExceptionOccurred;
    Response getUser(int userId, String uriInfo) ;
    Response addUser(UserBean user, String uriInfo) ;
    Response updateUser(UserBean user, int userId, String uriInfo);
    Response deleteUser(int userId, String uriInfo) ;
}
