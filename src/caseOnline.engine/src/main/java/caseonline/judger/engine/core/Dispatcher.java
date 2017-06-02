package caseonline.judger.engine.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import caseonline.judger.engine.application.ApplicationDispatcher;
import caseonline.judger.engine.exception.IllgealSubmissionException;
import caseonline.judger.engine.mapper.ExcellenceUsecaseMapper;
import caseonline.judger.engine.mapper.SubmissionMapper;
import caseonline.judger.engine.model.ExcellenceUsecase;
import caseonline.judger.engine.model.Submission;
import caseonline.judger.engine.util.FileManager;

/**
 * 任务调度器执行.
 *
 * @author 王肖辉 2017年3月19日
 */

@Component
public class Dispatcher {

	/**
	 * 创建一个新的任务.
	 *
	 * @param submissionId
	 *            提交ID
	 * @throws IllgealSubmissionException
	 *             the illgeal submission exception
	 */
	public void createNewTask(long submissionId) throws IllgealSubmissionException {
		synchronized (this) {
			Submission submission = submissionMapper.getSubmissionById(submissionId);
			if (submission == null) {
				throw new IllgealSubmissionException(
				String.format("Illegal submission #%s", new Object[] { submissionId }));
			}
			//执行测评之前删除所有文件
			deleteAllTempFile();
			String languageName=submission.getTestcase().getLanguage().getLanguageName();
			if (languageName.equalsIgnoreCase("java")) {
				run(submission,Jcoverage);
			}else if (languageName.equalsIgnoreCase("python")) {
				run(submission,Pcoverage);
			}
			//执行之后删除所有创建文件
			deleteAllTempFile();
		}
	}

	
	/**
	 * 执行入口
	 *
	 * @param 提交任务
	 * @param 覆盖率分析实现类
	 */
	private void run(Submission submission,ICoverage coverageTest) {
		submission.setJudgerName(judgerName);
		submissionMapper.updateSubmission(submission);
		/*** 执行前的准备工作 ***/
		Map<String, Object>preResult=coverageTest.preprocess(submission, workBaseDirectory);
		applicationDispatcher.onFileCreateFinished(submission.getSubmissionId(), preResult);
		
		/*** 执行 ***/
		Map<String, Object>runResult=coverageTest.runCoverage(submission, workBaseDirectory);
		applicationDispatcher.onRunBuildFinished(submission.getSubmissionId(), runResult);
		
		/*** 执行之后的解析工作 ***/
		Map<String, Object>parseResult=coverageTest.parseCoverage(submission, workBaseDirectory);
		float coverage=(Float) parseResult.get("useCoverage");
		if (coverage>=Float.valueOf(coverageLine)) {
			//对达到覆盖标准的提交处理
			HandlerResult(submission);
		}
		parseResult.put("coverageInfo", coverage);
		applicationDispatcher.onCoverageParseFinished(submission.getSubmissionId(), parseResult);
	}

	/**
	 * 对符合覆盖率标准的提交进行处理
	 *
	 * @param submission the submission
	 */
	private void HandlerResult(Submission submission) {
//		Submission submission2=submissionMapper.getSubmissionById(submission.getSubmissionId());
//		ExcellenceUsecase usecase=new ExcellenceUsecase(submission2.getSubmissionId(), submission2.getUsecaseCoverage(),
//				submission2.getUsecaseAmount(), submission2.getUsecase(),submission2.getTestcase());
		ExcellenceUsecase usecase=new ExcellenceUsecase(submission.getSubmissionId(), submission.getUsecaseCoverage(),
		submission.getUsecaseAmount(), submission.getUsecase(),submission.getTestcase());
		excellenceUsecaseMapper.insertUsecase(usecase);
	}
	/**
	 * 删除临时文件.
	 */
	private void deleteAllTempFile() {
		FileManager.clearFiles(workBaseDirectory + "/src");
		FileManager.clearFiles(workBaseDirectory + "/test");
		FileManager.clearFiles(workBaseDirectory + "/build");
		FileManager.clearFiles(workBaseDirectory + "/cover");
	}

	/** The application dispatcher. */
	@Autowired
	private ApplicationDispatcher applicationDispatcher;

	/** The submission mapper. */
	@Autowired
	private SubmissionMapper submissionMapper;

	/** The excellence usecase mapper. */
	@Autowired	
	private ExcellenceUsecaseMapper excellenceUsecaseMapper;
	
	/** Python覆盖率分析实现 */
	@Autowired
	@Qualifier("pythonCoverage")
	private ICoverage Pcoverage;
	
	/** Java覆盖率分析实现 */
	@Autowired
	@Qualifier("javaCoverage")
	private ICoverage Jcoverage;
	
	/** 基础目录 */
	@Value("${judger.workDir}")
	private String workBaseDirectory;

	/** 分析报告生成目录 */
	@Value("${judger.buildDir}")
	private String buildDirectory;
	
	/** The coverage line. */
	@Value("${coverageLine}")
	private String coverageLine;	
	
	/** 测评机名称. */
	@Value("${judger.username}")
	private String judgerName;
	
}
