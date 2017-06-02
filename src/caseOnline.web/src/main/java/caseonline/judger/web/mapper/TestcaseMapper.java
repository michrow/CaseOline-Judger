package caseonline.judger.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.web.model.Testcase;

/**
 * The Interface TestcaseMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface TestcaseMapper {

	/**
	 * 根据测试案例ID删除测试案例.
	 *
	 * @param --测试案例ID
	 * @return --删除结果
	 */
	int deleteTestcase(Long testcaseId);

	/**
	 * 新增测试案例.
	 *
	 * @param --新增测试案例对象
	 * @return --新增结果
	 */
	int createTestcase(Testcase testcase);

	/**
	 * 跟新测试案例.
	 *
	 * @param --新的测试案例对象
	 * @return --更新结果
	 */
	int updateTestcase(Testcase testcase);

	/**
	 * 根据测试案例ID获取测试案例对象.
	 *
	 * @param --测试案例ID
	 * @return --测试案例对象
	 */
	Testcase getTestcaseById(@Param("testcaseId") Long testcaseId);

	Testcase getTestcaseByName(@Param("testcaseName") String testcaseName);

	/**
	 * 获取测试案例的总数.
	 *
	 * @return the number of testcase
	 */
	long getNumberOfTestcase();

	/**
	 * 条件查询测试案例总数.
	 *
	 * @param keyword
	 *            --测试案例名字
	 * @param categoryId
	 *            --测试案例的类型ID
	 * @param isPublic
	 *            --是否公开
	 * @return the numberof testcase by condition
	 */
	long getNumberofTestcaseByCondition(@Param("keyword") String keyword, @Param("categoryId") int categoryId,
			@Param("isPublic") boolean isPublic,@Param("userId") long userId);

	/**
	 * 获取测试案例的最小ID.
	 *
	 * @return the lower bound of testcase
	 */
	long getLowerBoundOfTestcase();

	/**
	 * 获取测试案例的最大ID.
	 *
	 * @return the upper bound of testcase
	 */
	long getUpperBoundOfTestcase();

	/**
	 * 获取定量查询测试案例最大ID.
	 *
	 * @param isPublicOnly
	 *            the is public only
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @return the upper bound of testcases with limit
	 */
	long getUpperBoundOfTestcasesWithLimit(@Param("isPublic") boolean isPublicOnly, @Param("testcaseId") long offset,
			@Param("limit") int limit);

	/**
	 * 按条件获取所有的测试案例.
	 *
	 * @param keyword
	 *            the keyword
	 * @param categoryId
	 *            the category id
	 * @param isPublic
	 *            the is public
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @return the all test case by conditon
	 */
	List<Testcase> getAllTestCaseByCondition(@Param("keyword") String keyword, @Param("categoryId") int categoryId,
			@Param("isPublic") boolean isPublic, @Param("testcaseId") long offset, @Param("limit") int limit,@Param("userId") long userId);
}