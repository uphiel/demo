package com.example.demo.dao;

import java.util.List;

public class Paragraph {
    private int number;
    private String title;
    private String content;
    private List lists;

    public Paragraph(int number, String content) {
        this.number = number;
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public String getContent() {
        return content;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
