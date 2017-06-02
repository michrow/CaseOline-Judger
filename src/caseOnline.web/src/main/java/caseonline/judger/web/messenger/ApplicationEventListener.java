package caseonline.judger.web.messenger;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 应用程序监听器 用于将消息队列中的消息转发至控制器
 * 
 * @author 王肖辉 2017年4月12日
 */
@Component
public class ApplicationEventListener {

	public ApplicationEventListener() {
		synchronized (this) {
			if (scheduler == null) {
				final int INITIAL_DELAY = 0;
				final int PERIOD = 20;
				scheduler = Executors.newScheduledThreadPool(1);
				scheduler.scheduleAtFixedRate(new Runnable() {

					@Override
					public void run() {
						Calendar calendar = Calendar.getInstance();
						calendar.add(Calendar.SECOND, -PERIOD);
						Date heartbeatTimeDeadline = calendar.getTime();
						for (Iterator<Entry<String, Map<String, Object>>> itr = onlineJudgers.entrySet().iterator(); itr
								.hasNext();) {
							Entry<String, Map<String, Object>> entry = itr.next();
							Date lastHeartbeatTime = (Date) entry.getValue().get("heartbeatTime");
							if (!lastHeartbeatTime.after(heartbeatTimeDeadline)) {
								itr.remove();
							}
						}
					}
				}, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
			}
		}
	}

	@EventListener
	public void submissionEventHadnler(SubmissionEvent event) throws IOException {
		long submissionId = event.getSubmissionId();
		float coverage = event.getCoverage();
		String message = event.getMessage();
		String judgeResult=event.getJudgeResult();
		boolean isCompleted = event.isCompleted();
		SseEmitter sseEmitter = sseEmitters.get(submissionId);
		if (sseEmitter == null) {
			LOGGER.warn(String.format("CANNOT get the SseEmitter for submission #%d.", submissionId));
			return;
		}
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("coverage", coverage);
		mapMessage.put("message", message);
		mapMessage.put("judgeResult", judgeResult);
		sseEmitter.send(mapMessage);
		if (isCompleted) {
			sseEmitter.complete();
			removeSseEmitters(submissionId);
		}
	}

	/**
	 * 注册Server Sent Event的发送者对象.
	 * 
	 * @param submissionId
	 *            - 提交记录的唯一标识符
	 * @param sseEmitter
	 *            - Server Sent Event的发送者对象
	 */
	public void addSseEmitters(long submissionId, SseEmitter sseEmitter) {
		sseEmitters.put(submissionId, sseEmitter);
	}

	/**
	 * 移除Server Sent Event的发送者对象.
	 * 
	 * @param submissionId
	 *            - 提交记录的唯一标识符
	 */
	private void removeSseEmitters(long submissionId) {
		sseEmitters.remove(submissionId);
		for (Entry<Long, SseEmitter> mapEntry : sseEmitters.entrySet()) {
			long currentSubmissionId = mapEntry.getKey();
			if (currentSubmissionId < submissionId) {
				sseEmitters.remove(currentSubmissionId);
			}
		}
	}

	/**
	 * 处理评测机心跳事件.
	 * 
	 * @param event
	 *            - 评测机心跳事件
	 */
	@EventListener
	public void keepAliveEventHandler(KeepAliveEvent event) {
		String judgerUsername = event.getJudgerName();
		String judgerDescription = event.getJudgerDescription();
		Date heartbeatTime = event.getHeartbeatTime();

		Map<String, Object> judgerInformation = new HashMap<String, Object>();
		judgerInformation.put("description", judgerDescription);
		judgerInformation.put("heartbeatTime", heartbeatTime);

		onlineJudgers.put(judgerUsername, judgerInformation);
		
	}

	public long getOnlineJudgerSize() {
		return onlineJudgers.size();
	}
	public Map<String, Map<String, Object>>getOnlineJudgers(){
		return onlineJudgers;
	}
	/**
	 * SseEmitter对象的列表. Map中的Key表示提交记录的唯一标识符. Map中的Value表示对应的SseEmitter对象,
	 * 用于推送实时评测信息.
	 */
	private static Map<Long, SseEmitter> sseEmitters = new Hashtable<Long, SseEmitter>();

	/**
	 * 在线评测机的列表. Map中的Key表示评测机的用户名. Map中的Value表示对应评测机的信息.
	 */
	private static Map<String, Map<String, Object>> onlineJudgers = new Hashtable<String, Map<String, Object>>();

	/**
	 * ScheduledExecutorService对象. 用于定期移除离线的评测机.
	 */
	private static ScheduledExecutorService scheduler = null;

	private static final Logger LOGGER = LogManager.getLogger(ApplicationEventListener.class);
}
