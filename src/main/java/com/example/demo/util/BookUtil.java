package com.example.demo.util;

import org.jsoup.Jsoup;

public class BookUtil {
    public static String getBookTitle(String content, String tagName){
        org.jsoup.nodes.Document doc = Jsoup.parse(content);
        org.jsoup.select.Elements title = doc.select(tagName);
        return title.size() > 0 ? title.get(0).text() : "no title";
    }
}
