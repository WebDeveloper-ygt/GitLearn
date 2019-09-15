package com.quiz.common.utils;

import com.quiz.exam.model.ExamBean;
import com.quiz.user.model.UserBean;

import javax.ws.rs.core.Response;
import java.util.List;

public class BeanValidation {

    public static boolean UserPostValidation(UserBean userBean) {
        return userBean.getEmailId() != null && !userBean.getEmailId().equalsIgnoreCase("") &&
                userBean.getFirstName() != null && !userBean.getFirstName().equalsIgnoreCase("") &&
                userBean.getLastName() != null && !userBean.getLastName().equalsIgnoreCase("") &&
                userBean.getInstituteName() != null && !userBean.getInstituteName().equalsIgnoreCase("") &&
                userBean.getPassword() != null && !userBean.getPassword().equalsIgnoreCase("") &&
                userBean.getPhoneNumber() != null && !userBean.getPhoneNumber().equalsIgnoreCase("") &&
                userBean.getUserName() != null && !userBean.getUserName().equalsIgnoreCase("");

    }

    public static String userUpdateValidation(Response checkUser, UserBean user, int userId) {
        List<UserBean> entity = (List<UserBean>) checkUser.getEntity();
        String updateUser = null;
        entity.iterator();
        for (UserBean userBean : entity) {
            String userName = ((user.getUserName() == null) || (user.getUserName().equalsIgnoreCase("")))
                    ? (userBean.getUserName()) : (user.getUserName());
            String firstName = ((user.getFirstName() == null) || (user.getFirstName().equalsIgnoreCase("")))
                    ? (userBean.getFirstName()) : (user.getFirstName());
            String lastName = ((user.getLastName() == null) || (user.getLastName().equalsIgnoreCase("")))
                    ? (userBean.getLastName()) : (user.getLastName());
            String emailId = ((user.getEmailId() == null) || (user.getEmailId().equalsIgnoreCase("")))
                    ? (userBean.getEmailId()) : (user.getEmailId());
            String phoneNumber = ((user.getPhoneNumber() == null) || (user.getPhoneNumber().equalsIgnoreCase("")))
                    ? (userBean.getPhoneNumber()) : (user.getPhoneNumber());
            String instituteName = ((user.getInstituteName() == null) || (user.getInstituteName().equalsIgnoreCase("")))
                    ? (userBean.getInstituteName()) : (user.getInstituteName());

            updateUser = "UPDATE `quizapi`.`quiz_users` SET `userName` = '" + userName + "',`firstName` = '"
                    + firstName + "',`lastName` = '" + lastName + "' ,`emailId` ='" + emailId + "',`phoneNumber` = '"
                    + phoneNumber + "',`instituteName` = '" + instituteName + "' WHERE `userId` = " + userId + "";
        }
        return updateUser;
    }

    public static boolean ExamPostValidation(ExamBean examBean) {
        return examBean.getExamDuration() != 0 && examBean.getExamDuration() >= 30 &&
                examBean.getExamName() != null && !examBean.getExamName().equalsIgnoreCase("") &&
                examBean.getExamStatus() != null && !examBean.getExamStatus().equalsIgnoreCase("") &&
                examBean.getNegativeMarks() != 0 && examBean.getNegativeMarks() > 0 &&
                examBean.getNumberOfQuestions() != 0 && examBean.getNumberOfQuestions() >= 30;
    }

    public static String examUpdateValidation(ExamBean updatedExamBean, ExamBean examBean) {
        int examDuration = (examBean.getExamDuration() < 30) ? updatedExamBean.getExamDuration() : examBean.getExamDuration();
        String examName = ((examBean.getExamName() == null || examBean.getExamName().equalsIgnoreCase(""))) ? updatedExamBean.getExamName() : examBean.getExamName();
        String examStatus = ((examBean.getExamStatus() == null || examBean.getExamStatus().equalsIgnoreCase(""))) ? updatedExamBean.getExamStatus() : examBean.getExamStatus();
        int negativeMarks = (examBean.getNegativeMarks() <= 0) ? updatedExamBean.getNegativeMarks() : examBean.getNegativeMarks();
        int numberOfQuestions = (examBean.getNumberOfQuestions() < 30) ? updatedExamBean.getNumberOfQuestions() : examBean.getNumberOfQuestions();
        int userId = updatedExamBean.getUser().getUserId();
        int examId = updatedExamBean.getExamId();
        String updatedExam = "UPDATE `quizapi`.`quiz_exams` SET `examName` = '" + examName + "',`examDuration` = '" + examDuration + "',`negativeMarks` = '" + negativeMarks + "'," +
                "`numberOfQuestions` = '" + numberOfQuestions + "',`examStatus` ='" + examStatus + "' WHERE `examId` = '" + examId + "' AND `userId` ='" + userId + "'";
        return updatedExam;
    }
}
