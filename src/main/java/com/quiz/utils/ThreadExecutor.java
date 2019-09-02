package com.quiz.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor implements ThreadCreator {

    @Override
    public ExecutorService getExecutor(int threads) {
        return Executors.newFixedThreadPool(threads);
    }
}

interface ThreadCreator{
    ExecutorService getExecutor(int threads);
}