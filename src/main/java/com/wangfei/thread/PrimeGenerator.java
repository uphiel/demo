package com.wangfei.thread;

public class PrimeGenerator implements Runnable {

    public void run() {
        long number = 1L;
        while(true){
            if(isPrime(number)){
                System.out.printf("Number %d is Prime", number);
            }
        }

        if(isInterrupted()){
            System.out.printf("The Prime Generator has been Interrupted");
            return;
        }
        number++;
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

    private boolean isInterrupted(){
       this.
    }
}
