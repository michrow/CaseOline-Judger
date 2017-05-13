package caseonline.judger.engine.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import caseonline.judger.engine.application.ApplicationDispatcher;
import caseonline.judger.engine.exception.IllgealSubmissionException;
import caseonline.judger.engine.mapper.SubmissionMapper;
import caseonline.judger.engine.model.Submission;
import caseonline.judger.engine.util.DigestUtils;
import caseonline.judger.engine.util.FileManager;
import caseonline.judger.engine.util.HtmlParse;
import caseonline.judger.engine.util.Preprocessor;
import caseonline.judger.engine.util.TestcaseParse;

/**
 * 评判调度器.
 *
 * @author 王肖辉 2017年3月19日
 */

@Component
public class Dispatcher {

	/**
	 * 创建一个新的任务.
	 *
	 * @param submissionId
	 *            the submission id
	 * @throws IllgealSubmissionException
	 *             the illgeal submission exception
	 */
	public void createNewTask(long submissionId) throws IllgealSubmissionException {
		synchronized (this) {
			String baseDirectory = String.format("%s/voj-%s", new Object[] { workBaseDirectory, submissionId });
			String baseFileName = DigestUtils.getRandomString(12, DigestUtils.Mode.ALPHA);

			Submission submission = submissionMapper.getSubmissionById(submissionId);
			if (submission == null) {
				throw new IllgealSubmissionException(
						String.format("Illegal submission #%s", new Object[] { submissionId }));
			}

			deleteAllTempFile();

			/**************** 创建文件 **********************/
			preprocess(submission, baseDirectory, baseFileName);

			/**************** 执行ant 命令 **********************/
			run(submission);

			/**************** 获取覆盖率html页面解析 **********************/
			getCoverage(submission);

			deleteAllTempFile();
		}
	}

	/**
	 * 解析覆盖率统计html文件.
	 *
	 * @param submission
	 *            the submission
	 */
	private void getCoverage(Submission submission) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccessful", false);
		String languageName=submission.getLanguage().getLanguageName();
		Map<String, List<String>> coverage=null;
		float useCoverage=0.0f;
		if (languageName.equalsIgnoreCase("java")) {
			// 获取测试案例类名
			String code = submission.getTestcase().getTestcaseCode();		
			String testName = code.substring(code.indexOf("class") + 5, code.indexOf("{")).trim();
			// 获取覆盖率
			coverage = HtmlParse.parseHtml(buildDirectory + "\\" + testName + ".html",languageName);
			String branches=coverage.get("Total").get(3);
			useCoverage=Float.valueOf((branches.substring(0, branches.length()-1)));
		
		}else if (languageName.equalsIgnoreCase("python")) {
			// 获取覆盖率
			coverage = HtmlParse.parseHtml(workBaseDirectory + "\\test\\cover\\" + "index.html",languageName);
			String branches=coverage.get("Total").get(5);
			useCoverage=Float.valueOf((branches.substring(0, branches.length()-1)));
		}
		Submission submission2=submissionMapper.getSubmissionById(submission.getSubmissionId());
		submission2.setUsecaseCoverage(useCoverage);
		
		submissionMapper.updateSubmission(submission2);
		
		
		JSONObject json = new JSONObject();
		if (coverage != null) {
			result.put("isSuccessful", true);
			json = json.fluentPutAll(coverage);
		}

		String coverageInfo = json.toJSONString();

		String log = "解析覆盖率结果:" + "\n" + coverageInfo;
		result.put("log", log);
		result.put("coverageInfo", coverageInfo);
		applicationDispatcher.onCoverageParseFinished(submission.getSubmissionId(), result);
	}

	/**
	 * 删除临时文件.
	 */
	private void deleteAllTempFile() {
		FileManager.clearFiles(workBaseDirectory + "\\src");
		FileManager.clearFiles(workBaseDirectory + "\\test");
		FileManager.clearFiles(workBaseDirectory + "\\build");
	}

	/**
	 * 执行ant 构建文件（完成编译，执行）.
	 *
	 * @param submission
	 *            the submission
	 */
	private void run(Submission submission) {
		Map<String, Object> result = new HashMap<String, Object>();
	
		String languageName=submission.getLanguage().getLanguageName();
		
		JSONObject json = TestcaseParse.runAnt(workBaseDirectory,languageName);
		result.put("isSuccessful", true);
		result.put("log", "构建结果:" + "\n" + json.getString("result") + "\n" + json.getString("error"));
		if (json.getString("result").isEmpty()) {
			result.put("isSuccessful", false);
		}
		applicationDispatcher.onRunBuildFinished(submission.getSubmissionId(), result);
	}

	/**
	 * 执行钱的准备工作（创建临时文件：案例文件和测试用例文件）.
	 *
	 * @param submission
	 *            the submission
	 * @param baseDirectory
	 *            --根目录目录
	 * @param className
	 *            --用例文件类名
	 */
	private void preprocess(Submission submission, String baseDirectory, String className) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String testCodePath = preprocessor.createTestCode(submission, workBaseDirectory + "\\src\\");
			String useCodePath = preprocessor.createUseCode(submission, workBaseDirectory + "\\test\\", className);
			result.put("isSuccessful", true);
			String log = String.format("创建文件:\n testCodePath:%s;\n useCodePath:%s",
					new Object[] { testCodePath, useCodePath });
			result.put("log", log);
			applicationDispatcher.onFileCreateFinished(submission.getSubmissionId(), result);

		} catch (Exception e) {
			e.printStackTrace();
			long submissionId = submission.getSubmissionId();
			result.put("isSuccessful", false);
			applicationDispatcher.onFileCreateFinished(submissionId, result);
		}
	}

	/** The application dispatcher. */
	@Autowired
	private ApplicationDispatcher applicationDispatcher;

	/** The submission mapper. */
	@Autowired
	private SubmissionMapper submissionMapper;

	/** The preprocessor. */
	@Autowired
	private Preprocessor preprocessor;

	/** The work base directory. */
	@Value("${judger.workDir}")
	private String workBaseDirectory;

	/** The build directory. */
	@Value("${judger.buildDir}")
	private String buildDirectory;
}
