package caseonline.judger.engine.application;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import caseonline.judger.engine.messenger.MessageSender;

/**
 * 测评机心跳.
 */
@Component
public class ApplicationHeartbeat implements Runnable {

	/**
	 * Run.
	 */
	public void run() {
		Calendar calendar = Calendar.getInstance();
		long currentTime = calendar.getTimeInMillis();
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("event", "KeepAlive");
		mapMessage.put("username", username);
		mapMessage.put("description", description);
		mapMessage.put("heartbeatTime", currentTime);
		messageSender.sendMessage(mapMessage);
		LOGGER.info("Heartbeat sent to the web server.");
	}

	/** 消息发送者. */
	@Autowired
	private MessageSender messageSender;

	/** 测评机名称. */
	@Value("${judger.username}")
	private String username;

	/** 测评机描述. */
	@Value("${judger.description}")
	private String description;

	private static final Logger LOGGER = LogManager.getLogger(ApplicationHeartbeat.class);

}
