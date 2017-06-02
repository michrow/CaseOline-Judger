package caseonline.judger.engine.messenger;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import caseonline.judger.engine.application.ApplicationDispatcher;

/**
 * 消息接受者
 */
public class MessageReceiver implements MessageListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message1) {
		if (message1 instanceof MapMessage) {

			final MapMessage mapMessage = (MapMessage) message1;
			try {
				String event = mapMessage.getString("event");
				if (event.equals("SubmissionCreated")) {
					LOGGER.info("收到任务");
					newSubmissionHandler(mapMessage);
				}
			} catch (JMSException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * 处理新来的消息
	 *
	 * @param message
	 *            the message
	 * @throws JMSException
	 *             the JMS exception
	 */
	private void newSubmissionHandler(MapMessage message) throws JMSException {
		long submissionId = message.getLong("submissionId");
		dispatcher.onSubmissionCreated(submissionId);
	}

	/** The dispatcher. */
	@Autowired
	private ApplicationDispatcher dispatcher;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(MessageReceiver.class);
}
