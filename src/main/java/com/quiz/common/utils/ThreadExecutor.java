package com.quiz.common.utils;

import com.quiz.user.UserServiceImpl;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor {
    private static final Logger LOG = Logger.getLogger(ThreadExecutor.class);

    public ExecutorService getExecutor() {
        int cores= Runtime.getRuntime().availableProcessors();
        LOG.info("Available cores are ==> " + cores);
        return Executors.newFixedThreadPool(cores);
    }
}
