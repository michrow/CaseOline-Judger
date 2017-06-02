package caseonline.judger.web.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.web.model.JudgeResult;

/**
 * 测评结果Mapper接口.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface JudgeResultMapper {

	/**
	 * 根据ID获取测评结果.
	 *
	 * @param resultId
	 *            the result id
	 * @return the judge result by id
	 */
	JudgeResult getJudgeResultById(@Param("resultId") int resultId);

	/**
	 * 根据标志获取测评结果.
	 *
	 * @param resultFlag
	 *            the result flag
	 * @return the judge result by flag
	 */
	JudgeResult getJudgeResultByFlag(@Param("resultFlag") String resultFlag);
}
