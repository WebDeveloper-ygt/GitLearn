package com.quiz.common.utils;

import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.ConcurrencyException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static Connection getDSConnection() {
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

    public static Connection getDbConnection() {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource dsContext = (DataSource) envContext.lookup("jdbc/quizapiDS");
            connection = dsContext.getConnection();
            return connection;
        } catch (NamingException | SQLException exe) {
            exe.printStackTrace();
        }
        return connection;
    }
}
