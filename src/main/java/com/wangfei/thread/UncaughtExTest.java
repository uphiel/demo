package com.wangfei.thread;

public class UncaughtExTest implements Runnable{
    public static void main(String[] args){
        UncaughtExTest test = new UncaughtExTest();
        Thread thread = new Thread(test);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
    }

    @Override
    public void run() {
        int number = Integer.parseInt("ttt");
    }
}
