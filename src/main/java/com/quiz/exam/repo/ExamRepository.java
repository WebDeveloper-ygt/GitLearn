package com.quiz.exam.repo;

import com.quiz.user.UserServiceDAO;

import javax.ws.rs.core.Response;


public class ExamRepository {

    private UserServiceDAO userServiceDAO;

    public Response getExamByUserIdandExamId(String uriPath, int userId, int examId) {
        Response userResponse = userServiceDAO.getUser(uriPath,userId);
        return userResponse;
    }
}
