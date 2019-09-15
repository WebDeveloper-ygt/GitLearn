package com.quiz.exam;

import com.quiz.exam.model.ExamBean;

import javax.ws.rs.core.Response;


public interface ExamService {

    /*Response getAllExamsByAdmin(String uriInfo);
    Response getAllExamsByIdAdmin(String uriInfo,int examId);*/
    Response getExamByUserIdandExamId(String uriInfo, int examId, int userId);
    Response addExamByUserId(String uriInfo, int userId,ExamBean examBean);
    Response updateExamByUserId(String uriInfo, int userId, int examId, ExamBean examBean);
    Response deleteExamByUserIdandExamId(String uriInfo, int userId, int examId);

    Response getExamByUserId(String uriPath, int userId);
}
