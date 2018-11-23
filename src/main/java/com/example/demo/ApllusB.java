package com.example.demo;

import com.example.demo.dao.Book;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Set;

public class ApllusB {
    public static void main(String[] args)  throws Exception{
        System.out.println("result:" + aplusb(3,2));
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("1", "wangfei");
        map.put("2", "wangbo");
        Set set =  map.entrySet();
        Object[] arr = set.toArray();
        for(int i=0; i<arr.length; i++){
            System.out.println(arr[i].toString());
        }

        Book book = new Book("上天台","http://www.biquge.com.tw/2_2456/");
        System.out.println(book.getBookName());
        System.out.println(book.getNumber());

    }

    public static int aplusb(int a, int b) {
        // write your code here
        int a_ = a ^ b;
        int b_ = (a & b) << 1;
//        while (b) {
            a_ = a ^ b;
            b_ = (a & b) << 1;
            a = a_;
            b = b_;
//        }
        return a_ + b_;
    }
}
