package com.quiz.common.utils;

public class Constants {
        public static final String USERS="select * from quiz_users";
        public static final String USERS_ID="select * from quiz_users where userId=";
        public static final String USER_EMAIL ="select * from quiz_users where emailId=";
        public static final String USER_DELETE ="DELETE FROM quiz_users WHERE userId=";
        public static final String USERS_EXAMS="select * from quiz_exams where userId=";
        public static final String EXAMS="select * from quiz_exams";
        public static final String EXAMS_ID="select * from quiz_exams where examId=";
        public static final String EXAM_DELETE ="DELETE FROM quiz_exams WHERE examId=";
        public static final String QUESTIONS_EXAMID ="SELECT * FROM quiz_questions where examId=";
        public static final String QUSETION_OPTIONS="SELECT * FROM question_options where questionId=";
        public static final String SECRET ="cXVpemFwaXNlY3JldGlzcWx3eXNmdW51c2luZ2plcmVzeQ==";
        public static final String LOGIN="select * from quiz_users where emailId=? and password=?";
        public static final String ALL ="all";
}
