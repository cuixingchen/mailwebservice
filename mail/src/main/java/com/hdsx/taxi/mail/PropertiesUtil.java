package com.hdsx.taxi.mail;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {

	/**
	 * 配置文件地址
	 */
	private static final String configFileName = "/email.properties";

	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesUtil.class);

	private static Properties obj;

	private synchronized static void initProperties() {
		if (obj == null) {
			obj = new Properties();
			try {
				obj.load(PropertiesUtil.class.getResourceAsStream(configFileName));
			} catch (IOException e) {
				logger.error("配置文件" + configFileName + "初始化错误："
						+ e.getMessage());
			}
		}
	}

	/**
	 * 单例
	 * 
	 * @return
	 */
	public static Properties getInstance() {
		if (obj == null) {
			initProperties();
		}
		return obj;
	}
}
