package com.quiz.common.utils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class ApiUtils {

    private static final Logger LOG=Logger.getLogger(ApiUtils.class);
    private static final String URL = "jdbc:mysql://mysql:3306/quizapi?useSSL=false";  //?autoReconnect=true
    private static final String L_URL = "jdbc:mysql://localhost:3306/quizapi?useSSL=false";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    private ApiUtils() {
        LOG.info("Invoked " +this.getClass().getName());
    }

    public static Connection getDbConnection(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOG.info("Connection happened");
            return connection;
        } catch (Exception exe) {
            System.out.println(exe.toString());
            return connection;
        }

    }

    public static Connection getDSConnection(){
      return null;
    }
}
