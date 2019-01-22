package com.wangfei.thread;

public class Calculator implements Runnable{
    private int number;

    public Calculator(int number){
        this.number = number;
    }
    public void run() {
        for(int i = 0; i < 10; i++){
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), i, number, i * number);
        }
    }
    public static void main(String[] args){
        for(int i = 0; i < 10; i++){
            Calculator cal = new Calculator(i);
            Thread thread = new Thread(cal);
            thread.run();
        }
    }
}
