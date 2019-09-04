package com.quiz.user;

import com.quiz.common.exception.CustomException;
import com.quiz.common.exception.ExceptionOccurred;
import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.user.model.UserBean;
import com.quiz.common.utils.ApiUtils;
import com.quiz.common.utils.Constants;
import com.quiz.common.utils.Links;
import org.apache.log4j.Logger;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceDAO {


    private List<UserBean> userList;
    private static String relMessage;

    private static final Logger LOG = Logger.getLogger(UserServiceDAO.class);

    public UserServiceDAO() {
        LOG.info("Invoked ==> " + this.getClass().getName());
    }

    public Response getAllUsers(String uriInfo) throws CustomException {

        return getUserDetailsInCommon(uriInfo, Constants.USERS, 0);
    }

    private Response getUserDetailsInCommon(String uriInfo, String statement, int id) throws CustomException {

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
                userList.add(user);
            }
        } catch (SQLException sqlException) {
            return HateoasUtils.DbConnectionException(sqlException.getMessage());
        }
        LOG.info("Number of User retrieved from the database is ==> " + userList.size());
        if (userList.size() > 0) {
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<UserBean>>(userList) {
            }).build();
        } else {
            return HateoasUtils.ResourceNotFound(UserServiceDAO.class.getName(), uriInfo, id);
        }

    }
}
