package caseonline.judger.web.model;

/**
 * 测试案例类型关系
 */
public class TestcaseRelaCate {

	/**
	 * Instantiates a new testcase rela cate.
	 */
	public TestcaseRelaCate() {
	}

	/**
	 * Instantiates a new testcase rela cate.
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param category
	 *            the category
	 */
	public TestcaseRelaCate(Long testcaseId, TestcaseCategory category) {
		super();
		this.testcaseId = testcaseId;
		this.category = category;
	}

	/** 测试案例ID. */
	private Long testcaseId;

	/** 类型. */
	private TestcaseCategory category;

	/** 备用字段 1. */
	private String relabak1;

	/** 备用字段 2. */
	private String relabak2;

	/** 备用字段 3. */
	private String relabak3;

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
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getRelabak1() {
		return relabak1;
	}

	/**
	 * Gets the 类型.
	 *
	 * @return the 类型
	 */
	public TestcaseCategory getCategory() {
		return category;
	}

	/**
	 * Sets the 类型.
	 *
	 * @param category
	 *            the new 类型
	 */
	public void setCategory(TestcaseCategory category) {
		this.category = category;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param relabak1
	 *            the new 备用字段 1
	 */
	public void setRelabak1(String relabak1) {
		this.relabak1 = relabak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getRelabak2() {
		return relabak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param relabak2
	 *            the new 备用字段 2
	 */
	public void setRelabak2(String relabak2) {
		this.relabak2 = relabak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getRelabak3() {
		return relabak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param relabak3
	 *            the new 备用字段 3
	 */
	public void setRelabak3(String relabak3) {
		this.relabak3 = relabak3;
	}

}