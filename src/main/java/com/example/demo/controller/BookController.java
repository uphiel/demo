package com.example.demo.controller;

import com.example.demo.dao.Alink;
import com.example.demo.dao.Book;
import com.example.demo.dao.Paragraph;
import com.example.demo.dao.User;
import com.example.demo.xml.SuperXMLNode;
import com.example.demo.xml.SuperXmlTools;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Controller
public class BookController extends BookLogger {
    public String DETAIL = "/detail?address=";
    @RequestMapping("/boot/list")
    public String getList(String address, Model model) throws Exception {
        //小说地址http://www.biquge.com.tw/2_2456/
        //获取页面内容
        System.out.println("address: " + address);
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.getInputStream();
        InputStream in = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(in, "GBK");
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        in.close();
//        System.out.println(sb.toString());
        String content = sb.toString().replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");
        System.out.println("接收到的内容：" + content + "\n");

        //页面内容处理
        org.jsoup.nodes.Document doc = null;
//            doc = DocumentHelper.parseText(content);
//            Element root = doc.getRootElement();
//            System.out.println(root.getData());

//            SuperXmlTools xmlTools = new SuperXmlTools("GBK");
//            SuperXMLNode root = xmlTools.readerXML(content.getBytes("GBK"));
//            Map<String, Object> nodeMap = (Map<String, Object>) root.getChildren();
//            Set<Map.Entry<String, Object>> set = nodeMap.entrySet();
//            for(Map.Entry<String, Object> entry : set){
//                System.out.println(entry.getKey() + " --> " + entry.getValue());
//            }

        doc = Jsoup.parse(content);
        org.jsoup.select.Elements title = doc.select("h1");
        System.out.println(title.get(0).text());
        //获取小说名称
        String titleText = title.get(0).text();
        //获取简介
        String info = doc.select("#intro p").text();
        org.jsoup.select.Elements contents = doc.select("#list dl dd");
        List<Alink> lists = new ArrayList<Alink>();
        String temp = "";
        if(contents !=null){
            for(int i = 0; i < contents.size(); i++){
                Alink alink = new Alink();
//                lists.add(contents.get(i).text());
                temp = contents.get(i).html().replaceAll("&lt;","<");
                temp = temp.replaceAll("&gt;", ">");
//                System.out.println(temp);
                alink.setText(contents.get(i).text());
                alink.setLink(DETAIL+url.getProtocol()+"://"+url.getHost()+contents.get(i).child(0).attr("href"));
                lists.add(alink);
            }

        }

        model.addAttribute("bookTitle",titleText);
        model.addAttribute("bookInfo",info);
        model.addAttribute("bookList",lists);
        return "/list";
    }

    @RequestMapping("/detail")
    public String getContent(String address, Model model) throws Exception {
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.getInputStream();
        InputStream in = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(in, "GBK");
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        in.close();

        String content = sb.toString().replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");

        //页面内容处理
        org.jsoup.nodes.Document doc = Jsoup.parse(content);

        //获取上一章和下一章地址
        Elements eles = doc.select(".bottem1 a");
        for(org.jsoup.nodes.Element ele : eles){
            if("上一章".equals(ele.text())){
                model.addAttribute("lastLink", DETAIL+url.getProtocol()+"://"+url.getHost()+ele.attr("href"));
            }else if("下一章".equals(ele.text())){
                model.addAttribute("nextLink", DETAIL+url.getProtocol()+"://"+url.getHost()+ele.attr("href"));
            }
        }
//        获取段落内容
        String[] paras = doc.select("#content").html().split("<br>");
        List<Paragraph> lists = new ArrayList<>();
        for(int i = 0; i < paras.length; i++){
            Paragraph para = new Paragraph(i+1, paras[i]);
            lists.add(para);
        }
        model.addAttribute("lists", lists);
        model.addAttribute("booktitle", doc.select(".bookname h1").text());
        return "/detail";
    }
}