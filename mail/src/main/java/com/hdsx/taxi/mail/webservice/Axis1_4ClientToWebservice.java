package com.hdsx.taxi.mail.webservice;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

/**
 * axis1.4访问webservice工具类
 * 
 * @author cuipengfei
 *
 */
public class Axis1_4ClientToWebservice {

	public static void main(String[] args) {
		// Web服务的URL
		String url = "http://218.206.107.18:10010/Service_T.asmx?wsdl";
		String nameSpace="http://tempuri.org/";
		String method="queryrundatacount_qingdao";
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			// 注册序列化/反序列化器
			// QName qn = new QName("urn:PersonInfo", "Person");
			// call.registerTypeMapping(Person.class, qn, new
			// BeanSerializerFactory(
			// Person.class, qn),
			// new BeanDeserializerFactory(Person.class, qn));
//			QName qnb = new QName("http://tempuri.org/", "map");
			// call.registerTypeMapping(Book.class, qnb, new
			// BeanSerializerFactory(
			// Book.class, qnb), new BeanDeserializerFactory(Book.class, qnb));

			// 设置调用方法
			call.setOperationName(new javax.xml.namespace.QName(
					nameSpace,method ));
			 //参数  
			call.addParameter(new QName(nameSpace,"fromtime"), org.apache.axis.encoding.XMLType.XSD_STRING,  
			javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName(nameSpace,"totime"), org.apache.axis.encoding.XMLType.XSD_STRING,  
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName(nameSpace,"jylx"), org.apache.axis.encoding.XMLType.XSD_STRING,  
					javax.xml.rpc.ParameterMode.IN);
			//返回类型  
			call.setReturnType(new QName(nameSpace,method),Vector.class);  

			call.setUseSOAPAction(false);  
			call.setSOAPActionURI(nameSpace+method);  

			Map<String, String> map = new HashMap<String, String>();
			map.put("fromtime", "2015-01-01 01:01:00");
			map.put("totime", "2015-03-03 01:01:00");
			// map.put("cphm", "京A12345");
			// map.put("name", "小样儿");
			// map.put("ID_ECERT", "130425789652145623");
			map.put("jylx", "0,4,5,6");
			// Web服务调用
			java.lang.Object _resp;
			try {
				_resp = call
						.invoke(new java.lang.Object[] { "2015-01-01 01:01:00","2015-01-01 01:01:00","0,4,5,6" });
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
