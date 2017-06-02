package caseonline.judger.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;

import caseonline.judger.engine.model.TestcaseCategory;

/**
 * The Interface TestcaseCategoriesMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface TestcaseCategoryMapper {

	/**
	 * Delete category.
	 *
	 * @param categoryId
	 *            the category id
	 * @return the int
	 */
	int deleteCategory(Integer categoryId);

	/**
	 * Creates the category.
	 *
	 * @param category
	 *            the category
	 * @return the int
	 */
	int createCategory(TestcaseCategory category);

	/**
	 * Update category.
	 *
	 * @param category
	 *            the category
	 * @return the int
	 */
	int updateCategory(TestcaseCategory category);

	/**
	 * Gets the category by type.
	 *
	 * @param categoryType
	 *            the category type
	 * @return the category by type
	 */
	TestcaseCategory getCategoryByType(String categoryType);

	/**
	 * Gets the category by name.
	 *
	 * @param categoryName
	 *            the category name
	 * @return the category by name
	 */
	TestcaseCategory getCategoryByName(String categoryName);

	/**
	 * Gets the category by id.
	 *
	 * @param categoryId
	 *            the category id
	 * @return the category by id
	 */
	TestcaseCategory getCategoryById(int categoryId);

	/**
	 * Gets the all testcase categories.
	 *
	 * @return the all testcase categories
	 */
	List<TestcaseCategory> getAllTestcaseCategories();

}