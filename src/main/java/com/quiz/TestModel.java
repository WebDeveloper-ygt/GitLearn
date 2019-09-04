package com.quiz;

import java.util.List;

import com.quiz.common.utils.Links;

public class TestModel {

    private String name;
    private int age;
    private String job;
    private List<Links> links;
    
    public TestModel() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

}