package caseonline.judger.web.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestcaseParse {
	
	private TestcaseParse(){}
	
	public static String parseTestcase(long testcaseId, String basePath, String testcaseCode) {
		String fileName = createFile(testcaseCode, basePath, testcaseId);
		JSONObject compileResult = compile(basePath, testcaseId);
		File file = new File(basePath);
		String result = (String) compileResult.get("result");
		JSONObject runResult = null;
		if (!result.equals("fail")) {
			runResult = run(file, fileName);
		}
		String paramInfo = (String) runResult.get("result");
		return paramInfo;
	}

	private static String createFile(String code, String basePath, Long testcaseId) {
		String className = code.substring(code.indexOf("class") + 5, code.indexOf("{")).trim();
		String srcPath = basePath+ "\\" + className + ".java";
		File file = getFile(srcPath);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(code);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return className;
	}

	private static JSONObject compile(String basePath, long testcaseId) {
		Runtime runtime = Runtime.getRuntime();
		String compileCmd="javac " + basePath +"\\*.java";	
		
		try {
			Process process = runtime.exec(compileCmd);
			String result = loadStream(process.getInputStream());
			String error = loadStream(process.getErrorStream());
			if (!error.isEmpty()) {
				result = "fail";
			}
			String compileResult = "{" + "\"" + "result\"" + ":" + "\"" + result + "\"" + "," + "\"" + "error\"" + ":"
					+ "\"" + error + "\"" + "}";
			JSONObject json = (JSONObject) JSON.parse(compileResult);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static JSONObject run(File srcPath, String fileName) {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("java -jar Reflect.jar " + fileName, null, srcPath);
			String input = loadStream(process.getInputStream());
			String error = loadStream(process.getErrorStream());
			
			JSONObject jsonObject=new JSONObject();
			
			jsonObject.put("result", input);
			jsonObject.put("error", error);
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject runAnt(File srcPath, String fileName) {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("ant", null, srcPath);
			String input = loadStream(process.getInputStream());
			String error = loadStream(process.getErrorStream());
			
			JSONObject jsonObject=new JSONObject();
			
			jsonObject.put("result", input);
			jsonObject.put("error", error);
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	private static String loadStream(InputStream in) throws IOException {
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}
		return buffer.toString();
	}

	public static File getFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			System.out.println("创建单个文件" + path + "失败，目标文件已存在！");
		}
		if (path.endsWith(File.separator)) {
			System.out.println("创建单个文件" + path + "失败，目标文件不能为目录！");
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + path + "成功！");
			} else {
				System.out.println("创建单个文件" + path + "失败！");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + path + "失败！" + e.getMessage());
		}
		return file;
	}
}
