package caseonline.judger.web.model;

import java.util.Date;

/**
 * 测评机信息
 */
public class Judgers {
	
	/**
	 * Instantiates a new judgers.
	 */
	public Judgers(){
		
	}
	
	/**
	 * Instantiates a new judgers.
	 *
	 * @param judgerName the judger name
	 * @param heartbeatTime the heartbeat time
	 * @param judgerDesc the judger desc
	 */
	public Judgers(String judgerName, Date heartbeatTime, String judgerDesc) {
		super();
		this.judgerName = judgerName;
		this.heartbeatTime = heartbeatTime;
		this.judgerDesc = judgerDesc;
	}
	
	/**
	 * Gets the judger name.
	 *
	 * @return the judger name
	 */
	public String getJudgerName() {
		return judgerName;
	}
	
	/**
	 * Sets the judger name.
	 *
	 * @param judgerName the new judger name
	 */
	public void setJudgerName(String judgerName) {
		this.judgerName = judgerName;
	}
	
	/**
	 * Gets the heartbeat time.
	 *
	 * @return the heartbeat time
	 */
	public Date getHeartbeatTime() {
		return heartbeatTime;
	}
	
	/**
	 * Sets the heartbeat time.
	 *
	 * @param heartbeatTime the new heartbeat time
	 */
	public void setHeartbeatTime(Date heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}
	
	/**
	 * Gets the judger desc.
	 *
	 * @return the judger desc
	 */
	public String getJudgerDesc() {
		return judgerDesc;
	}
	
	/**
	 * Sets the judger desc.
	 *
	 * @param judgerDesc the new judger desc
	 */
	public void setJudgerDesc(String judgerDesc) {
		this.judgerDesc = judgerDesc;
	}

	/** The judger name. */
	private String judgerName;
	
	/** The heartbeat time. */
	private Date heartbeatTime;
	
	/** The judger desc. */
	private String judgerDesc;
}
