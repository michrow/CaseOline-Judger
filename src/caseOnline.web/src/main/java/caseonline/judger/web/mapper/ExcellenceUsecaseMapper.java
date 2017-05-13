package caseonline.judger.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import caseonline.judger.web.model.ExcellenceUsecase;

/**
 * 优秀测试用例Mappper接口.
 */
public interface ExcellenceUsecaseMapper {

	/**
	 * 删除.
	 *
	 * @param --提交ID
	 * @return the int
	 */
	int deleteUsecase(Long submissionId);

	/**
	 * 插入.
	 *
	 * @param --优秀的测试用例提交
	 * @return the int
	 */
	int insertUsecase(ExcellenceUsecase usecase);

	/**
	 * 按条件筛选.
	 *
	 * @param submissionId
	 *            --提交ID
	 * @param testcaseId
	 *            --测试案例ID
	 * @param usecaseCoverage
	 *            --覆盖率（大于等于）
	 * @return --优秀测试用例提交筛选结果集合
	 */
	List<ExcellenceUsecase> selectUsecaseByConditon(@Param("submissionId") Long submissionId,
			@Param("testcaseId") Long testcaseId, @Param("usecaseCoverage") float usecaseCoverage);
}