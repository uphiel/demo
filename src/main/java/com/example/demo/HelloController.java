package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index(){
        return "Hello world1" + "";
    }

    @RequestMapping("/helloname")
    public String name(String name){
        System.out.println("name: " + name);
        return name;
    }
}
