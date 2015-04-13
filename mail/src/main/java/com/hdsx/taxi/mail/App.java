package com.hdsx.taxi.mail;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送
 *
 */
public class App {
	public static void main(String[] args) {
		// try {
		// MailService.send_email("314023627@qq.com", "断线提醒", "断线很长时间了");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Map<String ,String> map= new HashMap<String,String>();
		// try {
		// map.put("CorpID", "TCLKJ02243");
		// map.put("Pwd", "5166");
		// map.put("Mobile", "15117986763");
		// map.put("Content", "测试");
		// String result=HttpUtil.getInstance().httpGet(map,
		// "http://inolink.com/ws/Send.aspx");
		// System.out.println(result);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		NoticeThread.getInstance().run(
				Integer.parseInt(PropertiesUtil.getInstance().getProperty(
						"mail.delay")) * 60 * 1000,
				Integer.parseInt(PropertiesUtil.getInstance().getProperty(
						"mail.period")) * 60 * 1000);
	}

}
