package com.quiz.user;

import com.quiz.exception.CustomException;
import com.quiz.exception.ExceptionOccurred;
import com.quiz.hateoas.HateoasUtils;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserServiceInterface{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    private static final UserServiceDAO userDao = new UserServiceDAO();

    public UserServiceImpl() {
        LOG.info("Invoked :: " +this.getClass().getName());
    }

    @Override
    public Response getAllUsers(UriInfo uriInfo) {
        Response allUsers = null;
        try {
            allUsers = UserServiceDAO.getAllUsers(uriInfo);
        } catch (ExceptionOccurred | CustomException exceptionOccurred) {
            exceptionOccurred.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public Response getUser(int userId, UriInfo uriInfo) {
        return null;
    }

    @Override
    public Response addUser(UserBean user, UriInfo uriInfo) {
        return null;
    }

    @Override
    public Response updateUser(UserBean user, int userId, UriInfo uriInfo) {
        return null;
    }

    @Override
    public Response deleteUser(int userId, UriInfo uriInfo) {
        return null;
    }
}
