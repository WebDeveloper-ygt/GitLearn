package com.quiz.common.exception;

import com.quiz.common.utils.Links;

import java.util.List;

public class CustomException extends  RuntimeException{

    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }
}
