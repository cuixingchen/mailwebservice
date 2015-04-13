package com.hdsx.taxi.mail;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * httpclient工具类
 * @author cuipengfei
 *
 */
public class HttpUtil {

	static HttpUtil obj;

	private static synchronized void HttpUtilInit() {
		if (obj == null) {
			obj = new HttpUtil();
		}
	}

	public static HttpUtil getInstance() {
		if (obj == null) {
			HttpUtilInit();
		}
		return obj;
	}

	private HttpUtil() {
		super();
	}

	public String httpGet(Map<String, String> map, String url) throws Exception {
		return httpGet(map,url,null);
	}
	
	public String httpGet(Map<String, String> map, String url,BasicCookieStore cookieStore) throws Exception {
		if(cookieStore==null){
			cookieStore= new BasicCookieStore();
		}
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(url);

		if (null != map) {

			// urlBuilder.append("/");
			urlBuilder.append("?");

			Iterator<Entry<String, String>> iterator = map.entrySet()
					.iterator();

			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				urlBuilder.append(URLEncoder.encode(param.getKey(), "UTF-8"))
						.append('=')
						.append(URLEncoder.encode(param.getValue(), "GBK"));
				if (iterator.hasNext()) {
					urlBuilder.append('&');
					// urlBuilder.append('/');
				}
			}
		}
		try {
			HttpGet httpget = new HttpGet(urlBuilder.toString());
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();
				return EntityUtils.toString(entity);

			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}
	}

	public String httpPost(Map<String, String> map, String url)
			throws Exception {
		return httpPost(map,url,null);
	}
	
	public String httpPost(Map<String, String> map, String url,BasicCookieStore cookieStore)
			throws Exception {
		if(cookieStore==null){
			cookieStore = new BasicCookieStore();
		}
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// nvps.add(new BasicNameValuePair("nickName", "nick"));
		for (Map.Entry<String, String> entry : map.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(post);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);

	}

}
