package caseonline.judger.web.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionListener implements HttpSessionListener {
	private static long totalSessions = 0;

	public static long getTotalSessions() {
		return totalSessions;
	}

	private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent session) {
		synchronized (this) {
			++totalSessions;
		}
		LOGGER.debug("Session Created:" + session.getSession().getId());
		LOGGER.debug("Total Sessions:" + totalSessions);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent session) {
		synchronized (this) {
			--totalSessions;

			if (totalSessions < 0) {
				totalSessions = 0;
			}
		}
		LOGGER.debug("Session Destroyed: " + session.getSession().getId());
		LOGGER.debug("Total Sessions: " + totalSessions);
	}
}
