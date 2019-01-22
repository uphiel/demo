package com.example.demo.test;


import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

public class SslClientTest {
    private static String path = "C:\\Users\\phiel\\Desktop\\trust.ks";
    private static char[] password = "123456".toCharArray();
    private static String httpsUrl = "https://127.0.0.1:10022";
    private static String method = "POST";
    private static String param = "{\"wangfei\":\"wangfei\"}";


    /**
     * @param args
     */
    public static void main(String[] args) {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
        {
            public boolean verify(String hostname,
                                  SSLSession sslsession) {
                return true;
            }
        });
        SSLContext context = null;
        try {
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(new FileInputStream(path), password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            context = SSLContext.getInstance("SSL");
            context.init(null, tm, null);
            SSLSocketFactory ssf = context.getSocketFactory();
            // 创建URL对象
//            URL myURL = new URL(null, httpsUrl, new sun.net.www.protocol.https.Handler());
            URL myURL = new URL(httpsUrl);
//            HostnameVerifier hv = new HostnameVerifier() {
//                public boolean verify(String urlHostName, SSLSession session) {
//                    System.out.println("Warning: URL Host: " + urlHostName + " vs. "
//                            + session.getPeerHost());
//                    return true;
//                }
//            };

            // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
            HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();

            httpsConn.setSSLSocketFactory(ssf);
//            httpsConn.setDefaultHostnameVerifier(hv);
            httpsConn.setDoInput(true);// 打开输入流，以便从服务器获取数据
            httpsConn.setDoOutput(true);// 打开输出流，以便向服务器提交数据
            httpsConn.setRequestMethod(method); // 设置以POST方式提交数据

            httpsConn.setUseCaches(false);// 使用POST不能使用缓存
            //
            // 设置请求的类型是文本类型
            httpsConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if(param.length()>0){
                httpsConn.getOutputStream().write(param.getBytes("UTF-8"));
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpsConn.getInputStream()));
            String line;
            StringBuilder rt = null;
            while ((line = in.readLine()) != null) {
                rt.append(line);
            }
            System.out.println("rt: " + rt);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        SSLSocketFactory ssf = context.getSocketFactory();
//        try {
//
//            SSLSocket ss = (SSLSocket) ssf.createSocket("127.0.0.1", 10022);
//            System.out.println("客户端就绪。");
//            DataOutputStream outputStream=new DataOutputStream(ss.getOutputStream());
////	            outputStream.write("ww".getBytes());
//            outputStream.writeUTF("22");
//            System.out.println("=======");
//            ObjectOutputStream os = new ObjectOutputStream(ss.getOutputStream());
//            os.writeObject("echo : Hello");
////	            ObjectInputStream br = new ObjectInputStream(ss.getInputStream());
//            System.out.println("客户端测试ok");
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
