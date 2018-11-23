package com.example.demo.xml;

public class Attribute {
    private String prefix = "";
    private String name = "";
    private String value = "";

    public Attribute(String var1, String var2) {
        this.name = var1;
        this.value = var2;
    }

    public Attribute(String var1, String var2, String var3) {
        this.prefix = var1;
        this.name = var2;
        this.value = var3;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String var1) {
        this.prefix = var1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String var1) {
        this.name = var1;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String var1) {
        this.value = var1;
    }
}