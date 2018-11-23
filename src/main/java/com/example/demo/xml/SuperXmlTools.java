package com.example.demo.xml;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class SuperXmlTools {
    private String encoding = "utf-8";
    private boolean packSimple = true;
    private boolean endFlag = false;
    private boolean haveValue = false;
    private byte[] defNamespace = "xmlns".getBytes();
    private byte[] startCData = "<![CDATA[".getBytes();
    private byte[] endCData = "]]>".getBytes();
    private ByteArrayOutputStream output;
    private static XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    private static byte[] digits;
    private static byte[] ltEscape;
    private static byte[] gtEscape;
    private static byte[] andEscape;
    private static byte[] quotEscape;

    static {
        inputFactory.setProperty("javax.xml.stream.supportDTD", false);
        inputFactory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
        inputFactory.setProperty("javax.xml.stream.isCoalescing", true);
        digits = new byte[]{60, 62, 61, 34, 58, 32, 47, 38};
        ltEscape = "&lt;".getBytes();
        gtEscape = "&gt;".getBytes();
        andEscape = "&amp;".getBytes();
        quotEscape = "&quot;".getBytes();
    }

    public void writeStartDocument(boolean var1) throws Exception {
        this.output = new ByteArrayOutputStream();
        if (var1) {
            String var2 = "<?xml version=\"1.0\" encoding=\"" + this.encoding + "\" ?>";
            this.output.write(var2.getBytes(this.encoding));
        }

    }

    public void writeStartElement(String var1, String var2) throws Exception {
        if (this.endFlag) {
            this.output.write(digits[1]);
        }

        this.output.write(digits[0]);
        if (!this.isEmpty(var1)) {
            this.output.write(var1.getBytes(this.encoding));
            this.output.write(digits[4]);
        }

        this.output.write(var2.getBytes(this.encoding));
        this.endFlag = true;
    }

    public void writeAttribute(String var1, String var2, String var3) throws Exception {
        this.writeAttribute(var1, var2, var3.getBytes(this.encoding));
    }

    public void writeAttribute(String var1, String var2, byte[] var3) throws Exception {
        if (!this.endFlag) {
            throw new Exception("属性配置位置不正确,必须在'值'前面");
        } else {
            this.output.write(digits[5]);
            if (!this.isEmpty(var1)) {
                this.output.write(var1.getBytes(this.encoding));
                this.output.write(digits[4]);
            }

            this.output.write(var2.getBytes(this.encoding));
            this.output.write(digits[2]);
            this.output.write(digits[3]);
            this.output.write(this.escapeCharacter(var3));
            this.output.write(digits[3]);
        }
    }

    public void writeNamespace(String var1, String var2) throws Exception {
        this.writeNamespace(var1, var2.getBytes(this.encoding));
    }

    public void writeNamespace(String var1, byte[] var2) throws Exception {
        if (!this.endFlag) {
            throw new Exception("名空间配置位置不正确,必须在'值'前面");
        } else {
            this.output.write(digits[5]);
            this.output.write(this.defNamespace);
            this.output.write(digits[4]);
            this.output.write(var1.getBytes(this.encoding));
            this.output.write(digits[2]);
            this.output.write(digits[3]);
            this.output.write(var2);
            this.output.write(digits[3]);
        }
    }

    public void writeDefaultNamespace(byte[] var1) throws Exception {
        if (!this.endFlag) {
            throw new Exception("缺省名空间配置位置不正确,必须在'值'前面");
        } else {
            this.output.write(digits[5]);
            this.output.write(this.defNamespace);
            this.output.write(digits[2]);
            this.output.write(digits[3]);
            this.output.write(var1);
            this.output.write(digits[3]);
        }
    }

    public void writeCData(byte[] var1) throws Exception {
        if (this.endFlag) {
            this.output.write(digits[1]);
        }

        this.output.write(this.startCData);
        this.output.write(var1);
        this.output.write(this.endCData);
        this.haveValue = true;
        this.endFlag = false;
    }

    public void writeXML(byte[] var1) throws Exception {
        if (this.endFlag) {
            this.output.write(digits[1]);
        }

        this.output.write(var1);
        this.haveValue = true;
        this.endFlag = false;
    }

    public void writeCharacters(byte[] var1) throws Exception {
        if (this.endFlag) {
            this.output.write(digits[1]);
        }

        this.output.write(this.escapeCharacter(var1));
        this.haveValue = true;
        this.endFlag = false;
    }

    public void writeEndElement(String var1, String var2, boolean var3) throws Exception {
        if (!this.haveValue && !var3) {
            if (this.packSimple) {
                this.output.write(digits[6]);
                this.output.write(digits[1]);
            } else {
                this.output.write(digits[1]);
                this.output.write(digits[0]);
                this.output.write(digits[6]);
                if (!this.isEmpty(var1)) {
                    this.output.write(var1.getBytes(this.encoding));
                    this.output.write(digits[4]);
                }

                this.output.write(var2.getBytes(this.encoding));
                this.output.write(digits[1]);
            }
        } else {
            this.output.write(digits[0]);
            this.output.write(digits[6]);
            if (!this.isEmpty(var1)) {
                this.output.write(var1.getBytes(this.encoding));
                this.output.write(digits[4]);
            }

            this.output.write(var2.getBytes(this.encoding));
            this.output.write(digits[1]);
        }

        this.haveValue = false;
        this.endFlag = false;
    }

    public byte[] writeEndDocument() throws Exception {
        byte[] var1 = this.output.toByteArray();
        this.output.close();
        this.output = null;
        return var1;
    }

    public byte[] escapeCharacter(byte[] var1) throws Exception {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        int var3 = 0;

        for(int var4 = 0; var4 < var1.length; ++var4) {
            if (var1[var4] == digits[0]) {
                var2.write(var1, var3, var4 - var3);
                var2.write(ltEscape);
                var3 = var4 + 1;
            } else if (var1[var4] == digits[1]) {
                var2.write(var1, var3, var4 - var3);
                var2.write(gtEscape);
                var3 = var4 + 1;
            } else if (var1[var4] == digits[3]) {
                var2.write(var1, var3, var4 - var3);
                var2.write(quotEscape);
                var3 = var4 + 1;
            } else if (var1[var4] == digits[7]) {
                var2.write(var1, var3, var4 - var3);
                var2.write(andEscape);
                var3 = var4 + 1;
            }
        }

        if (var3 != 0) {
            var2.write(var1, var3, var1.length - var3);
            var1 = var2.toByteArray();
        }

        var2.close();
        return var1;
    }

    public byte[] getXMLNodeByte(SuperXMLNode var1) throws Exception {
        this.writeStartDocument(false);
        this.writeXMLNode(var1);
        return this.writeEndDocument();
    }

    public void writeXMLNode(SuperXMLNode var1) throws Exception {
        this.writeStartElement(var1.getPrefix(), var1.getName());
        Iterator var3 = var1.getNamespaces().keySet().iterator();

        String var2;
        while(var3.hasNext()) {
            var2 = (String)var3.next();
            if (this.isEmpty(var2)) {
                this.writeDefaultNamespace(var1.getNamespaceValue("", this.encoding));
            } else {
                this.writeNamespace(var2, var1.getNamespaceValue(var2, this.encoding));
            }
        }

        var3 = var1.getAttrs().keySet().iterator();

        while(var3.hasNext()) {
            var2 = (String)var3.next();
            this.writeAttribute(((Attribute)var1.getAttrs().get(var2)).getPrefix(), var2, var1.getAttributeValue(var2, this.encoding));
        }

        if (!this.isEmpty(var1.getCDATA())) {
            this.writeCData(var1.getCData(this.encoding));
        }

        if (!this.isEmpty(var1.getValue())) {
            this.writeCharacters(var1.getValue(this.encoding));
        }

        var2 = null;
        var3 = null;
        boolean var4 = false;
        Iterator var6 = var1.getChildren().keySet().iterator();

        while(true) {
            while(var6.hasNext()) {
                String var5 = (String)var6.next();
                var4 = true;
                Object var9 = var1.getChildren().get(var5);
                if (var9 instanceof SuperXMLNode) {
                    this.writeXMLNode((SuperXMLNode)var9);
                } else if (var9 instanceof List) {
                    List var10 = (List)var9;
                    Iterator var8 = var10.iterator();

                    while(var8.hasNext()) {
                        SuperXMLNode var7 = (SuperXMLNode)var8.next();
                        this.writeXMLNode(var7);
                    }
                }
            }

            this.writeEndElement(var1.getPrefix(), var1.getName(), var4);
            return;
        }
    }

    public void writeWsdlNode(SuperXMLNode var1) throws Exception {
        this.writeStartElement(var1.getPrefix(), var1.getName());
        Iterator var3 = var1.getNamespaces().keySet().iterator();

        String var2;
        while(var3.hasNext()) {
            var2 = (String)var3.next();
            if (this.isEmpty(var2)) {
                this.writeDefaultNamespace(var1.getNamespaceValue("", this.encoding));
            } else {
                this.writeNamespace(var2, var1.getNamespaceValue(var2, this.encoding));
            }
        }

        var3 = var1.getAttrs().keySet().iterator();

        while(var3.hasNext()) {
            var2 = (String)var3.next();
            this.writeAttribute(((Attribute)var1.getAttrs().get(var2)).getPrefix(), var2, var1.getAttributeValue(var2, this.encoding));
        }

        if (!this.isEmpty(var1.getCDATA())) {
            this.writeCData(var1.getCData(this.encoding));
        }

        if (!this.isEmpty(var1.getValue())) {
            this.writeCharacters(var1.getValue(this.encoding));
        }

        var2 = null;
        var3 = null;
        boolean var4 = false;
        Iterator var6 = var1.getChildren().keySet().iterator();

        while(true) {
            while(var6.hasNext()) {
                String var5 = (String)var6.next();
                var4 = true;
                Object var9 = var1.getChildren().get(var5);
                if (var9 instanceof SuperXMLNode) {
                    this.writeXMLNode((SuperXMLNode)var9);
                } else if (var9 instanceof List) {
                    List var10 = (List)var9;
                    SuperXMLNode var7;
                    if (var5.startsWith("element")) {
                        var7 = (SuperXMLNode)var10.get(0);
                        var7.addAttrs("minOccurs", "0");
                        var7.addAttrs("maxOccurs", "unbounded");
                        this.writeXMLNode(var7);
                    } else {
                        Iterator var8 = var10.iterator();

                        while(var8.hasNext()) {
                            var7 = (SuperXMLNode)var8.next();
                            this.writeXMLNode(var7);
                        }
                    }
                }
            }

            this.writeEndElement(var1.getPrefix(), var1.getName(), var4);
            return;
        }
    }

    public SuperXMLNode readerXML(byte[] var1) throws Exception {
        LinkedList var2 = new LinkedList();
        String var3 = null;
        XMLEvent var4 = null;
        SuperXMLNode var5 = null;
        SuperXMLNode var6 = null;
        XMLEventReader var7 = null;

        try {
            var7 = inputFactory.createXMLEventReader(new ByteArrayInputStream(var1), this.encoding);

            while(true) {
                while(true) {
                    do {
                        if (!var7.hasNext()) {
                            return var5;
                        }

                        var4 = var7.nextEvent();
                    } while(var4.isStartDocument());

                    if (var4.isStartElement()) {
                        StartElement var17 = var4.asStartElement();
                        String var9 = var17.getName().getLocalPart();
                        var6 = new SuperXMLNode(var9);
                        Iterator var10 = var17.getNamespaces();
                        Namespace var11 = null;

                        while(var10.hasNext()) {
                            var11 = (Namespace)var10.next();
                            var6.addNamespace(var11.getPrefix(), var11.getNamespaceURI());
                        }

                        Iterator var12 = var17.getAttributes();
                        javax.xml.stream.events.Attribute var13 = null;

                        while(var12.hasNext()) {
                            var13 = (javax.xml.stream.events.Attribute)var12.next();
                            var6.addAttrs(var13.getName().getPrefix(), var13.getName().getLocalPart(), var13.getValue());
                        }

                        if (var5 == null) {
                            var5 = var6;
                            var2.add(var9);
                        } else {
                            this.getCurrentNode(var5, var2).addChild(var6);
                            var2.add(var9);
                        }
                    } else if (var4.isCharacters()) {
                        Characters var8 = var4.asCharacters();
                        if (!var8.isWhiteSpace()) {
                            if (var8.isCData()) {
                                var3 = var4.asCharacters().getData();
                                var6.setCDATA(var3);
                            } else {
                                var3 = var4.asCharacters().getData();
                                var6.setValue(var3);
                            }
                        }
                    } else if (var4.isEndElement()) {
                        var2.removeLast();
                    } else if (var4.isEndDocument()) {
                        return var5;
                    }
                }
            }
        } finally {
            if (var7 != null) {
                var7.close();
            }

        }
    }

    private SuperXMLNode getCurrentNode(SuperXMLNode var1, List<String> var2) {
        SuperXMLNode var3 = var1;
        Object var4 = null;
        String var5 = null;
        if (var2 == null) {
            return var1;
        } else {
            for(int var6 = 1; var6 < var2.size(); ++var6) {
                var5 = (String)var2.get(var6);
                var4 = var3.getChildren().get(var5);
                if (var4 instanceof SuperXMLNode) {
                    var3 = (SuperXMLNode)var4;
                } else if (var4 instanceof List) {
                    var3 = (SuperXMLNode)((LinkedList)var4).getLast();
                }
            }

            return var3;
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public SuperXmlTools(String var1) {
        this.encoding = var1;
    }

    public boolean isPackSimple() {
        return this.packSimple;
    }

    public void setPackSimple(boolean var1) {
        this.packSimple = var1;
    }

    private boolean isEmpty(String var1) {
        return var1 == null || var1.length() == 0;
    }
}

