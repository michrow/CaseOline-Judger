package caseonline.judger.web.model;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Submission.
 */
public class Submission {

	/**
	 * 默认构造函数.
	 */
	public Submission() {
	}

	/**
	 * 构造函数.
	 *
	 * @param submissionId            --提交ID
	 * @param testcase            --案例
	 * @param user            --用户
	 * @param language            --语言
	 * @param submissionsSubmitTime            --提交时间
	 * @param usecase            --用例
	 * @param usecaseAmount            --用例数量
	 * @param usecaseCoverage            覆盖率
	 * @param judgeResult the judge result
	 */
	public Submission(Long submissionId, Testcase testcase, User user, Language language, Date submissionsSubmitTime,
			String usecase, Integer usecaseAmount, Float usecaseCoverage, JudgeResult judgeResult) {
		this(testcase, user, language, submissionsSubmitTime, usecase, usecaseAmount, usecaseCoverage, judgeResult);
		this.submissionId = submissionId;
	}

	/**
	 * Instantiates a new submission.
	 *
	 * @param testcase            the testcase
	 * @param user            the user
	 * @param language            the language
	 * @param submissionSubmitTime the submission submit time
	 * @param usecase            the usecase
	 * @param usecaseAmount            the usecase amount
	 * @param usecaseCoverage            the usecase coverage
	 * @param judgeResult the judge result
	 */
	public Submission(Testcase testcase, User user, Language language, Date submissionSubmitTime, String usecase,
			Integer usecaseAmount, Float usecaseCoverage, JudgeResult judgeResult) {
		super();
		this.testcase = testcase;
		this.user = user;
		this.language = language;
		this.submissionSubmitTime = submissionSubmitTime;
		this.usecase = usecase;
		this.usecaseAmount = usecaseAmount;
		this.usecaseCoverage = usecaseCoverage;
		this.judgeResult = judgeResult;
	}

	/**
	 * Instantiates a new submission.
	 *
	 * @param testcase the testcase
	 * @param currentUser the current user
	 * @param language the language
	 * @param submissionTime the submission time
	 * @param usecase the usecase
	 * @param usecaseSize the usecase size
	 */
	public Submission(Testcase testcase, User currentUser, Language language, Date submissionTime, String usecase,
			int usecaseSize) {
		this.testcase = testcase;
		this.usecase = usecase;
		this.language = language;
		this.user = currentUser;
		this.submissionSubmitTime = submissionTime;
		this.usecaseAmount = usecaseSize;
	}

	/** 提交ID. */
	private Long submissionId;

	/** 测试案例. */
	private Testcase testcase;

	/** 用户. */
	private User user;

	/** 语言. */
	private Language language;

	/** 提交时间. */
	private Date submissionSubmitTime;

	/** 用例. */
	private String usecase;

	/** 用例条数. */
	private Integer usecaseAmount;

	/** 用例覆盖率. */
	private Float usecaseCoverage;

	/** The judge result. */
	private JudgeResult judgeResult;

	/** The judge log. */
	private String judgeLog;
	
	/** 测评机名称 */
	private String judgerName;

	/** 备用字段 1. */
	private String submitbak1;

	/** 备用字段 2. */
	private String submitbak2;

	/** 备用字段 3. */
	private String submitbak3;

	/**
	 * Gets the 提交ID.
	 *
	 * @return the 提交ID
	 */
	public Long getSubmissionId() {
		return submissionId;
	}

	/**
	 * Sets the 提交ID.
	 *
	 * @param submissionId
	 *            the new 提交ID
	 */
	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	/**
	 * Gets the 测试案例.
	 *
	 * @return the 测试案例
	 */
	public Testcase getTestcase() {
		return testcase;
	}

	/**
	 * Sets the 测试案例.
	 *
	 * @param testcase
	 *            the new 测试案例
	 */
	public void setTestcase(Testcase testcase) {
		this.testcase = testcase;
	}

	/**
	 * Gets the 用户.
	 *
	 * @return the 用户
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the 用户.
	 *
	 * @param user
	 *            the new 用户
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the 语言.
	 *
	 * @return the 语言
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets the 语言.
	 *
	 * @param language
	 *            the new 语言
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * Gets the 提交时间.
	 *
	 * @return the 提交时间
	 */
	public Date getSubmissionSubmitTime() {
		return submissionSubmitTime;
	}

	/**
	 * Sets the 提交时间.
	 *
	 * @param submissionSubmitTime the new 提交时间
	 */
	public void setSubmissionSubmitTime(Date submissionSubmitTime) {
		this.submissionSubmitTime = submissionSubmitTime;
	}

	/**
	 * Gets the 用例.
	 *
	 * @return the 用例
	 */
	public String getUsecase() {
		return usecase;
	}

	/**
	 * Sets the 用例.
	 *
	 * @param usecase
	 *            the new 用例
	 */
	public void setUsecase(String usecase) {
		this.usecase = usecase;
	}

	/**
	 * Gets the 用例条数.
	 *
	 * @return the 用例条数
	 */
	public Integer getUsecaseAmount() {
		return usecaseAmount;
	}

	/**
	 * Sets the 用例条数.
	 *
	 * @param usecaseAmount
	 *            the new 用例条数
	 */
	public void setUsecaseAmount(Integer usecaseAmount) {
		this.usecaseAmount = usecaseAmount;
	}

	/**
	 * Gets the 用例覆盖率.
	 *
	 * @return the 用例覆盖率
	 */
	public Float getUsecaseCoverage() {
		return usecaseCoverage;
	}

	/**
	 * Sets the 用例覆盖率.
	 *
	 * @param usecaseCoverage
	 *            the new 用例覆盖率
	 */
	public void setUsecaseCoverage(Float usecaseCoverage) {
		this.usecaseCoverage = usecaseCoverage;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getSubmitbak1() {
		return submitbak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param submitbak1
	 *            the new 备用字段 1
	 */
	public void setSubmitbak1(String submitbak1) {
		this.submitbak1 = submitbak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getSubmitbak2() {
		return submitbak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param submitbak2
	 *            the new 备用字段 2
	 */
	public void setSubmitbak2(String submitbak2) {
		this.submitbak2 = submitbak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getSubmitbak3() {
		return submitbak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param submitbak3
	 *            the new 备用字段 3
	 */
	public void setSubmitbak3(String submitbak3) {
		this.submitbak3 = submitbak3;
	}

	/**
	 * Gets the judge result.
	 *
	 * @return the judgeResult
	 */
	public JudgeResult getJudgeResult() {
		return judgeResult;
	}

	/**
	 * Sets the judge result.
	 *
	 * @param judgeResult            the judgeResult to set
	 */
	public void setJudgeResult(JudgeResult judgeResult) {
		this.judgeResult = judgeResult;
	}

	/**
	 * Gets the judge log.
	 *
	 * @return the judgeLog
	 */
	public String getJudgeLog() {
		return judgeLog;
	}

	/**
	 * Sets the judge log.
	 *
	 * @param judgeLog            the judgeLog to set
	 */
	public void setJudgeLog(String judgeLog) {
		this.judgeLog = judgeLog;
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

}