package com.example.demo.test;

import com.example.demo.util.HttpNew;
import com.example.demo.util.HttpUtil;

public class Test4HttpNew {
    public static void main(String[] args) throws Exception{
//        String url = "http://www.biquge.com.tw/2_2456/";
        String url = "https://www.xbiquge6.com/78_78513/";
        String charset = "GBK";
        HttpNew httpNew = new HttpNew(url, charset);
//        System.out.println(httpNew.execute(null, HttpUtil.METHOD.GET));
        System.out.println(httpNew.execute(null, HttpUtil.METHOD.POST));
    }
}
