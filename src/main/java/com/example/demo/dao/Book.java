package com.example.demo.dao;

import javax.persistence.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Table(name = "Book")
@Entity
public class Book {

    @Column(name = "book_seq", nullable = false)
    private String bookSeq;                  //书籍编号

    @Column(name = "book_name", nullable = false)
    private String bookName;                //书名

    @Column(name = "book_author")
    private String author;                       //作者

    @Column(name = "book_info")
    private String info;                             //书籍简介

    @Column(name = "charcount")
    private String charCount;                //总章数

    @Column(name = "site_no")
    private int number;                          //书籍网站数量

    @Column(name = "site_url", nullable = false)
    private String siteUrl;                    //书籍网站

    public Book(String bookSeq, String bookName) throws Exception {
        this.bookSeq = bookSeq;
        this.bookName = bookName;
    }
    public Book(String bookSeq, String bookName, String siteUrl, String author, String info) {
        this.bookSeq = bookSeq;
        this.bookName = bookName;
        this.siteUrl = siteUrl;
        this.author = author;
        this.info = info;
    }
    public Book(String bookSeq, String bookName, String siteUrl, String author, String info, String charCount, int number) {
        this.bookSeq = bookSeq;
        this.bookName = bookName;
        this.siteUrl = siteUrl;
        this.author = author;
        this.info = info;
        this.charCount = charCount;
        this.number = number;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public String getBookSeq() {
        return bookSeq;
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

    public String getCharCount() {
        return charCount;
    }

    public int getNumber() {
        return number;
    }

    public void setBookSeq(String bookSeq) {
        this.bookSeq = bookSeq;
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

    public void setCharCount(String charCount) {
        this.charCount = charCount;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }


}
