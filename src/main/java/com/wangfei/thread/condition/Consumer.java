package com.wangfei.thread.condition;

import java.util.Random;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        if(buffer.hasPendingLines()){
            String line = buffer.get();
            processLine(line);
        }
    }

    private void processLine(String line) {
        Random random = new Random();
        try {
            Thread.sleep((random.nextInt()+1)*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
