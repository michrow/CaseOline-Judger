package caseonline.judger.engine.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import caseonline.judger.engine.mapper.SubmissionMapper;
import caseonline.judger.engine.model.Submission;
import caseonline.judger.engine.util.FileManager;
import caseonline.judger.engine.util.HtmlParse;
import caseonline.judger.engine.util.Preprocessor;


/**
 * Java语言的覆盖率解析实现.
 */
@Component
public class JavaCoverage implements ICoverage {

	/* (non-Javadoc)
	 * @see caseonline.judger.engine.core.ICoverage#preprocess(caseonline.judger.engine.model.Submission, java.lang.String)
	 */
	public Map<String, Object> preprocess(Submission submission, String baseDirectory) {
		Map<String, Object>result=new HashMap<String,Object>();
		result.put("IsCreateTestcase", true);
		result.put("IsCreateUsecase", true);
		result.put("log", "");
		result.put("isSuccessful",false);
		StringBuffer sBuffer=new StringBuffer();
		
		//获取测试案例类名
		String code=submission.getTestcase().getTestcaseCode();
		String jsonParam = submission.getTestcase().getTestcaseParamAttr();
		JSONObject param = (JSONObject) JSON.parse(jsonParam);
		String  className=param.getString("className");
		
		String codeFilePath = String.format("%s/%s/", new Object[] { baseDirectory,"src"});
		try {
			//创建测试案例文件
			String testCodeFilePath=preprocessor.createTestcaseFile(code, codeFilePath,className+".java");
			sBuffer.append("创建测试案例代码文件："+testCodeFilePath+"\n");
		} catch (Exception e) {
			result.put("IsCreateTestcase", false);
			LOGGER.error(e.getMessage());
		}		
		try {
			//创建测试用例文件
			String usecaseFilePath=preprocessor.createUsecaseFile(submission, baseDirectory+"/test/", "Test"+className, "javause.template", "java");
			sBuffer.append("创建测试用例代码文件："+usecaseFilePath);
		} catch (Exception e) {
			result.put("IsCreateUsecase", false);
			LOGGER.error(e.getMessage());
		}
		boolean tc=(Boolean) result.get("IsCreateTestcase");
		boolean uc=(Boolean) result.get("IsCreateUsecase");
		if (tc && uc) {
			result.put("isSuccessful",true);
		}
		result.put("log",sBuffer.toString());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see caseonline.judger.engine.core.ICoverage#runCoverage(caseonline.judger.engine.model.Submission, java.lang.String)
	 */
	public Map<String, Object> runCoverage(Submission submission, String baseDirectory) {
		Map<String, Object>result=new HashMap<String,Object>();
		result.put("log", "");
		result.put("isSuccessful", true);
		Runtime runtime = Runtime.getRuntime();
		try {
			//命令执行路径
			File file = FileManager.getFile(baseDirectory);
			String commad="cmd /c ant";
			Process process = runtime.exec(commad, null, file);
			String input = preprocessor.loadStream(process.getInputStream());
			String error = preprocessor.loadStream(process.getErrorStream());
			if (input.isEmpty()) {
				result.put("isSuccessful", false);
			}
			result.put("log", "构建结果:" + "\n" + input + "\n" + error);
		} catch (IOException e) {
			result.put("isSuccessful", false);
			LOGGER.error(e.getMessage());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see caseonline.judger.engine.core.ICoverage#parseCoverage(caseonline.judger.engine.model.Submission, java.lang.String)
	 */
	public Map<String, Object> parseCoverage(Submission submission, String resultDirectory) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccessful", false);
		Map<String, List<String>> coverage=null;
		float useCoverage=0.0f;		
		String jsonParam = submission.getTestcase().getTestcaseParamAttr();
		JSONObject param = (JSONObject) JSON.parse(jsonParam);
		String  testName=param.getString("className");
		// 解析覆盖率报告
		coverage = HtmlParse.parseHtml(resultDirectory+"/build/coverage/default/" + testName + ".html","java");
		
		//获取分支覆盖率
		String branches=coverage.get("Total").get(3);
		useCoverage=Float.valueOf((branches.substring(0, branches.length()-1)));
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
		result.put("useCoverage",useCoverage);
		return result;
	}
	
	
	/** The preprocessor. */
	@Autowired
	private Preprocessor preprocessor;
	
	/** The submission mapper. */
	@Autowired
	private SubmissionMapper submissionMapper;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(JavaCoverage.class);
}
