package com.quiz.user;

import com.quiz.common.exception.CustomException;
import com.quiz.common.exception.ExceptionBean;
import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.BeanValidation;
import com.quiz.user.model.UserBean;
import com.quiz.common.utils.ApiUtils;
import com.quiz.common.utils.Constants;
import com.quiz.common.utils.Links;
import org.apache.log4j.Logger;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserServiceDAO {


    private List<UserBean> userList;
    private static String relMessage;
    private List<Links> exceptionLink;
    private static final Logger LOG = Logger.getLogger(UserServiceDAO.class);

    public UserServiceDAO() {
        LOG.info("Invoked ==> " + this.getClass().getName());
    }

    public Response getAllUsers(String uriInfo) throws CustomException {

        return getUserDetailsInCommon(uriInfo, Constants.USERS, 0);
    }

    private Response getUserDetailsInCommon(String uriInfo, String statement, int id) throws CustomException {
        userList = new ArrayList<>();
        Connection dbConnection = ApiUtils.getDbConnection();
        if (dbConnection == null) {
            return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            // throw new CustomException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
        }
        try {
            PreparedStatement pst = dbConnection.prepareStatement(statement);
            ResultSet result = pst.executeQuery();

            LOG.info("Executing query on database for user ==> " + ((id > 0) ? id : Constants.ALL));
            while (result.next()) {
                UserBean user = new UserBean();
                List<Links> links = new ArrayList<>();
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
                // System.out.println(user.getEmailId() + " ==> " +user.toString());
                userList.add(user);
                //System.out.println(userList.size());
            }
            LOG.info("Number of User retrieved from the database is ==> " + userList.size());
            if (userList.size() > 0) {
                return Response.status(Response.Status.OK).entity(new GenericEntity<List<UserBean>>(userList) {
                }).build();
            } else {
                return HateoasUtils.ResourceNotFound(UserServiceDAO.class.getName(), uriInfo, id);
            }
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
            return HateoasUtils.DbConnectionException(sqlException.getMessage());
        }

    }

    public Response getUser(String uriInfo, int userId) {
        return getUserDetailsInCommon(uriInfo, Constants.USERS_ID + userId, userId);
    }

    public Response addUser(String uriInfo, UserBean user) {
        Response checkUser = getUserDetailsInCommon(uriInfo, (Constants.USER_EMAIL + "'" + user.getEmailId() + "'"), 0);
        LOG.info("Checking existing details result is  ==> " + checkUser);
        if (checkUser.getStatus() == 404) {
            LOG.info("A New User Request recieved ==>");
            Connection dbConnection = ApiUtils.getDbConnection();
            if (dbConnection == null) {
                return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            }
            try {
                LOG.info("A new user request is under processing (1) ==>");
                String addUser = "INSERT INTO quiz_users (userName,firstName,lastName,emailId,phoneNumber,instituteName,password)VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pst = dbConnection.prepareStatement(addUser);
                pst.setString(1, user.getUserName());
                pst.setString(2, user.getFirstName());
                pst.setString(3, user.getLastName());
                pst.setString(4, user.getEmailId());
                pst.setString(5, user.getPhoneNumber());
                pst.setString(6, user.getInstituteName());
                pst.setString(7, Base64.getEncoder().encodeToString(user.getPassword().getBytes()));

                boolean execute = pst.execute();
                LOG.info("A new user request is under processing (2) ==> result ==> " + execute);
                if (!execute) {
                    LOG.info("A new user request is under processing (3) ==>");
                    Response returnUser = getUserDetailsInCommon(uriInfo, (Constants.USER_EMAIL + "'" + user.getEmailId() + "'"), 0);
                    LOG.info("A new ueser request is processed ==> " + returnUser);
                    return returnUser;
                } else {
                    return HateoasUtils.badPostRequest();
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                return HateoasUtils.badPostRequest();
                // return HateoasUtils.DbConnectionException(sqlException.getMessage());
            }

        } else if (checkUser.getStatus() == 500) {
            return HateoasUtils.DbConnectionException("We are facing problems in connecting database");
        } else {
            LOG.error("User Already Present ==> " + user.getEmailId());
            return HateoasUtils.ResourceFound(UserServiceDAO.class.getName(), uriInfo, user.getEmailId());
        }
    }

    public Response updateUser(String uriInfo, UserBean userBean, int userId) {
        Response checkUser = getUserDetailsInCommon(uriInfo, Constants.USERS_ID + userId, userId);
        if (checkUser.getStatus() == 200) {
            LOG.info("User update Request recieved ==>");
            Connection dbConnection = ApiUtils.getDbConnection();
            if (dbConnection == null) {
                return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            }
            String updateUser = BeanValidation.userUpdateValidation(checkUser, userBean, userId);
            if (updateUser != null) {
                LOG.info("User update Request under process (1) ==>");
                try {
                    PreparedStatement prepareStatement = dbConnection.prepareStatement(updateUser);
                    int update = prepareStatement.executeUpdate();
                    if (update >= 1) {
                        LOG.info("User update Request under process (2) ==>");
                        Response updatedUser = getUserDetailsInCommon(uriInfo, (Constants.USERS_ID + userId), userId);
                        LOG.info("User Update processed ==> ");
                        return updatedUser;
                    } else {
                        exceptionLink = new ArrayList<>();
                        exceptionLink.add(HateoasUtils.getSelfDetails(uriInfo));
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ExceptionBean("User update failed", 400,
                                "User with user id " + userId + " has not been updated", exceptionLink)).build();
                    }
                } catch (Exception sqlException) {
                    sqlException.printStackTrace();
                    return HateoasUtils.badPostRequest();
                }
            } else {
                return HateoasUtils.badPostRequest();
            }
        } else if (checkUser.getStatus() == 500) {
            return HateoasUtils.DbConnectionException("We are facing problems in connecting database");
        } else {
            LOG.error("User Not Found ==> " + userId);
            return HateoasUtils.ResourceNotFound(UserServiceDAO.class.getName(), uriInfo, userId);
        }
    }

    public Response delerteUser(String uriInfo, int userId) {
        Response checkUser = getUserDetailsInCommon(uriInfo, Constants.USERS_ID + userId, userId);
        if (checkUser.getStatus() == 200) {
            LOG.info("User delete Request recieved ==>");
            Connection dbConnection = ApiUtils.getDbConnection();
            if (dbConnection == null) {
                return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            }
            try {
                Statement createStatement = dbConnection.createStatement();
                int executeDelete = createStatement.executeUpdate(Constants.USER_DELETE + userId);
                if(executeDelete == 1){
                    exceptionLink = new ArrayList<>();
                    exceptionLink.add(HateoasUtils.getSelfDetails(uriInfo));
                    return Response.status(Response.Status.OK).entity(new ExceptionBean("User Deleted", 200,
                            "User with user Id " + userId + " deleted", exceptionLink)).build();
                } else {
                    return HateoasUtils.ResourceNotFound(UserServiceDAO.class.getName(),uriInfo,userId);
                }
            }catch (Exception sqlException){
                sqlException.printStackTrace();
                return HateoasUtils.DbConnectionException(sqlException.getMessage());
            }
        }else if (checkUser.getStatus() == 500) {
            return HateoasUtils.DbConnectionException("We are facing problems in connecting database");
        } else {
            LOG.error("User Not Found ==> " + userId);
            return HateoasUtils.ResourceNotFound(UserServiceDAO.class.getName(), uriInfo, userId);
        }
    }
}
