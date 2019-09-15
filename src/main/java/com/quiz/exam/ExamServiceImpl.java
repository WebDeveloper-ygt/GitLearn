package com.quiz.exam;

import com.quiz.common.hateoas.HateoasUtils;
import com.quiz.common.utils.BeanValidation;
import com.quiz.exam.model.ExamBean;
import com.quiz.user.UserController;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class ExamServiceImpl implements ExamService {

    private static final Logger LOG = Logger.getLogger(UserController.class);
    private ExamDAO examRepository = new ExamDAO();

    @Override
    public Response getExamByUserIdandExamId(String uriPath, int userId, int examId) {

        return examRepository.getExamByUserIdandExamId(uriPath,userId, examId);
    }

    @Override
    public Response addExamByUserId(String uriInfo, int userId, ExamBean examBean) {
        boolean checkExamBean = BeanValidation.ExamPostValidation(examBean);
        System.out.println(checkExamBean);
        if(checkExamBean){
            return examRepository.createExamByUserId(uriInfo, userId, examBean);
        }else{
          LOG.info("UserBean validation failed ==> " + examBean.toString());
            return HateoasUtils.badPostRequest();
        }
    }

    @Override
    public Response updateExamByUserId(String uriInfo, int userId,int examId, ExamBean examBean) {

        return examRepository.examUpdateByUserIdAndExamId(uriInfo,userId,examId,examBean);
    }

    @Override
    public Response deleteExamByUserIdandExamId(String uriInfo, int userId, int examId) {
        return examRepository.deleteExamByUserAndExamID(uriInfo,userId,examId);
    }

    @Override
    public Response getExamByUserId(String uriPath, int userId) {
        return examRepository.getExamByUserId(uriPath,userId);
    }
}
