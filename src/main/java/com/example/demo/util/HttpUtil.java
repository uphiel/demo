package com.example.demo.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http工具类
 */
public class HttpUtil {
	private static CookieStore cookiestore;

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
//	private Logger log = Logger.getLogger(HttpUtil.class);

	/**
	 * 构造函数
	 * @param url
	 * @param encode
	 */
	public HttpUtil(String url, String encode) {
		this.url = url;
		this.encode = encode;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCookiestore(CookieStore cookiestore) {
		this.cookiestore = cookiestore;
	}

	public CookieStore getCookiestore() {
		return cookiestore;
	}

	/**
	 * 方法
	 */
	public enum METHOD {
		POST, GET
	}


	/**
	 * 获取post方法
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HttpPost getPost(Map<String , String> param) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(url);
		//设置超时，防止等待时间过长
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
		post.setConfig(requestConfig);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Set<String> keySet = param.keySet();
		for (String key : keySet) {
			NameValuePair nameValuePair = new BasicNameValuePair(key, param.get(key));
			list.add(nameValuePair);
		}
		post.setEntity(new UrlEncodedFormEntity(list, encode));
		return post;
	}
	
	/**s
	 * 获取post方法 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HttpPost getPostStream(Map<String , String> param) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(url);
		//设置超时，防止等待时间过长
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
		post.setConfig(requestConfig);
		String data = String.valueOf(param.get("data"));
		StringEntity entity = new StringEntity(data,Charset.forName("UTF-8"));
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/octet-stream");//二进制流数据
		post.setEntity(entity);
		return post;
	}
	
	/**
	 * get方法
	 * @param param
	 * @return
	 */
	public HttpGet getGet(Map<String , String> param) {
		HttpGet get = new HttpGet(url );
		if(param != null){
			get = new HttpGet(url + "?" + encodeUrl(param));
		}
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();//设置请求和传输超时时间
		get.setConfig(requestConfig);
		get.addHeader("Content-type" , "text/html; charset=utf-8");
		return get;
	}
	
	private String encodeUrl(Map<String , String> param) {
		StringBuilder val = new StringBuilder();
		Set<String> keySet = param.keySet();
		try {  
			for (String key : keySet) {
				val.append(key);
				val.append("=");
				val.append(URLEncoder.encode(param.get(key), "UTF-8"));
				val.append("&");
			}
		} catch (UnsupportedEncodingException e) {
//			log.error(e);
			e.printStackTrace();
		}

		return val.toString();
	}
	
	/**
	 * 调用http请求
	 * @param param
	 * @param m
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public void execute(Map<String , String> param, METHOD m) throws Exception {
		HttpClient httpClient;
        try{
            if (url.startsWith("https")) {
                httpClient = new SSLClient();
            } else {
                httpClient = new DefaultHttpClient();
			}
            if(this.getCookiestore() != null){
				((DefaultHttpClient) httpClient).setCookieStore(cookiestore);
			}
			HttpUriRequest request = null;
            if (m == METHOD.POST) {		// 调用post方法
                //request = getPost(param);
            	request = getPostStream(param);
            } else if (m == METHOD.GET) {		// 调用get方法
                request = getGet(param);
            }
            setResponse(httpClient.execute(request));
			if(this.cookiestore==null){
				this.setCookiestore(((DefaultHttpClient) httpClient).getCookieStore());
			}
        } catch (IOException e) {
            throw new Exception("执行方法出错",e);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("执行方法出错",e);
        } catch (KeyManagementException e) {
            throw new Exception("执行方法出错",e);
        }
	}
	
	/**
	 * 获取请求的状态
	 * @return
	 */
	public int getStatusCode() throws Exception {
		if (response == null) {
			throw new Exception("请现请求http，然后在获取消息状态");
		}
		return response.getStatusLine().getStatusCode();
	}
	
	/**
	 * 获取请求返回的字符串
	 * @return
	 * @throws Exception
	 */
	public String getReturnStr() throws Exception {
		if (response == null) {
			throw new Exception("请现请求http，然后在获取消息状态");
		}
		HttpEntity entity = response.getEntity();
        String str = null;
        try {
            str = EntityUtils.toString(entity, encode);
        } catch (IOException e) {
            throw new Exception("IO exception",e);
        }
        return str;
	}
	
	class SSLClient extends DefaultHttpClient {
		public SSLClient() throws NoSuchAlgorithmException, KeyManagementException {
			super();
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {


				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					//do nothing
				}


				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					//do nothing
				}


				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

			};
			ctx.init(null, new TrustManager[] { tm }, null);

			SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = this.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
		}
	}
}
