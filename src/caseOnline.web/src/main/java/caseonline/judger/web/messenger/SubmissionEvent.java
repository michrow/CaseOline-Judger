package caseonline.judger.web.messenger;

import org.springframework.context.ApplicationEvent;

public class SubmissionEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6790136532750988780L;
	public SubmissionEvent(Object source,long submissionId,float coverage,String message,boolean isCompleted,String judgeResult) {
		super(source);
		this.isCompleted=isCompleted;
		this.coverage=coverage;
		this.message=message;
		this.submissionId=submissionId;
		this.judgeResult=judgeResult;
		
	}
	private long submissionId;
	/**
	 * @return the submissionId
	 */
	public long getSubmissionId() {
		return submissionId;
	}
	/**
	 * @param submissionId the submissionId to set
	 */
	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the isCompleted
	 */
	public boolean isCompleted() {
		return isCompleted;
	}
	/**
	 * @param isCompleted the isCompleted to set
	 */
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	/**
	 * @return the coverage
	 */
	public float getCoverage() {
		return coverage;
	}
	/**
	 * @param coverage the coverage to set
	 */
	public void setCoverage(float coverage) {
		this.coverage = coverage;
	}
	/**
	 * @return the judgeResult
	 */
	public String getJudgeResult() {
		return judgeResult;
	}
	/**
	 * @param judgeResult the judgeResult to set
	 */
	public void setJudgeResult(String judgeResult) {
		this.judgeResult = judgeResult;
	}
	private float coverage;
	private String message;
	private boolean isCompleted;
	private String judgeResult;
}
