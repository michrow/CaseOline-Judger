package caseonline.judger.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.engine.model.TestcaseRelaCate;

/**
 * The Interface TestcaseRelaCateMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface TestcaseRelaCateMapper {

	/**
	 * 建立新的测试案例类型关系
	 *
	 * @param testcaseRelaCate
	 *            the testcase rela cate
	 * @return the int
	 */
	int createTestcaseCategoryRela(TestcaseRelaCate testcaseRelaCate);

	/**
	 * 跟新测试案例类型关系
	 *
	 * @param testcaseRelaCate
	 *            the testcase rela cate
	 * @return the int
	 */
	int updateTestcaseCategoryRela(TestcaseRelaCate testcaseRelaCate);

	/**
	 * 根据测试案例ID获取测试案例类型关系
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @return the testcase categories by testcase id
	 */
	List<TestcaseRelaCate> getTestcaseCategoriesByTestcaseId(long testcaseId);

	/**
	 * 获取某一测试案例范围内的测试案例类型关系
	 *
	 * @param testcaseIdLowerBound
	 *            the testcase id lower bound
	 * @param testcaseIdUpperBound
	 *            the testcase id upper bound
	 * @return the testcase categories by testcases
	 */
	List<TestcaseRelaCate> getTestcaseCategoriesByTestcases(@Param("testcaseIdLowerBound") long testcaseIdLowerBound,
			@Param("testcaseIdUpperBound") long testcaseIdUpperBound);

	/**
	 * Delete testcase category rela.
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @return the int
	 */
	int deleteTestcaseCategoryRela(long testcaseId);

}