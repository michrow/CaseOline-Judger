package caseonline.judger.web.model;

/**
 * 测试案例类型
 */
public class TestcaseCategory {

	/**
	 * Instantiates a new testcase category.
	 */
	public TestcaseCategory() {
	}

	/**
	 * Instantiates a new testcase category.
	 *
	 * @param categoryType
	 *            the category type
	 * @param categoryName
	 *            the category name
	 * @param preCategoryId
	 *            the pre category id
	 */
	public TestcaseCategory(String categoryType, String categoryName, Integer preCategoryId) {
		super();
		this.categoryType = categoryType;
		this.categoryName = categoryName;
		this.preCategoryId = preCategoryId;
	}

	/**
	 * Instantiates a new testcase category.
	 *
	 * @param categoryId
	 *            the category id
	 * @param categoryType
	 *            the category type
	 * @param categoryName
	 *            the category name
	 * @param preCategoryId
	 *            the pre category id
	 */
	public TestcaseCategory(Integer categoryId, String categoryType, String categoryName, Integer preCategoryId) {
		this(categoryType, categoryName, preCategoryId);
		this.categoryId = categoryId;

	}

	/** 分类ID. */
	private Integer categoryId;

	/** 分类类型. */
	private String categoryType;

	/** 分类名字. */
	private String categoryName;

	/** 父类型. */
	private Integer preCategoryId;

	/** 备用字段 1. */
	private String catebak1;

	/** 备用字段 2. */
	private String catebak2;

	/** 备用字段 3. */
	private String catebak3;

	/**
	 * Gets the 分类ID.
	 *
	 * @return the 分类ID
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the 分类ID.
	 *
	 * @param categoryId
	 *            the new 分类ID
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Gets the 分类类型.
	 *
	 * @return the 分类类型
	 */
	public String getCategoryType() {
		return categoryType;
	}

	/**
	 * Sets the 分类类型.
	 *
	 * @param categoryType
	 *            the new 分类类型
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * Gets the 分类名字.
	 *
	 * @return the 分类名字
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the 分类名字.
	 *
	 * @param categoryName
	 *            the new 分类名字
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Gets the 父类型.
	 *
	 * @return the 父类型
	 */
	public Integer getPreCategoryId() {
		return preCategoryId;
	}

	/**
	 * Sets the 父类型.
	 *
	 * @param preCategoryId
	 *            the new 父类型
	 */
	public void setPreCategoryId(Integer preCategoryId) {
		this.preCategoryId = preCategoryId;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getCatebak1() {
		return catebak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param catebak1
	 *            the new 备用字段 1
	 */
	public void setCatebak1(String catebak1) {
		this.catebak1 = catebak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getCatebak2() {
		return catebak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param catebak2
	 *            the new 备用字段 2
	 */
	public void setCatebak2(String catebak2) {
		this.catebak2 = catebak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getCatebak3() {
		return catebak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param catebak3
	 *            the new 备用字段 3
	 */
	public void setCatebak3(String catebak3) {
		this.catebak3 = catebak3;
	}

}