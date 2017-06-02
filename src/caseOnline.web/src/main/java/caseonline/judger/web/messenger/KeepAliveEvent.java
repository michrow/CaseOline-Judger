package caseonline.judger.web.messenger;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

public class KeepAliveEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1422795261235372758L;
	
	public KeepAliveEvent(Object source,String judgerName,String judgerDescription,Date heartbeatTime) {
		super(source);
		this.heartbeatTime=heartbeatTime;
		this.judgerDescription=judgerDescription;
		this.judgerName=judgerName;
		
	}
	
	/**
	 * @return the judgerName
	 */
	public String getJudgerName() {
		return judgerName;
	}
	/**
	 * @param judgerName the judgerName to set
	 */
	public void setJudgerName(String judgerName) {
		this.judgerName = judgerName;
	}
	/**
	 * @return the judgerDescription
	 */
	public String getJudgerDescription() {
		return judgerDescription;
	}
	/**
	 * @param judgerDescription the judgerDescription to set
	 */
	public void setJudgerDescription(String judgerDescription) {
		this.judgerDescription = judgerDescription;
	}
	/**
	 * @return the heartbeatTime
	 */
	public Date getHeartbeatTime() {
		return heartbeatTime;
	}
	/**
	 * @param heartbeatTime the heartbeatTime to set
	 */
	public void setHeartbeatTime(Date heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}

	private String judgerName;
	private String judgerDescription;
	private Date heartbeatTime;

}
