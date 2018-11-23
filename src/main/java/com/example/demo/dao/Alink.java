package com.example.demo.dao;

public class Alink {
    private String text;
    private String link;

    public Alink(){}

    public Alink(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
