package caseonline.judger.web.messenger;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class MessageSender {
	public void sendMessage(final Map<String, Object> mapMessage) {
		long submissionId=(long) mapMessage.get("submissionId");
		jmsTemplate.convertAndSend(mapMessage);
		LOGGER.info(String.format("Submission task #%d has been created", new Object[]{submissionId}));
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	private static final Logger LOGGER=LogManager.getLogger(MessageSender.class);
}
