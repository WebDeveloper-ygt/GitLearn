package com.quiz.exception;

import com.quiz.utils.Links;

import java.util.List;

public class CustomException extends  Exception{
    private String message;
    private int statusCode;
    private String description;
    private List<Links> links;

    private static final long serialVersionUID = 1L;

    public CustomException() {

    }
    public CustomException(String message, int statusCode, String description, List<Links>links) {
        this.message = message;
        this.statusCode = statusCode;
        this.description = description;
        this.links = links;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public List<Links> getLinks() {
        return links;
    }
    public void setLinks(List<Links> links) {
        this.links = links;
    }

}
