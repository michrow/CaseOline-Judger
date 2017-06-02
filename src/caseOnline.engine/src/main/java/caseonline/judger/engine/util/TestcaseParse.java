package caseonline.judger.engine.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 测试案例解析，获取测试案例类的信息
 */
public class TestcaseParse {

	/**
	 * Instantiates a new testcase parse.
	 */
	private TestcaseParse() {
	}

	/**
	 * Parses the testcase.
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param basePath
	 *            the base path
	 * @param testcaseCode
	 *            the testcase code
	 * @return the string
	 */
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

	/**
	 * Creates the file.
	 *
	 * @param code
	 *            the code
	 * @param basePath
	 *            the base path
	 * @param testcaseId
	 *            the testcase id
	 * @return the string
	 */
	private static String createFile(String code, String basePath, Long testcaseId) {
		String className = code.substring(code.indexOf("class") + 5, code.indexOf("{")).trim();
		String srcPath = basePath + "\\" + className + ".java";
		File file = FileManager.getFile(srcPath);
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

	/**
	 * Compile.
	 *
	 * @param basePath
	 *            the base path
	 * @param testcaseId
	 *            the testcase id
	 * @return the JSON object
	 */
	private static JSONObject compile(String basePath, long testcaseId) {
		Runtime runtime = Runtime.getRuntime();
		String compileCmd = "javac " + basePath + "\\*.java";

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

	/**
	 * Run.
	 *
	 * @param srcPath
	 *            the src path
	 * @param fileName
	 *            the file name
	 * @return the JSON object
	 */
	private static JSONObject run(File srcPath, String fileName) {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("java -jar Reflect.jar " + fileName, null, srcPath);
			String input = loadStream(process.getInputStream());
			String error = loadStream(process.getErrorStream());

			JSONObject jsonObject = new JSONObject();

			jsonObject.put("result", input);
			jsonObject.put("error", error);
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Run ant.
	 *
	 * @param workBaseDirectory
	 *            the src path
	 * @param languageName 
	 * @return the JSON object
	 */
	public static JSONObject runAnt(String workBaseDirectory, String languageName) {
		
		Runtime runtime = Runtime.getRuntime();
		try {
			String commad="";
			String input ="";
			String error="";
			if (languageName.equalsIgnoreCase("java")) {
				File file = FileManager.getFile(workBaseDirectory);
				commad="cmd /c ant";
				Process process = runtime.exec(commad, null, file);
				input = loadStream(process.getInputStream());
				error = loadStream(process.getErrorStream());
			}else if (languageName.equalsIgnoreCase("python")) {
				File file = FileManager.getFile(workBaseDirectory+"\\test");
				commad="cmd /c nosetests -v --with-coverage --cover-erase --cover-branches --cover-html";
				Process process = runtime.exec(commad, null, file);
				//执行此命令error 的结果为真正想要的输出，原因不详？？？
				error = loadStream(process.getInputStream());
				input = loadStream(process.getErrorStream());
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", input);
			jsonObject.put("error", error);
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Load stream.
	 *
	 * @param in
	 *            the in
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static String loadStream(InputStream in) throws IOException {
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}
		return buffer.toString();
	}
}
