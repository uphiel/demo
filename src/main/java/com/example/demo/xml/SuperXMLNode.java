package com.example.demo.xml;



import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SuperXMLNode {
    private String prefix = "";
    private String value = "";
    private String name = "";
    private String cdata = null;
    private Map<String, Attribute> attrs;
    private Map<String, Namespace> namespaces;
    private Map<String, Object> children;

    public SuperXMLNode(String var1) {
        this.name = var1;
        this.attrs = new HashMap();
        this.namespaces = new HashMap();
        this.children = new LinkedHashMap();
    }

    public String getCDATA() {
        return this.cdata;
    }

    public void setCDATA(String var1) {
        this.cdata = var1;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String var1) {
        this.prefix = var1;
    }

    public byte[] getValue(String var1) throws Exception {
        return this.value != null && this.value.length() != 0 ? this.value.getBytes(var1) : null;
    }

    public byte[] getNamespaceValue(String var1, String var2) throws Exception {
        Namespace var3 = (Namespace)this.namespaces.get(var1);
        return var3 == null ? null : var3.getUri().getBytes(var2);
    }

    public String getNamespaceValue(String var1) {
        Namespace var2 = (Namespace)this.namespaces.get(var1);
        return var2 == null ? null : var2.getUri();
    }

    public byte[] getAttributeValue(String var1, String var2) throws Exception {
        Attribute var3 = (Attribute)this.attrs.get(var1);
        return var3 == null ? null : var3.getValue().getBytes(var2);
    }

    public String getAttributeValue(String var1) {
        Attribute var2 = (Attribute)this.attrs.get(var1);
        return var2 == null ? null : var2.getValue();
    }

    public byte[] getCData(String var1) throws Exception {
        return this.cdata != null && this.cdata.length() != 0 ? this.cdata.getBytes(var1) : null;
    }

    public String getCData() {
        return this.cdata != null && this.cdata.length() != 0 ? this.cdata : null;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(byte[] var1, String var2) throws Exception {
        this.value = new String(var1, var2);
    }

    public void setValue(String var1) {
        this.value = var1;
    }

    public String getName() {
        return this.name;
    }

    public void addAttrs(String var1, String var2) {
        Attribute var3 = new Attribute(var1, var2);
        this.attrs.put(var1, var3);
    }

    public void addAttrs(String var1, String var2, String var3) {
        Attribute var4 = new Attribute(var1, var2, var3);
        this.attrs.put(var2, var4);
    }

    public void addAttrs(Attribute var1) {
        this.attrs.put(var1.getName(), var1);
    }

    public void addNamespace(String var1, String var2) {
        Namespace var3 = new Namespace(var1, var2);
        this.namespaces.put(var1, var3);
    }

    public void addChild(SuperXMLNode var1) {
        String var2 = var1.getName();
        Object var3 = this.children.get(var2);
        if (var3 == null) {
            this.children.put(var2, var1);
        } else if (var3 instanceof List) {
            ((List)var3).add(var1);
        } else if (var3 instanceof SuperXMLNode) {
            SuperXMLNode var4 = (SuperXMLNode)var3;
            LinkedList var5 = new LinkedList();
            var5.add(var4);
            var5.add(var1);
            this.children.put(var2, var5);
        }

    }

    public void addChild(String var1, SuperXMLNode var2) {
        Object var3 = this.children.get(var1);
        if (var3 == null) {
            this.children.put(var1, var2);
        } else if (var3 instanceof List) {
            ((List)var3).add(var2);
        } else if (var3 instanceof SuperXMLNode) {
            SuperXMLNode var4 = (SuperXMLNode)var3;
            LinkedList var5 = new LinkedList();
            var5.add(var4);
            var5.add(var2);
            this.children.put(var1, var5);
        }

    }

    public Object getChild(String var1) {
        return this.children.get(var1);
    }

    public SuperXMLNode getChildNode(String var1) {
        Object var2 = this.children.get(var1);
        return var2 instanceof SuperXMLNode ? (SuperXMLNode)var2 : null;
    }

    public List<SuperXMLNode> getChildList(String var1) {
        Object var2 = this.children.get(var1);
        return var2 instanceof List ? (List)var2 : null;
    }

    public void removeChild(String var1) {
        this.children.remove(var1);
    }

    public Map<String, Attribute> getAttrs() {
        return this.attrs;
    }

    public void setAttrs(Map<String, Attribute> var1) {
        this.attrs = var1;
    }

    public Map<String, Namespace> getNamespaces() {
        return this.namespaces;
    }

    public void setNamespaces(Map<String, Namespace> var1) {
        this.namespaces = var1;
    }

    public void setName(String var1) {
        this.name = var1;
    }

    public Map<String, Object> getChildren() {
        return this.children;
    }

    public void setChildren(Map<String, Object> var1) {
        this.children = var1;
    }
}

