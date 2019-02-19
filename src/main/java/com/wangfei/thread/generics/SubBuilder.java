package com.wangfei.thread.generics;

abstract class SuperBuilder<T extends SuperBuilder<T>> {
    private int retryLimit = 0;

    T retryLimit(int retryLimit){
        this.retryLimit = retryLimit;
        return (T)this;
    }

}
public class SubBuilder extends SuperBuilder<SubBuilder>{
    private long delay = 0;

    SuperBuilder delay(long delay){
        this.delay = delay;
        return this;
    }
    public static void main(String[] args){
        System.out.println(new SubBuilder().retryLimit(2).delay(60000).getClass());
    }
}
