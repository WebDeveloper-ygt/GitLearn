package com.quiz.utils;

import com.quiz.exception.CustomException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApiUtils {

    private static final Logger LOG=Logger.getLogger(ApiUtils.class);
    private static final String URL = "jdbc:mysql://mysql:3306/quizapi";
    private static final String L_URL = "jdbc:mysql://localhost:3306/quizapi";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private ApiUtils() {
        LOG.info("Invoked " +this.getClass().getName());
    }

    public static Connection getDbConnection() throws ClassNotFoundException, CustomException {

        Connection connection;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOG.info("Connection happened");
        } catch (SQLException exe) {
            LOG.error(exe.getMessage());
            throw new CustomException(exe.getMessage(), 500, "Error occurred while connecting database", null);
        }

        return connection;

    }
}
