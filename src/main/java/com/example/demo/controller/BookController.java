package com.example.demo.controller;

import com.example.demo.dao.Alink;
import com.example.demo.dao.Book;
import com.example.demo.dao.Paragraph;
import com.example.demo.util.BookUtil;
import com.example.demo.util.HttpNew;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class BookController extends BookLogger {
//    @Autowired
//    private BookRepository bookRepository;

    public String DETAIL = "/detail?address=";

    @RequestMapping("/boot/list")
    public String getList(String address, Model model) throws Exception {
        //小说地址http://www.biquge.com.tw/2_2456/
        //获取页面内容
        System.out.println("address: " + address);
        URL url = new URL(address);
//        下面的代码忽略cookie，会被网站挂黑名单
//        URLConnection connection = url.openConnection();
//        connection.getInputStream();
//        InputStream in = connection.getInputStream();
//        InputStreamReader isr = new InputStreamReader(in, "GBK");
//        BufferedReader br = new BufferedReader(isr);
//        String line;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        br.close();
//        isr.close();
//        in.close();
//        String content = sb.toString().replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");
//        System.out.println("接收到的内容：" + content + "\n");

//        下列代码是httpclient4.5版本之前的
//        HttpUtil httpUtil = new HttpUtil(address, "utf-8");
//
//        httpUtil.execute(null, HttpUtil.METHOD.GET);
//        System.out.println(httpUtil.getResponse().getEntity());
//        BufferedReader br = new BufferedReader(new InputStreamReader(httpUtil.getResponse().getEntity().getContent(), "GBK"));
//        StringBuffer sb = new StringBuffer();
//        String line = "";
//        String NL = System.getProperty("line.separator");
//        while((line = br.readLine()) != null){
//            sb.append(line + NL);
//        }
//        br.close();
//        String content = sb.toString().replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");
        //httpclient4.5版本
//        HttpNew httpNew = new HttpNew(address, "GBK");
//        String content = httpNew.execute(null, HttpUtil.METHOD.GET).replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");
//        System.out.println("接收到的内容：" + content + "\n");
        String filePath = "D:\\novel\\list.txt";
        HttpNew httpNew = new HttpNew(address, "GBK", filePath);
        String content = httpNew.execute(address);
        //页面内容处理
        org.jsoup.nodes.Document doc = Jsoup.parse(content);
        org.jsoup.select.Elements title = doc.select("h1");
        String titleText = "";
        if (title.size() > 0) {
            System.out.println(title.get(0).text());
            //获取小说名称
            titleText = title.get(0).text();
        } else {
            System.out.println("no title");
        }

        //获取简介
        String info = doc.select("#intro p").text();
        org.jsoup.select.Elements contents = doc.select("#list dl dd");
        List<Alink> lists = new ArrayList<Alink>();
        String temp = "";
        if (contents != null) {
            for (int i = 0; i < contents.size(); i++) {
                Alink alink = new Alink();
//                lists.add(contents.get(i).text());
                temp = contents.get(i).html().replaceAll("&lt;", "<");
                temp = temp.replaceAll("&gt;", ">");
//                System.out.println(temp);
                alink.setText(contents.get(i).text());
                alink.setLink(DETAIL + url.getProtocol() + "://" + url.getHost() + contents.get(i).child(0).attr("href"));
                httpNew.getOs().write((alink.getText() + alink.getLink()).getBytes());
                httpNew.getOs().write("\n".getBytes());
                lists.add(alink);
            }
            httpNew.getOs().write(titleText.getBytes());
            httpNew.getOs().write("\n\n".getBytes());
        }
//        filePath.lastIndexOf(92)
        File file = new File(filePath.substring(0, filePath.lastIndexOf(92)) + titleText + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        String bookSeq = String.valueOf(System.currentTimeMillis());
        Book book = new Book(bookSeq, titleText, address, "", info);
        model.addAttribute("bookTitle", titleText);
        model.addAttribute("bookInfo", info);
        model.addAttribute("bookList", lists);
        return "/list";
    }

    @RequestMapping("/detail")
    public String getContent(String address, Model model) throws Exception {
        URL url = new URL(address);
//        URLConnection connection = url.openConnection();
//        connection.getInputStream();
//        InputStream in = connection.getInputStream();
//        InputStreamReader isr = new InputStreamReader(in, "GBK");
//        BufferedReader br = new BufferedReader(isr);
//        String line;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        br.close();
//        isr.close();
//        in.close();
//
//        String content = sb.toString().replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");

//httpclient4.5版本
//        HttpNew httpNew = new HttpNew(address, "GBK");
//        String content = httpNew.execute(null, HttpUtil.METHOD.GET).replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");
//        System.out.println("接收到的内容：" + content + "\n");

        String content = new HttpNew(address, "GBK").execute(address);
        //页面内容处理
        org.jsoup.nodes.Document doc = Jsoup.parse(content);

        //获取上一章和下一章地址
        Elements eles = doc.select(".bottem1 a");
        for (org.jsoup.nodes.Element ele : eles) {
            if ("上一章".equals(ele.text())) {
                model.addAttribute("lastLink", DETAIL + url.getProtocol() + "://" + url.getHost() + ele.attr("href"));
            } else if ("下一章".equals(ele.text())) {
                model.addAttribute("nextLink", DETAIL + url.getProtocol() + "://" + url.getHost() + ele.attr("href"));
            } else if ("章节目录".equals(ele.text())) {
                model.addAttribute("listCharpterLink", DETAIL + url.getProtocol() + "://" + url.getHost() + ele.attr("href"));
            }
        }
//        获取段落内容
        String[] paras = doc.select("#content").html().split("<br>");
        List<Paragraph> lists = new ArrayList<Paragraph>();
        for (int i = 0; i < paras.length; i++) {
            Paragraph para = new Paragraph(i + 1, paras[i]);
            lists.add(para);
        }
        model.addAttribute("lists", lists);
        model.addAttribute("booktitle", doc.select(".bookname h1").text());
        return "/detail";
    }

    @RequestMapping("/boot/download")
    public void download(String address, Model model) throws Exception {
        System.out.println("address: " + address);
        URL url = new URL(address);
        HttpNew httpNew = new HttpNew(address, "GBK");
        String content = httpNew.execute(address);
        String bookTitle = BookUtil.getBookTitle(content, "h1");

    }

//    @RequestMapping("/book/query")
//    @ResponseBody
//    public Book findByName(String bookName){
//        Book book = bookRepository.findName(bookName);
//        System.out.println(book.getBookName());
//        return book;
//    }
//
//    @RequestMapping("/book/insert")
//    @ResponseBody
//    public String insert(Book book){
//        bookRepository.save(book);
//        return null;
//    }

}
