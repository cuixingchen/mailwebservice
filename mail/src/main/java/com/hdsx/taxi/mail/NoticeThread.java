package com.hdsx.taxi.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdsx.taxi.upa.bean.MessageBean;
import com.hdsx.taxi.upa.bean.TcpClientLog;
import com.hdsx.taxi.upa.service.impl.MessageServiceImpl;
import com.hdsx.taxi.upa.service.impl.TcpClientLogServiceImpl;

public class NoticeThread extends AbsThread {

	private static final Logger logger = LoggerFactory
			.getLogger(NoticeThread.class);

	private static NoticeThread obj;

	public static NoticeThread getInstance() {
		if (obj == null) {
			initNoticeThread();
		}
		return obj;
	}

	private synchronized static void initNoticeThread() {
		if (obj == null) {
			obj = new NoticeThread();
		}
	}

	private NoticeThread() {
		super();
	}

	private Timer timer = new Timer();

	@Override
	public void runThread(long delay, long period) {
		logger.info("通知线程启动");

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					List<MessageBean> noticeList = MessageServiceImpl
							.getInstance().getAll();
					Iterator<MessageBean> it = noticeList.iterator();
					MessageBean messageBean = null;
					HashMap<String, Object> map = null;
					HashMap<String, Object> sort = null;
					List<TcpClientLog> logList = null;
					while (it.hasNext()) {
						messageBean = it.next();
						if ("0".equals(messageBean.getState())) {
							continue;
						}
						map = new HashMap<String, Object>();
						map.put("xzqhdm", messageBean.getXzqhdm());
						sort = new HashMap<String, Object>();
						sort.put("time", -1);
						logList = TcpClientLogServiceImpl.getInstance()
								.getSort(map, sort, 1);
						if (logList != null) {
							TcpClientLog logbean = logList.get(0);
							int mintes = Integer.parseInt(PropertiesUtil
									.getInstance().getProperty("notice.time"));
							long times = (new Date()).getTime()
									- logbean.getTime().getTime();
							if (logbean.getConnectype() == 0
									&& times > mintes * 60 * 1000) {
								/**
								 * 发邮件提醒
								 */
								if (messageBean.getImail() != null
										&& !"".equals(messageBean.getImail())) {
									String[] imail = messageBean.getImail()
											.split(",");
									for (int i = 0; i < imail.length; i++) {
										try {
											MailService.send_email(imail[0],
													"断线提醒",
													messageBean.getMessage());
										} catch (Exception e) {
											logger.error("邮件提醒：", e);
										}
									}
								}

								/**
								 * 发短信提醒
								 */
								if (messageBean.getPhone() != null
										&& !"".equals(messageBean.getPhone())) {
									String[] phone = messageBean.getPhone()
											.split(",");
									for (int i = 0; i < phone.length; i++) {
										Map<String, String> mapcondition = new HashMap<String, String>();
										try {
											mapcondition.put("CorpID",
													"TCLKJ02243");
											mapcondition.put("Pwd", "5166");
											mapcondition
													.put("Mobile", phone[0]);
											mapcondition.put("Content",
													messageBean.getMessage());
											// String result =
											HttpUtil.getInstance()
													.httpGet(mapcondition,
															"http://inolink.com/ws/Send.aspx");
											// System.out.println(result);
										} catch (Exception e) {
											logger.error("短信提醒：", e);
										}
									}
								}
							}

						}
					}
				} catch (Exception e) {
					logger.error("线程最外层捕获异常", e);
				}
			}

		}, delay * 1000, period * 1000);
	}

}
