package caseonline.judger.engine.messenger;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * 发送消息
 * 
 * @author 王肖辉 2017年3月19日
 */

@Component
public class MessageSender {

	/**
	 * 发送消息到消息队列
	 * 
	 * @param message
	 */
	public void sendMessage(final Map<String, Object> message) {
		jmsTemplate.convertAndSend(message);
	}

	@Autowired
	private JmsTemplate jmsTemplate;
}
