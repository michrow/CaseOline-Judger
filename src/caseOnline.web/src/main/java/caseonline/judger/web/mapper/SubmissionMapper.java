package caseonline.judger.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.web.model.Submission;

/**
 * The Interface SubmissionsMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface SubmissionMapper {

	/**
	 * 删除提交.
	 *
	 * @param --提交ID
	 * @return --新增结果（0：失败，1：成功）
	 */
	int deleteSubmission(@Param("submissionId") Long submissionId);

	/**
	 * 新增提交.
	 *
	 * @param --新增的提交对象
	 * @return --新增结果
	 */
	int createSubmission(Submission submission);

	/**
	 * 更新提交.
	 *
	 * @param --要更新的提交对象
	 * @return --更新结果
	 */
	int updateSubmission(Submission submission);

	/**
	 * 通过提交ID获取提交对象.
	 *
	 * @param --提交对象ID
	 * @return 获取的提交对象
	 */
	Submission getSubmissionById(@Param("submissionId") Long submissionId);

	/**
	 * 根据用户ID获取用户最新的提交.
	 *
	 * @param --用户ID
	 * @return 获取的提交对象集合
	 */
	List<Submission> getLatestSubmissionsOfUser(@Param("userId") long userId);

	/**
	 * 根据语言ID获取提交对象.
	 *
	 * @param 语言ID
	 * @return 获取提交对象集合
	 */
	List<Submission> getSubmissionsByLanguageId(@Param("languageId") int languageId);

	/**
	 * 通过用户ID获取用户提交的总量.
	 *
	 * @param --用户ID
	 * @return --用户提交的总量
	 */
	long getTotalSubmissionByUserId(@Param("userId") long userId);

	/**
	 * 通过用户ID和测试案例ID查询提交.
	 *
	 * @param --测试案例ID
	 * @param --用户名
	 * @param --每次查询数量
	 * @return --查询结果集合
	 */
	List<Submission> getSubmissionsByTcIdOrUId(@Param("testcaseId") long testcaseId, @Param("userName") String userName,
			@Param("limit") int limit);

	/**
	 * 根据偏移量，用户名及测试案例ID获取提交.
	 *
	 * @param --案例ID
	 * @param --用户名
	 * @param --偏移量
	 * @param --每次查询数量
	 * @return 查询结果集
	 */
	List<Submission> getSubmissionsByOffset(@Param("testcaseId") long testcaseId, @Param("userName") String userName,
			@Param("offset") long offset, @Param("limit") int limit);

	/**
	 * 根据偏移量，用户名及测试案例ID获取最新提交.
	 *
	 * @param --案例ID
	 * @param --用户名
	 * @param --偏移量
	 * @param --每次查询数量
	 * @return 查询结果集
	 */
	List<Submission> getLatestSubmissionsByOffset(@Param("testcaseId") long testcaseId,
			@Param("userName") String userName, @Param("offset") long offset, @Param("limit") int limit);

	long getNumberOfSubmissions(@Param("startTime") String startTime, @Param("endTime") String endTime);

	long getLatestSubmissionId();
}