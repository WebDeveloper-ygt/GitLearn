package com.quiz.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.quiz.exception.CustomException;
import com.quiz.exception.ExceptionOccurred;
import com.quiz.user.model.TestModel;
import com.quiz.user.model.UserBean;
import com.quiz.utils.Links;

import org.apache.log4j.Logger;

public class UserServiceImpl implements UserServiceInterface{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    private static final UserServiceDAO userDao = new UserServiceDAO();
    List<Links> links;
    Links link;
    
    public UserServiceImpl() {
        LOG.info("Invoked :: " +this.getClass().getName());
    }

    @Override
    public Response getAllUsers(String uriInfo) {
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


    public Response getIt(UriInfo uriInfo){
        links = new ArrayList<>();
        link = new Links();
        TestModel testModel = new TestModel();
        testModel.setAge(20);
        testModel.setName("Openshidt");
        testModel.setJob("container");
        link.setAction("GET");
        link.setLink(uriInfo.getAbsolutePath().toString());
        link.setRef("self");
        links.add(link);
        testModel.setLinks(links);
        UserBean.print(uriInfo.getAbsolutePath().toString());
        return Response.status(Status.OK).entity(testModel).build();
    }

	public String getdetails(String string) {
		return string;
	}
}
