package com.quiz.user;

import com.quiz.exception.ExceptionOccurred;
import com.quiz.user.model.UserBean;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public interface UserServiceInterface {

    Response getAllUsers(UriInfo uriInfo) throws ExceptionOccurred;
    Response getUser(int userId, UriInfo uriInfo) ;
    Response addUser(UserBean user, UriInfo uriInfo) ;
    Response updateUser(UserBean user, int userId, UriInfo uriInfo);
    Response deleteUser(int userId, UriInfo uriInfo) ;
}
