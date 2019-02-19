package com.wangfei.thread.generics;

import java.util.ArrayList;
import java.util.List;

public class Generic<T> {
    /**
     * 成员变量key的类型为T，T的类型由外部指定
     */
    private T key;

    /**
     * @param key 泛型构造方法形参key的类型也为T，T的类型由外部指定
     */
    public Generic(T key) {
        this.key = key;
    }

    /**
     * 泛型方法getKey的返回值类型为T，T的类型由外部指定
     *
     * @return
     */
    public T getKey() {
        return key;
    }

    public static void main(String[] args) {
        //泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
        //传入的实参类型需与泛型的类型参数类型相同，即为Integer.
        Generic<Integer> genericInteger = new Generic<Integer>(123456);

        //传入的实参类型需与泛型的类型参数类型相同，即为String.
        Generic<String> genericString = new Generic<String>("key_vlaue");
        System.out.println("泛型测试, key is " + genericInteger.getKey());
        System.out.println("泛型测试, key is " + genericString.getKey());

        Generic generic = new Generic("111111");
        Generic generic1 = new Generic(4444);
        Generic generic2 = new Generic(55.55);
        Generic generic3 = new Generic(false);

        System.out.println("泛型测试, key is " + generic.getKey());
        System.out.println("泛型测试, key is " + generic1.getKey());
        System.out.println("泛型测试, key is " + generic2.getKey());
        System.out.println("泛型测试, key is " + generic3.getKey());
        System.out.println(genericInteger instanceof Generic);
        System.out.println("-----------------------------------");

        List<String>[] lsa = new ArrayList[10];
        Object o = lsa;
        Object[] oa = (Object[])o;
        List<Integer> li = new ArrayList<Integer>();
        li.add(new Integer(3));
        oa[1] = li;
//        String s = lsa[1].get(0);
        System.out.println("-----------------------------------");

        List<?>[] lsa1 = new ArrayList[10];
        Object o1 = lsa1;
        Object[] oa1 = (Object[])o1;
        List<Integer> li1 = new ArrayList<Integer>();
        li1.add(new Integer(3));
        oa1[1] = li1;
        Integer s = (Integer)lsa1[1].get(0);
        System.out.println(s);

    }
}
