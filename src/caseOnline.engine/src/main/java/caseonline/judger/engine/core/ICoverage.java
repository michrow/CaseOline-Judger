package caseonline.judger.engine.core;

import java.util.Map;

import caseonline.judger.engine.model.Submission;

/**
 * 覆盖率分析实现接口
 */
public interface ICoverage {
	
	/**
	 * 准备
	 *
	 * @param 提交
	 * @param 基础路径
	 * @return 准备结果
	 */
	public Map<String, Object> preprocess(Submission submission,String baseDirectory);
	
	/**
	 * 执行
	 *
	 * @param 提交
	 * @param 基础路径
	 * @return 执行结果
	 */
	public Map<String, Object> runCoverage(Submission submission,String baseDirectory);
	
	/**
	 * 解析
	 *
	 * @param 解析
	 * @param 报告生成路径
	 * @return 解析结果
	 */
	public Map<String, Object> parseCoverage(Submission submission,String resultDirectory);
}
