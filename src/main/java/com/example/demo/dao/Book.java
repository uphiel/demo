package com.example.demo.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Book {
    private LinkedHashMap<String, String> bookUrl;         //书籍地址
    private String bookName;                //书名
    private String author;                       //作者
    private String info;                             //书籍简介
    private String catalog;                     //书籍列表
    private String charCount;                //总章数
    private String url;                              //单章地址
    private String sectionNum;              //第几章
    private String charpterTitle;           //章标题
    private String charpterContent;     //章内容
    private int number;                          //书籍网站数量


    public Book(){}

    public Book(String bookName, String url) throws Exception{
//        if(bookUrl != null){
//            Set<Map.Entry<String, String>> set = bookUrl.entrySet();
//            for(Map.Entry<String, String> entry : set){
//                if(url.equals(entry.getValue())){
//                    throw new Exception("url already exists");
//                }
//            }
//        }
        this.bookName = bookName;
        this.url = url;
        this.bookUrl = new LinkedHashMap<>();
        this.setBookUrl();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public HashMap getBookUrl() {
        return bookUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getInfo() {
        return info;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getCharCount() {
        return charCount;
    }

    public String getSectionNum() {
        return sectionNum;
    }

    public String getCharpterTitle() {
        return charpterTitle;
    }

    public String getCharpterContent() {
        return charpterContent;
    }

    public void setBookUrl() throws Exception{
        int num = 1;
        if(bookUrl==null){
            this.bookUrl.put(String.valueOf(num), this.url);
        }else{
            this.number++;
            this.bookUrl.put(String.valueOf( this.number), this.url);
        }
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setCharCount(String charCount) {
        this.charCount = charCount;
    }

    public void setSectionNum(String sectionNum) {
        this.sectionNum = sectionNum;
    }

    public void setCharpterTitle(String charpterTitle) {
        this.charpterTitle = charpterTitle;
    }

    public void setCharpterContent(String charpterContent) {
        this.charpterContent = charpterContent;
    }

    public void setNumber(int number) {
        int i = 0;
        Set<Map.Entry<String, String>> set = this.bookUrl.entrySet();
        for(Map.Entry<String, String> entry : set){
            i++;
        }
        this.number = i;
    }

    public int getNumber() {
        return number;
    }
}
