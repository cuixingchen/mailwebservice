package com.hdsx.taxi.mail.webservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * httpclient方式访问WSDLwebservice工具类 soap1.1标准
 * 
 * @author cuipengfei
 *
 */
public class HttpClientToWebService {

	private static DefaultHttpClient httpClient = new DefaultHttpClient();

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fromtime", "2015-01-01 01:01:00");
		map.put("totime", "2015-01-01 11:11:00");
		 map.put("cphm", "");
		 map.put("name", "");
		 map.put("ID_ECERT", "");
		map.put("jylx", "0,4,5,6");
		String nameSpace = "http://tempuri.org/";
		String url = "http://218.206.107.18:10010/Service_T.asmx?wsdl";
		String method = "queryrundatacount_qingdao";
		sendSoapToWebservice(url, nameSpace, method, map);

		map = new HashMap<String, Object>();
		map.put("fromtime", "2015-01-01 01:01:00");
		map.put("totime", "2015-01-01 11:11:00");
		 map.put("cphm", "");
		 map.put("name", "");
		 map.put("ID_ECERT", "");
		 map.put("PageSize", 20);
		 map.put("CurrPageIndex", 1);
		map.put("jylx", "0,4,5,6");
		method = "queryrundata_qingdao";
		sendSoapToWebservice(url, nameSpace, method, map);
		
		map = new HashMap<String, Object>();
		map.put("fromtime", "2015-01-01 01:01:00");
		map.put("totime", "2015-01-01 11:11:00");
		map.put("jylx", "0,4,5,6");
		method = "queryrundatahz_qingdao";
		sendSoapToWebservice(url, nameSpace, method, map);
	}

	/**
	 * wsdl请求
	 * 
	 * @param url
	 * @param nameSpace
	 * @param method
	 * @param map
	 * @return
	 */
	public static HttpResponse sendSoapToWebservice(String url,
			String nameSpace, String method, HashMap<String, Object> map) {
		HttpResponse response = null;
		try {
			String soapRequestData = getXmlString(method, map, nameSpace);
			HttpPost httppost = new HttpPost(url);
			HttpEntity re = new StringEntity(soapRequestData, HTTP.UTF_8);
			httppost.setHeader("Content-Type",
					"application/text/xml; charset=utf-8");
			httppost.setEntity(re);
			response = httpClient.execute(httppost);
			System.out.println(EntityUtils.toString(httppost.getEntity()));
			if (response != null
					&& 200 == response.getStatusLine().getStatusCode()) {
				readStringXml(EntityUtils.toString(response.getEntity()));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 读取xml
	 * 
	 * @param xml
	 */
	private static void readStringXml(String xml) {
		Document doc = null;
		try {
			System.out.println(xml);
			doc = DocumentHelper.parseText(xml);// 将字符串转为XML
			Element root = doc.getRootElement();
			Iterator iter = root.elements().iterator();
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				System.out.println(recordEle.getName());
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 生成soap协议xml请求条件
	 * 
	 * @param methodName
	 * @param parameter
	 * @param nameSpace
	 * @return
	 */
	private static String getXmlString(String methodName,
			HashMap<String, Object> parameter, String nameSpace) {
		StringBuilder sb = new StringBuilder(
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
						+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
						+ " xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
						+ ">");
		sb.append("<soap:Header/>");
		sb.append("<soap:Body xmlns:m=\""
				+ nameSpace
				+ "\""
				+ " soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\""
				+ ">");
		sb.append("<m:" + methodName + "> ");
		for (String key : parameter.keySet()) {
			sb.append("<" + key + ">" + parameter.get(key) + "</" + key + ">");
		}
		sb.append("</m:" + methodName + ">");
		sb.append("</soap:Body>");
		sb.append("</soap:Envelope>");
		return sb.toString();
	}

}
