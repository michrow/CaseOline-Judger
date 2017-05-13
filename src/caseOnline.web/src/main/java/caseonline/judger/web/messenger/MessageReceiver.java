package caseonline.judger.web.messenger;

import java.util.Calendar;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class MessageReceiver implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message instanceof MapMessage) {
			final MapMessage mapMessage = (MapMessage) message;

			try {
				String event = mapMessage.getString("event");
				if ("TestFinished".equals(event)) {
					testFinishedHandler(mapMessage);
				}else if ("FileCreateFinished".equals(event)) {
					fileCreateFinishedHandler(mapMessage);
				}else if ("RunBuildFinished".equals(event)) {
					runBuildFinishedHandler(mapMessage);
				}else if ("CoverageParseFinished".equals(event)) {
					coverageParseFinishedHandler(mapMessage);
				}
				else if ("KeepAlive".equals(event)) {
					receiveAliveJudgersHandler(mapMessage);
				} else if ("ErrorOccurred".equals(event)) {
					ErrorHandler(mapMessage);
				} else {
					LOGGER.warn(String.format("Unknown Event Received.[Event=%s]", new Object[] { event }));
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}

		}
	}

	private void coverageParseFinishedHandler(MapMessage mapMessage) throws JMSException {
		long submissionId=mapMessage.getLong("submissionId");
		
		eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, 0.0f, "CoverageParseFinished", true,"Running"));
		LOGGER.info(String.format("Submission #%d returned [RunBuildFinished]", submissionId));		
	}

	private void runBuildFinishedHandler(MapMessage mapMessage) throws JMSException {
		long submissionId=mapMessage.getLong("submissionId");
		eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, 0.0f, "RunBuildFinished", true,"Running"));
		LOGGER.info(String.format("Submission #%d returned [RunBuildFinished]", submissionId));				
	}

	private void fileCreateFinishedHandler(MapMessage mapMessage) throws JMSException {
		long submissionId=mapMessage.getLong("submissionId");
		eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, 0.0f, "fileCreateFinished", true,"Running"));
		LOGGER.info(String.format("Submission #%d returned [fileCreateFinished]", submissionId));		
	}

	private void ErrorHandler(MapMessage mapMessage) throws JMSException {
		long submissionId=mapMessage.getLong("submissionId");
		eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, 0.0f, "System Error", true,"System Error"));
		LOGGER.info(String.format("Submission #%d returned [System Error]", submissionId));
	}

	private void receiveAliveJudgersHandler(MapMessage mapMessage) throws JMSException {
		String judgerName=mapMessage.getString("username");
		String judgerDesc=mapMessage.getString("description");
		long hearbeatTimeMills=mapMessage.getLong("heartbeatTime");
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(hearbeatTimeMills);
		Date heartbeatTime=calendar.getTime();
		
		eventPublisher.publishEvent(new KeepAliveEvent(this, judgerName, judgerDesc, heartbeatTime));
		LOGGER.info(String.format("Received heartbeat from judger [%s]", judgerName));
		
	}

	private void testFinishedHandler(MapMessage mapMessage) throws JMSException {
		long submissionId=mapMessage.getLong("submissionId");
		String judgerResult=mapMessage.getString("judgerResult");
		float coverage= (float) mapMessage.getObject("coverage");
		String message=String.format("\n%s,Result=%s", new Object[]{submissionId,judgerResult});
		eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, coverage, message, true,"AC"));
		LOGGER.info(String.format("Submission #%d judge completed and result: %s", new Object[]{submissionId,judgerResult}));
		
	}
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	private static final Logger LOGGER = LogManager.getLogger(MessageReceiver.class);
}
