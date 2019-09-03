package com.quiz.user;

import com.quiz.common.exception.CustomException;
import com.quiz.common.exception.ExceptionOccurred;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class UserServiceImpl implements UserServiceInterface{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    private static final UserServiceDAO userDao = new UserServiceDAO();
    private Response userCrud;

    public UserServiceImpl() {
        LOG.info("Invoked ==> " +this.getClass().getName());
    }

    @Override
    public Response getAllUsers(String uriInfo) {
        try {
            userCrud = userDao.getAllUsers(uriInfo);
        } catch (CustomException exceptionOccurred) {
            //throw new CustomException();
            exceptionOccurred.printStackTrace();
        }
        return userCrud;
    }

}
