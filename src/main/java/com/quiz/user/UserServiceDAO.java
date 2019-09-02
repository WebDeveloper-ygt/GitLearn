package com.quiz.user;

import com.quiz.exception.CustomException;
import com.quiz.exception.ExceptionOccurred;
import com.quiz.hateoas.HateoasUtils;
import com.quiz.user.model.UserBean;
import com.quiz.utils.ApiUtils;
import com.quiz.utils.Constants;
import com.quiz.utils.Links;
import org.apache.log4j.Logger;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceDAO {

    private static Connection dbConnection;
    private static List<UserBean> userList;
    private static List<Links> exceptionLink;
    private static String relMessage;

    private static final Logger LOG = Logger.getLogger(UserServiceDAO.class);

    public UserServiceDAO() {
        LOG.info("Invoked :: " + this.getClass().getName());
    }

    public static Response getAllUsers(UriInfo uriInfo) throws ExceptionOccurred, CustomException {

        return getUserDetailsInCommon(uriInfo, Constants.USERS, 0);
    }

    private static Response getUserDetailsInCommon(UriInfo uriInfo, String statement, int id) throws CustomException {
        userList = new ArrayList<>();

        try {
            dbConnection = ApiUtils.getDbConnection();
        } catch (ClassNotFoundException exe) {
            throw new CustomException(exe.getMessage(), 500, "We found some Exception, will get back soon", null);
        }

        try {
            PreparedStatement pst = dbConnection.prepareStatement(statement);
            ResultSet result = pst.executeQuery();

            LOG.info("Executing query on database for user : " + id);
            while (result.next()) {
                UserBean user = new UserBean();
                List<Links> links = new ArrayList<>();
                System.out.print(user.toString() + " ");
                user.setUserId(result.getInt("userId"));
                user.setUserName(result.getString("userName"));
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
                user.setEmailId(result.getString("emailId"));
                user.setPhoneNumber(result.getString("phoneNumber"));
                user.setInstituteName(result.getString("instituteName"));

                if (id == 0) {
                    relMessage = "getUserById";
                    links.add(HateoasUtils.getDetailsById(uriInfo, result.getInt("userId"), relMessage));
                } else {
                    links.add(HateoasUtils.getSelfDetails(uriInfo));
                }
                user.setLinks(links);
                userList.add(user);
            }
        } catch (Exception exe) {
            LOG.error(exe.getCause() + " = " + exe.getMessage() + " == " + exe.getLocalizedMessage() + " == " + exe.getStackTrace());
            exe.printStackTrace();
            // throw new CustomException("Exception Occurred", 500, "We found some Exception, will get back soon", null);

        }
        LOG.info("Number of User retrieved from the database is " + userList.size());
        if (userList.size() > 0) {
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<UserBean>>(userList) {
            }).build();
        } else {
            exceptionLink = new ArrayList<>();
            exceptionLink.add(HateoasUtils.getSelfDetails(uriInfo));
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new CustomException("User Not Found", 404, "User with user id " + id + " not found", exceptionLink))
                    .build();
        }
    }


}
