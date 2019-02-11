package com.wangfei.thread;

public class PrimeGenerator extends Thread {

    public void run() {
        long number = 1L;
        //循环查找质数
        while(true){
            if(isPrime(number)){
                System.out.printf("Number %d is Prime\n", number);
            }
            if(isInterrupted()){
                System.out.println("The Prime Generator has been Interrupted");
                return;
            }
            number++;
        }
    }

    /**
     * 判断入参是否为质数
     * @param number
     * @return
     */
    private boolean isPrime(long number) {
        if (number <=2) {
            return true;
        }

        for (long i=2; i<number; i++){
            if ((number % i)==0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        Thread task=new PrimeGenerator();
        task.start();
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        task.interrupt();
    }
}
