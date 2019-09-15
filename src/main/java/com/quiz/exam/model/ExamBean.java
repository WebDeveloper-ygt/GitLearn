package com.quiz.exam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import com.quiz.common.utils.Links;
import com.quiz.user.model.UserBean;

@XmlRootElement(name = "exam")
@Entity
public class ExamBean {
    private int examId;
    private String examName ;
    private int examDuration ;
    private int negativeMarks;
    private int numberOfQuestions ;
    private List<AttendeesBean> attendeesList = new ArrayList<>();
    private String examStatus;
    private UserBean user;

    public ExamBean() {

    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public int getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(int negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public List<AttendeesBean> getAttendeesList() {
        return attendeesList;
    }

    public void setAttendeesList(List<AttendeesBean> attendeesList) {
        this.attendeesList = attendeesList;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ExamBean{" +
                "examId=" + examId +
                ", examName='" + examName + '\'' +
                ", examDuration=" + examDuration +
                ", negativeMarks=" + negativeMarks +
                ", numberOfQuestions=" + numberOfQuestions +
                ", attendeesList=" + attendeesList +
                ", examStatus='" + examStatus + '\'' +
                ", user=" + user +
                '}';
    }
}
