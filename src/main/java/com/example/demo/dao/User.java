package com.example.demo.dao;

public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String address;

    public User(int i, String s, int i1, String address) {
        this.id = i;
        this.name = s;
        this.age = i1;
        this.address = address;
    }
    //省略get和set方法、构造函数

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }
}
