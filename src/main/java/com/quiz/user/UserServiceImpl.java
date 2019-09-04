package com.quiz.user;

import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.BeanValidation;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class UserServiceImpl implements UserServiceInterface{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    private UserServiceDAO userDao = new UserServiceDAO();

    public UserServiceImpl() {
        LOG.info("Invoked ==> " +this.getClass().getName());
    }

    @Override
    public Response getAllUsers(String uriInfo) {
      return userDao.getAllUsers(uriInfo);
    }

    @Override
    public Response getUser(String uriInfo, int userId) {
       return  userDao.getUser(uriInfo,userId);

    }

    @Override
    public Response addUser(String uriPath, UserBean userBean) {
        LOG.info("New User Under validation  ==> " + userBean.toString());
        boolean valid = BeanValidation.UserPostValidation(userBean);
        if(!valid){
            LOG.info("UserBean validation failed ==> " + userBean.toString());
            return HateoasUtils.badPostRequest();
        }else {
            return userDao.addUser(uriPath,userBean);
        }
    }

    @Override
    public Response updateUSer(String uriPath, UserBean userBean,int userId) {
        LOG.info("User Under validation to update ==> " + userBean.toString());
        return userDao.updateUser(uriPath,userBean,userId);
    }
}
