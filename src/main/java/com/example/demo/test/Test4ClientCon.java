package com.example.demo.test;

import com.example.demo.util.ClientCon;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test4ClientCon {
    public static void main(String[] args) throws IOException {
//        HttpGet httpget = new HttpGet("http://www.biquge.com.tw/2_2456/");
        HttpGet httpget = new HttpGet("https://www.xbiquge6.com/78_78513/");

        // Request configuration can be overridden at the request level.
        // They will take precedence over the one set at the client level.
        httpget.setConfig(ClientCon.getRequestConfig());

        // Execution context can be customized locally.
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(ClientCon.getCookieStore());
        context.setCredentialsProvider(ClientCon.getCredentialsProvider());

        System.out.println("executing request " + httpget.getURI());
        InputStream instream = null;
        try {
            CloseableHttpResponse response = ClientCon.getHttpclient().execute(httpget, context);
            System.out.println(response.getEntity().toString());
            System.out.println(response.getEntity().getContent());
            instream = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GBK"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            // do something useful with the response
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Closing the input stream will trigger connection release
            instream.close();
        }
    }

}

