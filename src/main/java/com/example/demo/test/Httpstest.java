package com.example.demo.test;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Httpstest {
    private static HttpClientBuilder builder;

    public static void main(String[] args) {

        String url = "https://data.court.gov.cn/shesu/test/api/sxbzxr/portrait/org";
        String authJson = "{\"uid\": \"test\",\"pwd\": \"12qw90op\",\"expire\": 0}";
        String queryJson = "{\"name\": \"三台县四通汽车销售服务有限公司\" }";
        query(url, authJson, queryJson);
    }

    private static void query(String url, String authJson, String queryJson) {
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8888");
//        System.setProperty("https.proxyPort", "8888");
        CloseableHttpClient httpClient = getHttpClient();
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("shesu-auth", authJson);
            List<BasicNameValuePair> params = new
                    ArrayList<BasicNameValuePair>();
            params.add(new BasicNameValuePair("query", queryJson));
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse res = httpClient.execute(request);
            StatusLine sl = res.getStatusLine();
            if (sl != null) {
                System.out.println("Response status: " + sl.getStatusCode());
                HttpEntity body = res.getEntity();
                if (body != null) {
                    String s = EntityUtils.toString(body, "UTF-8");
                    System.out.println(s);
                } else {
                    System.out.println("Response is empty");
                }
            } else {
                System.out.println("Response status: null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
    }

    static CloseableHttpClient getHttpClient() {
        try {
            if (builder == null) {
                builder = HttpClientBuilder.create();
                X509TrustManager tm = new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                HostnameVerifier hv = new HostnameVerifier() {
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                };
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new X509TrustManager[]{tm}, null);
                SSLConnectionSocketFactory f = new
                        SSLConnectionSocketFactory(ctx, hv);
                builder.setSSLSocketFactory(f);
            }
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}