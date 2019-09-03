package com.quiz.user;

import com.quiz.common.exception.CustomException;
import com.quiz.common.exception.ExceptionOccurred;
import com.quiz.user.model.UserBean;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public interface UserServiceInterface {

    Response getAllUsers(String uriInfo) throws ExceptionOccurred, CustomException;
}
