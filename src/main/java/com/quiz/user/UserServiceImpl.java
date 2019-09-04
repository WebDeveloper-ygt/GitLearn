package com.quiz.user;

import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class UserServiceImpl implements UserServiceInterface{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    private static final UserServiceDAO userDao = new UserServiceDAO();
    private Response userCrud;

    public UserServiceImpl() {
        LOG.info("Invoked ==> " +this.getClass().getName());
    }

    @Override
    public Response getAllUsers(String uriInfo) {
        userCrud = userDao.getAllUsers(uriInfo);
        return userCrud;
    }

    @Override
    public Response getUser(String uriInfo, int userId) {
        userCrud = userDao.getUser(uriInfo,userId);
        return userCrud;
    }
}
