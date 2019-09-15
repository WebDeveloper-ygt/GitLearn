package com.quiz.common.utils;

import com.quiz.exam.model.ExamBean;
import com.quiz.user.model.UserBean;
import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.ConcurrencyException;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.core.*;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ApiUtils {

    private static final Logger LOG = Logger.getLogger(ApiUtils.class);
    private static final String URL = "jdbc:mysql://mysql:3306/quizapi?useSSL=false";  //?autoReconnect=true
    private static final String L_URL = "jdbc:mysql://localhost:3306/quizapi?useSSL=false";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    private ApiUtils() {
        LOG.info("Invoked " + this.getClass().getName());
    }

    public static Connection getDbConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(L_URL, USER, PASSWORD);
            LOG.info("Connection happened");
            return connection;
        } catch (Exception exe) {
            System.out.println(exe.toString());
            return connection;
        }

    }

    public static Connection getDSConnection() {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            // Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource dsContext = (DataSource) initContext.lookup("java:comp/env/jdbc/quizapiDS");
            connection = dsContext.getConnection();
            return connection;
        } catch (NamingException | SQLException exe) {
            exe.printStackTrace();
        }
        return connection;
    }

    public static Response NotModiedChecker(Response response, Request request, String className) {
        if (response.getStatus() == 200) {
            if (className.equalsIgnoreCase("userBean")) {
                List<UserBean> userBeanList = (List<UserBean>) response.getEntity();
                try {
                    MessageDigest digest = MessageDigest.getInstance("MD5");
                    byte[] hash = digest.digest(String.valueOf(userBeanList.size()).getBytes(StandardCharsets.UTF_8));
                    String hex = DatatypeConverter.printHexBinary(hash);
                    EntityTag entityTag = new EntityTag(hex);
                    System.out.println(request);
                    System.out.println("entity tag : " + entityTag);
                    CacheControl cacheControl = new CacheControl();
                    cacheControl.setMaxAge(1000);
                    Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(entityTag);
                    System.out.println("Response Builder after request evaluatePreconditions " +  responseBuilder);
                    if (responseBuilder != null) {
                        System.out.println("returned 304");
                        return responseBuilder.cacheControl(cacheControl).build();
                    }
                    System.out.println("Returned 200");
                    return Response.ok().cacheControl(cacheControl).tag(entityTag).entity(new GenericEntity<List<UserBean>>(userBeanList) {
                    }).build();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }else if (className.equalsIgnoreCase("examBean")){
                List<ExamBean> examBeanList = (List<ExamBean>) response.getEntity();
                try {
                    MessageDigest digest = MessageDigest.getInstance("MD5");
                    byte[] hash = digest.digest(String.valueOf(examBeanList.size()).getBytes(StandardCharsets.UTF_8));
                    String hex = DatatypeConverter.printHexBinary(hash);
                    EntityTag entityTag = new EntityTag(hex);
                    System.out.println(request);
                    System.out.println("entity tag : " + entityTag);
                    CacheControl cacheControl = new CacheControl();
                    cacheControl.setMaxAge(1000);
                    Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(entityTag);
                    System.out.println("Response Builder after request evaluatePreconditions " +  responseBuilder);
                    if (responseBuilder != null) {
                        System.out.println("returned 304");
                        return responseBuilder.cacheControl(cacheControl).build();
                    }
                    System.out.println("Returned 200");
                    return Response.ok().cacheControl(cacheControl).tag(entityTag).entity(new GenericEntity<List<ExamBean>>(examBeanList) {
                    }).build();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }else{
                return response;
            }
        }else{
            return response;
        }
        return response;
    }
}
