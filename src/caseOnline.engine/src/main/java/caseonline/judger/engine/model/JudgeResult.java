package caseonline.judger.engine.model;

/**
 * The Class JudgeResult.
 */
public class JudgeResult {

	/**
	 * 默认构造函数
	 */
	public JudgeResult() {
		super();
	}

	/**
	 * 构造函数
	 *
	 * @param resultFlag
	 *            --标志
	 * @param resultName
	 *            --名称
	 */
	public JudgeResult(String resultFlag, String resultName) {
		super();
		this.resultFlag = resultFlag;
		this.resultName = resultName;
	}

	/**
	 * 构造函数
	 *
	 * @param resultId
	 *            --ID
	 * @param resultFlag
	 *            the result flag
	 * @param resultName
	 *            the result name
	 */
	public JudgeResult(int resultId, String resultFlag, String resultName) {
		this(resultFlag, resultName);
		this.resultId = resultId;

	}

	/**
	 * Gets the 测评结果ID.
	 *
	 * @return the 测评结果ID
	 */
	public int getResultId() {
		return resultId;
	}

	/**
	 * Sets the 测评结果ID.
	 *
	 * @param resultId
	 *            the new 测评结果ID
	 */
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	/**
	 * Gets the 测评结果标志.
	 *
	 * @return the 测评结果标志
	 */
	public String getResultFlag() {
		return resultFlag;
	}

	/**
	 * Sets the 测评结果标志.
	 *
	 * @param resultFlag
	 *            the new 测评结果标志
	 */
	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	/**
	 * Gets the 测评结果名称.
	 *
	 * @return the 测评结果名称
	 */
	public String getResultName() {
		return resultName;
	}

	/**
	 * Sets the 测评结果名称.
	 *
	 * @param resultName
	 *            the new 测评结果名称
	 */
	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	/** 测评结果ID. */
	private int resultId;

	/** 测评结果标志. */
	private String resultFlag;

	/** 测评结果名称. */
	private String resultName;

}
