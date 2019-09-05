package com.quiz;

import com.quiz.common.utils.ApiUtils;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/api")
public class QuizApiMainApplication extends Application {

   @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties= new HashMap<>();
        properties.put("jersey.config.server.provider.packages", "com.quiz");
        return properties;
    }

}
