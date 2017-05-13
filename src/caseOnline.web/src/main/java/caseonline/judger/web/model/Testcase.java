package caseonline.judger.web.model;

/**
 * 测试案例
 */
public class Testcase {

	/**
	 * Instantiates a new testcase.
	 */
	public Testcase() {
	}

	/**
	 * Instantiates a new testcase.
	 *
	 * @param isPublic
	 *            the is public
	 * @param testcaseName
	 *            the testcase name
	 * @param language
	 *            the language
	 * @param testcaseDescription
	 *            the testcase description
	 * @param testcaseParamAttr
	 *            the testcase param attr
	 * @param testcaseCode
	 *            the testcase code
	 */
	public Testcase(boolean isPublic, String testcaseName, Language language, String testcaseDescription,
			String testcaseParamAttr, String testcaseCode) {
		super();
		this.isPublic = isPublic;
		this.testcaseName = testcaseName;
		this.language = language;
		this.testcaseDescription = testcaseDescription;
		this.testcaseParamAttr = testcaseParamAttr;
		this.testcaseCode = testcaseCode;
	}

	/**
	 * Instantiates a new testcase.
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param isPublic
	 *            the is public
	 * @param testcaseName
	 *            the testcase name
	 * @param language
	 *            the language
	 * @param testcaseDescription
	 *            the testcase description
	 * @param testcaseParamAttr
	 *            the testcase param attr
	 * @param testcaseCode
	 *            the testcase code
	 */
	public Testcase(Long testcaseId, boolean isPublic, String testcaseName, Language language,
			String testcaseDescription, String testcaseParamAttr, String testcaseCode) {
		this(isPublic, testcaseName, language, testcaseDescription, testcaseParamAttr, testcaseCode);
		this.testcaseId = testcaseId;

	}

	/** 测试案例ID. */
	private Long testcaseId;

	/** 是否开放. */
	private boolean isPublic;

	/** 测试案例名字. */
	private String testcaseName;

	/** 语言ID. */
	private Language language;

	/** 用户总提交量. */
	private int totalSubmission;

	/** 总的提交用户量. */
	private int totalUserSubmission;

	/** 最大覆盖率 . */
	private float maxCoverage;

	/** 测试案例代码. */
	private String testcaseCode;

	/** 测试案例方法参数信息. */
	private String testcaseParamAttr;

	/** 测试案例描述. */
	private String testcaseDescription;

	/** 备用字段 1. */
	private String casebak1;

	/** 备用字段 2. */
	private String casebak2;

	/** 备用字段 3. */
	private String casebak3;

	/**
	 * Gets the 测试案例ID.
	 *
	 * @return the 测试案例ID
	 */
	public Long getTestcaseId() {
		return testcaseId;
	}

	/**
	 * Sets the 测试案例ID.
	 *
	 * @param testcaseId
	 *            the new 测试案例ID
	 */
	public void setTestcaseId(Long testcaseId) {
		this.testcaseId = testcaseId;
	}

	/**
	 * Checks if is public.
	 *
	 * @return true, if is public
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * Sets the public.
	 *
	 * @param isPublic
	 *            the new public
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * Gets the 测试案例名字.
	 *
	 * @return the 测试案例名字
	 */
	public String getTestcaseName() {
		return testcaseName;
	}

	/**
	 * Sets the 测试案例名字.
	 *
	 * @param testcaseName
	 *            the new 测试案例名字
	 */
	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	/**
	 * Gets the 语言ID.
	 *
	 * @return the 语言ID
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets the 语言ID.
	 *
	 * @param language
	 *            the new 语言ID
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * Gets the 用户总提交量.
	 *
	 * @return the 用户总提交量
	 */
	public int getTotalSubmission() {
		return totalSubmission;
	}

	/**
	 * Sets the 用户总提交量.
	 *
	 * @param totalSubmission
	 *            the new 用户总提交量
	 */
	public void setTotalSubmission(int totalSubmission) {
		this.totalSubmission = totalSubmission;
	}

	/**
	 * Gets the 总的提交用户量.
	 *
	 * @return the 总的提交用户量
	 */
	public int getTotalUserSubmission() {
		return totalUserSubmission;
	}

	/**
	 * Sets the 总的提交用户量.
	 *
	 * @param totalUserSubmission
	 *            the new 总的提交用户量
	 */
	public void setTotalUserSubmission(int totalUserSubmission) {
		this.totalUserSubmission = totalUserSubmission;
	}

	/**
	 * Gets the 最大覆盖率 .
	 *
	 * @return the 最大覆盖率
	 */
	public float getMaxCoverage() {
		return maxCoverage;
	}

	/**
	 * Sets the 最大覆盖率 .
	 *
	 * @param maxCoverage
	 *            the new 最大覆盖率
	 */
	public void setMaxCoverage(float maxCoverage) {
		this.maxCoverage = maxCoverage;
	}

	/**
	 * Gets the 测试案例代码.
	 *
	 * @return the 测试案例代码
	 */
	public String getTestcaseCode() {
		return testcaseCode;
	}

	/**
	 * Sets the 测试案例代码.
	 *
	 * @param testcaseCode
	 *            the new 测试案例代码
	 */
	public void setTestcaseCode(String testcaseCode) {
		this.testcaseCode = testcaseCode;
	}

	/**
	 * Gets the 测试案例方法参数信息.
	 *
	 * @return the 测试案例方法参数信息
	 */
	public String getTestcaseParamAttr() {
		return testcaseParamAttr;
	}

	/**
	 * Sets the 测试案例方法参数信息.
	 *
	 * @param testcaseParamAttr
	 *            the new 测试案例方法参数信息
	 */
	public void setTestcaseParamAttr(String testcaseParamAttr) {
		this.testcaseParamAttr = testcaseParamAttr;
	}

	/**
	 * Gets the 测试案例描述.
	 *
	 * @return the 测试案例描述
	 */
	public String getTestcaseDescription() {
		return testcaseDescription;
	}

	/**
	 * Sets the 测试案例描述.
	 *
	 * @param testcaseDescription
	 *            the new 测试案例描述
	 */
	public void setTestcaseDescription(String testcaseDescription) {
		this.testcaseDescription = testcaseDescription;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getCasebak1() {
		return casebak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param casebak1
	 *            the new 备用字段 1
	 */
	public void setCasebak1(String casebak1) {
		this.casebak1 = casebak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getCasebak2() {
		return casebak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param casebak2
	 *            the new 备用字段 2
	 */
	public void setCasebak2(String casebak2) {
		this.casebak2 = casebak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getCasebak3() {
		return casebak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param casebak3
	 *            the new 备用字段 3
	 */
	public void setCasebak3(String casebak3) {
		this.casebak3 = casebak3;
	}

}