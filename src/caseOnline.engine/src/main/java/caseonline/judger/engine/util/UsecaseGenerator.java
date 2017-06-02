/*
 * 
 */
package caseonline.judger.engine.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

/**
 * 测试用例代码生成器
 */
public class UsecaseGenerator {

	/**
	 * Instantiates a new usecase generator.
	 */
	private UsecaseGenerator() {
	};

	public static void main(String[] args) {
		
		Bean bean=new Bean();
		bean.setClassName("testMath");
		List<String> heads=new ArrayList<String>();
		heads.add("from src.MaxMath import *");
		heads.add("from nose.tools import assert_equal");
		bean.setHeads(heads);
		bean.setTestClassName("MaxMath");
		List<Param> paramInfo=new ArrayList<Param>();
		
		Param param1=new Param();
		param1.setExpectValue(3);
		param1.setMethodName("max");
		List<Object> params=new ArrayList<Object>();
		params.add("3");
		params.add("2");
		params.add("1");
		param1.setParams(params);
		//param1.setTestClassName("self.maxmath");
		
		paramInfo.add(param1);
		
		bean.setParamInfo(paramInfo);
		
		try {
		String result=getUsecaseCode("pythonuse.template", bean);
		System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the usecase code.
	 *
	 * @param template
	 *            the template
	 * @param bean
	 *            the bean
	 * @return the usecase code
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getUsecaseCode(String template, Object bean) throws IOException {
		String root = System.getProperty("user.dir") + File.separator + "template";// 模板路径
		FileResourceLoader resourceLoader = new FileResourceLoader(root, "utf-8");
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		return getCodeForBean(gt, "/" + template, bean);
	}

	/**
	 * Gets the code for bean.
	 *
	 * @param gt
	 *            the gt
	 * @param FilePath
	 *            the file path
	 * @param bean
	 *            the bean
	 * @return the code for bean
	 */
	private static String getCodeForBean(GroupTemplate gt, String FilePath, Object bean) {
		Template t = gt.getTemplate(FilePath);
		t.binding("bean", bean);
		String str = t.render();
		return str;
	}
}
