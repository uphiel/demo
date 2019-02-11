package com.wangfei.thread.basic;

public class Main {
    public static void main(String[] args){
        EventStorage storage = new EventStorage();
        Producer producer = new Producer(storage);
        Consumer con = new Consumer(storage);
        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(con);

        thread1.start();
        thread2.start();
    }
}
