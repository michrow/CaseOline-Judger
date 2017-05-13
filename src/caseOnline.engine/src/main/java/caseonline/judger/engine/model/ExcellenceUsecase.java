package caseonline.judger.engine.model;

/**
 * The Class ExcellenceUsecase.
 */
public class ExcellenceUsecase {

	/**
	 * Instantiates a new excellence usecase.
	 */
	public ExcellenceUsecase() {
	}

	/**
	 * 构造函数
	 *
	 * @param submissionId
	 *            --提交ID
	 * @param usecaseCoverage
	 *            --覆盖率
	 * @param usecaseAmount
	 *            --用例数量
	 * @param usecase
	 *            用例
	 * @param testcase
	 *            案例
	 */
	public ExcellenceUsecase(Long submissionId, Float usecaseCoverage, Integer usecaseAmount, String usecase,
			Testcase testcase) {
		this.submissionId = submissionId;
		this.usecaseCoverage = usecaseCoverage;
		this.usecaseAmount = usecaseAmount;
		this.usecase = usecase;
		this.testcase = testcase;
	}

	/** 提交ID. */
	private Long submissionId;

	/** 用例. */
	private String usecase;

	/** 案例. */
	private Testcase testcase;

	/** 用例条数. */
	private Integer usecaseAmount;

	/** 用例覆盖率. */
	private Float usecaseCoverage;

	/** 备用字段 1. */
	private String excebak1;

	/** 备用字段 2. */
	private String excebak2;

	/** 备用字段 3. */
	private String excebak3;

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
	 * Gets the 案例.
	 *
	 * @return the testcase
	 */
	public Testcase getTestcase() {
		return testcase;
	}

	/**
	 * Sets the 案例.
	 *
	 * @param testcase
	 *            the testcase to set
	 */
	public void setTestcase(Testcase testcase) {
		this.testcase = testcase;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getExcebak1() {
		return excebak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param excebak1
	 *            the new 备用字段 1
	 */
	public void setExcebak1(String excebak1) {
		this.excebak1 = excebak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getExcebak2() {
		return excebak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param excebak2
	 *            the new 备用字段 2
	 */
	public void setExcebak2(String excebak2) {
		this.excebak2 = excebak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getExcebak3() {
		return excebak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param excebak3
	 *            the new 备用字段 3
	 */
	public void setExcebak3(String excebak3) {
		this.excebak3 = excebak3;
	}

}