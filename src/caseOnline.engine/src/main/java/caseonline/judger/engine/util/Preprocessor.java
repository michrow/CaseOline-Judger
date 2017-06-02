package caseonline.judger.engine.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import caseonline.judger.engine.exception.CreateDirectoryException;
import caseonline.judger.engine.mapper.SubmissionMapper;
import caseonline.judger.engine.model.Submission;

/**
 * 执行构建前的准备工作，创建临时文件
 */
@Component
public class Preprocessor {

	public String createTestcaseFile(String code,String codeFileDirectory,String fileName) throws Exception{
		File workDirFile = new File(codeFileDirectory);
		if (!workDirFile.exists() && !workDirFile.mkdirs()) {
			throw new CreateDirectoryException("Failed to create directory: " + codeFileDirectory);
		}
		String codeFilePath=codeFileDirectory+fileName;
		FileOutputStream outputStream = new FileOutputStream(new File(codeFilePath));
		IOUtils.write(code, outputStream);
		IOUtils.closeQuietly(outputStream);
		return codeFilePath;
	}
	
	public String createUsecaseFile(Submission submission,String useFileDirectory,String className,String template,String suffx) throws Exception{
		File workDirFile = new File(useFileDirectory);
		if (!workDirFile.exists() && !workDirFile.mkdirs()) {
			throw new CreateDirectoryException("Failed to create directory: " + useFileDirectory);
		}
		Object bean = null;
		bean = GetBean(submission, className,suffx);
		String code = UsecaseGenerator.getUsecaseCode(template, bean);
		String fileName="";
		if (submission.getLanguage().getLanguageName().equalsIgnoreCase("java")) {
			fileName=className;
		}else{
			fileName="Test"+DigestUtils.getRandomString(12, DigestUtils.Mode.ALPHA);
		}
		String usecaseFilePath=String.format("%s%s.%s", useFileDirectory,fileName,suffx);
		FileOutputStream outputStream = new FileOutputStream(new File(usecaseFilePath));
		IOUtils.write(code, outputStream);
		IOUtils.closeQuietly(outputStream);
		return usecaseFilePath;
	}	
	
	private Object GetBean(Submission submission, String className, String suffx) throws Exception{
		String usecases = submission.getUsecase();
		List<List<Object>> use = new ArrayList<List<Object>>();
		String[] usecase = usecases.split("\n");
		//设置测试用例数量
		submission.setUsecaseAmount(usecase.length);
		submissionMapper.updateSubmission(submission);
		Bean bean = new Bean();
		String jsonParam = submission.getTestcase().getTestcaseParamAttr();
		JSONObject param = (JSONObject) JSON.parse(jsonParam);
		// System.out.println(param);
		String  testClassName=param.getString("className");
		JSONArray methods = param.getJSONArray("method");
		JSONObject jsonObject = (JSONObject) methods.get(0);
		String methodName = (String) jsonObject.get("methodname");

		JSONArray jparamInfo = jsonObject.getJSONArray("paramInfo");
		// 参数类型
		List<Object> lparamTypeInfo = jparamInfo.subList(0, jparamInfo.size());
		// 返回值类型
		Object returnTypeName = jsonObject.get("returnTypeName");
		try {
			for (int j = 0; j < usecase.length; j++) {
				List<Object> values = new ArrayList<Object>();
				String[] mates = usecase[j].split(",");
				Object key = getNewValue(mates[0], returnTypeName);
				values.add(key);
				for (int i = 1; i < mates.length; i++) {
					Object paramType = lparamTypeInfo.get(i - 1);
					values.add(getNewValue(mates[i], paramType));
				}
				use.add(values);
			}
		} catch (Exception e) {
			throw new RuntimeException("测试用例编写有误");
		}
		bean.setAuthor("wxh");
		bean.setClassName(className);
		bean.setCreateTime(new Date().toString());
		List<String> heads = new ArrayList<String>();
		if (suffx.equalsIgnoreCase("java")) {
			heads.add("static org.junit.Assert.*;");
			heads.add("org.junit.Test;");			
		}else if (suffx.equalsIgnoreCase("py")) {
			heads.add("from src.MaxMath import *");
			heads.add("from nose.tools import assert_equal");
		}
		
		bean.setHeads(heads);
		bean.setTestClassName(testClassName);
		List<Param> paramInfos = new ArrayList<Param>();
		for (List<Object> list : use) {
			Param paramInfo = new Param();
			paramInfo.setExpectValue(list.get(0));
			paramInfo.setParams(list.subList(1, list.size()));
			paramInfo.setMethodName(methodName);
			paramInfos.add(paramInfo);
		}
		bean.setParamInfo(paramInfos);
		return bean;
	}

	/**
	 * 创建测试案例文件
	 *
	 * @param submission
	 *            the submission
	 * @param workDirectory
	 *            the work directory
	 * @return 文件路径
	 * @throws Exception
	 *             the exception
	 */
	public String createTestCode(Submission submission, String workDirectory) throws Exception {
		File workDirFile = new File(workDirectory);
		if (!workDirFile.exists() && !workDirFile.mkdirs()) {
			throw new CreateDirectoryException("Failed to create directory: " + workDirectory);
		}
		String code = submission.getTestcase().getTestcaseCode();		
		String languageName=submission.getLanguage().getLanguageName();
		String codeFilePath="";
		if (languageName.equalsIgnoreCase("java")) {
			String className = code.substring(code.indexOf("class") + 5, code.indexOf("{")).trim();
			codeFilePath = String.format("%s/%s.%s", new Object[] { workDirectory, className, "java" });
		} else if (languageName.equalsIgnoreCase("python")) {
			String className = code.substring(code.indexOf("class") + 5, code.indexOf(":")).trim();
			codeFilePath = String.format("%s/%s.%s", new Object[] { workDirectory, className, "py" });
			String packageFile= String.format("%s/%s.%s", new Object[] { workDirectory, "__init__", "py" });
			FileOutputStream outputStream = new FileOutputStream(new File(packageFile));
			IOUtils.write("", outputStream);
		}		

		FileOutputStream outputStream = new FileOutputStream(new File(codeFilePath));
		IOUtils.write(code, outputStream);
		IOUtils.closeQuietly(outputStream);
		return codeFilePath;
	}

	/**
	 * 创建测试用例文件
	 *
	 * @param submission
	 *            the submission
	 * @param workDirectory
	 *            the work directory
	 * @param className
	 *            the class name
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String createUseCode(Submission submission, String workDirectory, String className) throws Exception {
		File workDirFile = new File(workDirectory);
		if (!workDirFile.exists() && !workDirFile.mkdirs()) {
			throw new CreateDirectoryException("Failed to create directory: " + workDirectory);
		}
		String testcaseCode = submission.getTestcase().getTestcaseCode();
		String languageName=submission.getLanguage().getLanguageName();
		String template="";
		String codeFilePath="";
		Object bean =null;
		if (languageName.equalsIgnoreCase("java")) {
			String testClassName = testcaseCode.substring(testcaseCode.indexOf("class") + 5, testcaseCode.indexOf("{"))
					.trim();
			// 获取生成代码的Bean
			bean = GetBean(submission, className, testClassName);
			template="javause.template";
			codeFilePath = String.format("%s/%s.%s", new Object[] { workDirectory, className + "Test", "java" });
		}else if (languageName.equalsIgnoreCase("python")) {
			String testClassName = testcaseCode.substring(testcaseCode.indexOf("class") + 5, testcaseCode.indexOf(":"))
					.trim();
			// 获取生成代码的Bean
			bean = GetBean(submission, className, testClassName);
			template="pythonuse.template";
			codeFilePath = String.format("%s/%s.%s", new Object[] { workDirectory,"Test"+className , "py" });
		}
		String code = UsecaseGenerator.getUsecaseCode(template, bean);
		FileOutputStream outputStream = new FileOutputStream(new File(codeFilePath));
		IOUtils.write(code, outputStream);
		IOUtils.closeQuietly(outputStream);
		return codeFilePath;
	}

	/**
	 * 类型转换
	 *
	 * @param oldValue
	 *            the old value
	 * @param returnTypeName
	 *            the return type name
	 * @return the new value
	 */
	private Object getNewValue(String oldValue, Object returnTypeName) {
		if (returnTypeName.equals("int")) {
			return Integer.valueOf(oldValue);
		}
		if (returnTypeName.equals("float")) {
			return Float.valueOf(oldValue);
		}
		return null;
	}
	
	public String loadStream(InputStream in) throws IOException {
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}
		return buffer.toString();
	}
	
	/** The submission mapper. */
	@Autowired
	private SubmissionMapper submissionMapper;

}
