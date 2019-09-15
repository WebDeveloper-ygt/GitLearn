package com.quiz;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

@ApplicationPath("/api")
public class QuizApiMainApplication extends Application {

   @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties= new HashMap<>();
        properties.put("jersey.config.server.provider.packages", "com.quiz");
        return properties;
    }

    /*@Override
    public Set<Class<?>> getClasses() {
       Set<Class<?>> classes = new HashSet<>();
       classes.add(DeclarativeLinkingFeature.class);
        return classes;
    }
*/
   /* final Application application;

    {
        application = new ResourceConfig().packages("org.glassfish.jersey.examples.linking")
                .register(DeclarativeLinkingFeature.class);
    }*/
}
