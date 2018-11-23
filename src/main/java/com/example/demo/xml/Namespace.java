package com.example.demo.xml;

public class Namespace {
    private String prefix = "";
    private String uri = "";

    public Namespace(String var1, String var2) {
        this.prefix = var1;
        this.uri = var2;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String var1) {
        this.prefix = var1;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String var1) {
        this.uri = var1;
    }
}
