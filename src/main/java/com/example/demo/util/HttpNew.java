package com.example.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.Map;

public class HttpNew {
    /**
     * 要请求的url
     */
    private String url;

    /**
     * 编码
     */
    private String encode;

    /**
     * http的响应
     */
    private HttpResponse response;

    private static CookieStore cookieStore = new BasicCookieStore();

    private String filePath;//小说存放地址

    private OutputStream os;

    public HttpNew(String url, String encode) {
        this.url = url;
        this.encode = encode;
    }

    public HttpNew(String url, String encode, String filePath) throws FileNotFoundException {
        this.url = url;
        this.encode = encode;
        this.filePath = filePath;
        this.os = new FileOutputStream(new File(filePath));
    }

    public HttpNew(String url, String encode, String filePath, boolean append) throws FileNotFoundException {
        this.url = url;
        this.encode = encode;
        this.filePath = filePath;
        this.os = new FileOutputStream(new File(filePath), append);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setOs(String path) {
        try {
            this.os = new FileOutputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public OutputStream getOs() {
        return os;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public String getUrl() {
        return url;
    }

    public String getEncode() {
        return encode;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    /**
     * 方法
     */
    public enum METHOD {
        POST, GET
    }

    // Create a custom response handler
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

        public String handleResponse(
                final HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
//                return entity != null ? EntityUtils.toString(entity) : null;
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(instream, encode));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    try {
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        // do something useful with the response
                        return sb.toString();
                    } catch (IOException ex) {
                        // In case of an IOException the connection will be released
                        // back to the connection manager automatically
                        throw ex;
                    } finally {
                        // Closing the input stream will trigger connection release
                        instream.close();
                    }
                }
            } else {
//                if(response.getStatusLine().getStatusCode()==521){
//                    HttpEntity entity = response.getEntity();
//                    String html= EntityUtils.toString(entity,"utf-8");
//                    System.out.println(html);
//                    //处理从服务器返回的JS，并执行
//                    String js=html.trim().replace("<script>", "").replace("</script>", "").replace("eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]}))","y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]})");
//                    V8 runtime = V8.createV8Runtime();
//                    String result=runtime.executeStringScript(js);
//                    System.out.println(result);
//                }
//                throw new ClientProtocolException("Unexpected response status: " + status);
            }
return "";
        }
    };

    public String execute(Map<String, String> param, HttpUtil.METHOD m)  throws Exception{
        String result = null;
        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = this.getCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        // 创建一个HttpClient
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();

//        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = null;
//        HttpPost httpPost = null;
        try {
//            if (url.startsWith("https")) {
                httpget = new HttpGet(url);
                result = httpclient.execute(httpget, responseHandler, context);
//            } else {
//                httpPost = new HttpPost(url);
//                System.out.println("Executing request " + httpget.getRequestLine());
//                result = httpclient.execute(httpPost, responseHandler, context);
//            }
            //发送请求
            showCookie(cookieStore);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new Exception("执行方法出错", e);
        }
        return result;
    }
    public void showCookie(CookieStore cookieStore){
        for (Cookie c : cookieStore.getCookies()) {
            System.out.println(c.getName() + ": " + c.getValue());
        }
    }

    public String execute(String address)throws Exception{
        HttpNew httpNew = new HttpNew(address, this.getEncode());
        String content = httpNew.execute(null, HttpUtil.METHOD.GET).replaceAll("<!doctype html>", "<html>").replaceAll("&nbsp;", "&#160;");
//        System.out.println("接收到的内容：" + content + "\n");
        return  content;
    }

}
