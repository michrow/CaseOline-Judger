package caseonline.judger.engine.exception;

/**
 * 无效的提交记录异常. 当getSubmission(long)操作返回null时被抛出.
 * 
 */
public class IllgealSubmissionException extends Exception {
	/**
	 * IllgealSubmissionException的构造函数.
	 * 
	 * @param message
	 *            - 错误消息
	 */
	public IllgealSubmissionException(String message) {
		super(message);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9019235951964656553L;
}
