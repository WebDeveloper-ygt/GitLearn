package com.quiz.exam;

import com.quiz.common.exception.ExceptionBean;
import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.ApiUtils;
import com.quiz.common.utils.BeanValidation;
import com.quiz.common.utils.Constants;
import com.quiz.common.utils.Links;
import com.quiz.exam.model.ExamBean;
import com.quiz.user.UserServiceDAO;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class ExamDAO {
    private List<ExamBean> examList = new ArrayList<>();
    private static String relMessage;
    private List<Links> exceptionLink;
    private static final Logger LOG = Logger.getLogger(ExamDAO.class);
    Date expiryTime = Date.from(ZonedDateTime.now().plusMinutes(10).toInstant());
    private UserServiceDAO userDao = new UserServiceDAO();

    public ExamDAO() {
        LOG.info("Invoked ==> " + this.getClass().getName());
    }

    public Response getExamByUserIdandExamId(String uriPath, int userId, int examId) {
        return getExamByUserInCommon(uriPath, userId, examId);
    }

    public Response getExamByUserInCommon(String uriPath, int userId, int examId) {
        Response user = userDao.getUser(uriPath, userId);
        System.out.println(user);
        if (user.getStatus() == 200) {
            List<UserBean> userList = (List<UserBean>) user.getEntity();
            System.out.println(userList);
            UserBean updatedUser = userList.get(0);
            System.out.println(updatedUser);
            Connection dbConnection = ApiUtils.getDbConnection();
            if (dbConnection == null) {
                return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
                // throw new CustomException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            }
            try {
                PreparedStatement pst =null;
                if(examId > 0){
                    pst= dbConnection.prepareStatement(Constants.EXAMS_ID + examId + Constants.EXAM_ID_USER_ID + userId);
                }else{
                    pst = dbConnection.prepareStatement(Constants.EXAMS_USERID+userId);
                }
                System.out.println(pst.toString());
                ResultSet result = pst.executeQuery();
                System.out.println(result);
                LOG.info("Executing query on database for exam ==> " + examId);
                while (result.next()) {
                    ExamBean examBean = new ExamBean();
                    examBean.setExamId(result.getInt("examId"));
                    examBean.setExamName(result.getString("examName"));
                    examBean.setExamDuration(result.getInt("examDuration"));
                    examBean.setExamStatus(result.getString("examStatus"));
                    examBean.setNegativeMarks(result.getInt("negativeMarks"));
                    examBean.setNumberOfQuestions(result.getInt("numberOfQuestions"));
                    examBean.setUser(updatedUser);
                    System.out.println(examBean);
                    examList.add(examBean);
                }
                LOG.info("Number of Exams retrieved from the database is ==> " + examList.size());
                if (examList.size() > 0) {
                    System.out.println(examList.size());
                    return Response.status(Response.Status.OK).entity(new GenericEntity<List<ExamBean>>(examList) {
                    }).allow(HttpMethod.GET, HttpMethod.POST).build();
                } else {
                    return HateoasUtils.ResourceNotFound(ExamDAO.class.getName(), uriPath, examId);
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                return HateoasUtils.DbConnectionException(sqlException.getMessage());
            }
        } else {
            return HateoasUtils.ResourceNotFound(UserServiceDAO.class.getName(), uriPath, userId);
        }
    }

    public Response getExamByUserId(String uriPath, int userId) {

        return getExamByUserInCommon(uriPath,userId,0);
    }

    public Response createExamByUserId(String uriInfo, int userId, ExamBean examBean) {
        Response user = userDao.getUser(uriInfo, userId);
        System.out.println(user);
        if (user.getStatus() == 200) {
            List<UserBean> userList = (List<UserBean>) user.getEntity();
            System.out.println(userList);
            UserBean updatedUser = userList.get(0);
            System.out.println(updatedUser);
            Connection dbConnection = ApiUtils.getDbConnection();
            if (dbConnection == null) {
                return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
                // throw new CustomException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            }
            try{
                LOG.info("A new exam request is under processing (1) ==>");
                String addExam = "INSERT INTO quiz_exams (examName,examDuration,negativeMarks,numberOfQuestions,examStatus,userId)VALUES (?,?,?,?,?,?)";
                PreparedStatement pst = dbConnection.prepareStatement(addExam);
                pst.setString(1, examBean.getExamName());
                pst.setInt(2, examBean.getExamDuration());
                pst.setInt(3, examBean.getNegativeMarks());
                pst.setInt(4, examBean.getNumberOfQuestions());
                pst.setString(5, examBean.getExamStatus());
                pst.setInt(6, updatedUser.getUserId());

                boolean execute = pst.execute();
                LOG.info("A new exam request is under processing (2) ==> result ==> " + execute);
                if (!execute) {
                    LOG.info("A new exam request is under processing (3) ==>");
                    ResultSet resultSet = pst.executeQuery(Constants.EXAM_LAST_INSERT);
                    int insertId = 0;
                    while (resultSet.next()){
                        insertId = resultSet.getInt("last_insert_id()");
                    }
                    Response returnUser = getExamByUserInCommon(uriInfo,updatedUser.getUserId(),insertId);
                    LOG.info("A new exam request is processed ==> " + returnUser);
                    return returnUser;
                } else {
                    return HateoasUtils.badPostRequest();
                }
            }catch (Exception sqlException) {
                sqlException.printStackTrace();
                return HateoasUtils.DbConnectionException(sqlException.getMessage());
            }
        }else{
            return HateoasUtils.ResourceNotFound(UserServiceDAO.class.getName(), uriInfo, userId);
        }

    }

    public Response examUpdateByUserIdAndExamId(String uriInfo, int userId, int examId, ExamBean examBean) {
        Response checkExamUser = getExamByUserIdandExamId(uriInfo, userId, examId);
        if(checkExamUser.getStatus() == 200){
            LOG.info("Exam update Request recieved ==>");
            List<ExamBean> entity = (List<ExamBean>) checkExamUser.getEntity();
            ExamBean presentExamBean = entity.get(0);
            UserBean updateduser = presentExamBean.getUser();
            System.out.println("Present ExamBean ==>" + presentExamBean);
            String updateExamQuery = BeanValidation.examUpdateValidation(presentExamBean,examBean);
            System.out.println("Query ==> " + updateExamQuery);
            Connection dbConnection = ApiUtils.getDbConnection();
            if (dbConnection == null) {
                return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            }
            if (updateExamQuery != null) {
                LOG.info("Exam update Request under process (1) ==>");
                try {
                    PreparedStatement prepareStatement = dbConnection.prepareStatement(updateExamQuery);
                    int update = prepareStatement.executeUpdate();
                    if (update >= 1) {
                        LOG.info("Exam update Request under process (2) ==>");

                        //Response updated =getExamByUserIdandExamId(uriInfo,userId,examId);
                        prepareStatement.close();
                        Response updatedExam = getExamByUserIdandExamId(uriInfo,userId,examId);
                        List<ExamBean> entity1 = (List<ExamBean> )updatedExam.getEntity();
                        ExamBean ex = entity1.get(1);
                        return Response.status(Response.Status.CREATED).entity(ex).build();
                    } else {
                        exceptionLink = new ArrayList<>();
                        exceptionLink.add(HateoasUtils.getSelfDetails(uriInfo));
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ExceptionBean("Exam update failed", 400,
                                "Exam with exam id " + examId + " has not been updated", exceptionLink)).build();
                    }
                } catch (Exception sqlException) {
                    sqlException.printStackTrace();
                    return HateoasUtils.badPostRequest();
                }
            } else {
                return HateoasUtils.badPostRequest();
            }
        }else if (checkExamUser.getStatus() == 500) {
            return HateoasUtils.DbConnectionException("We are facing problems in connecting database");
        } else {
            LOG.error("Exam for user Not Found ==> " + userId);
            return HateoasUtils.ResourceNotFound(ExamDAO.class.getName(), uriInfo, userId);
        }
    }

    public Response deleteExamByUserAndExamID(String uriInfo, int userId, int examId) {
        Response checkUserExam = getExamByUserIdandExamId(uriInfo, userId, examId);
        if(checkUserExam.getStatus() == 200){
            LOG.info("Exam delete Request recieved ==>");
            Connection dbConnection = ApiUtils.getDbConnection();
            if (dbConnection == null) {
                return HateoasUtils.DbConnectionException("com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure");
            }
            try {
                Statement createStatement = dbConnection.createStatement();
                int executeDelete = createStatement.executeUpdate(Constants.EXAM_DELETE + examId + Constants.EXAM_ID_USER_ID + userId);
                System.out.println(executeDelete);
                if(executeDelete == 1){
                    System.out.println("Exam Deleted ");
                    exceptionLink = new ArrayList<>();
                    exceptionLink.add(HateoasUtils.getSelfDetails(uriInfo));
                    Response deleted = Response.status(Response.Status.OK).entity(new ExceptionBean("Exam Deleted", 200,
                            "Exam with exam Id " + examId + "for user id" + userId +" has been deleted", exceptionLink)).build();
                    System.out.println(deleted.getEntity());
                    return deleted;
                } else {
                    return HateoasUtils.ResourceNotFound(ExamDAO.class.getName(),uriInfo,userId);
                }
            }catch (Exception sqlException){
                sqlException.printStackTrace();
                return HateoasUtils.DbConnectionException(sqlException.getMessage());
            }
        }else{
            return HateoasUtils.ResourceNotFound(ExamDAO.class.getName(),uriInfo,examId);
        }
    }
}
