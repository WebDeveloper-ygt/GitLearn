package com.quiz.common.utils;

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
}
