package caseonline.judger.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import caseonline.judger.web.exception.ResourceNotFoundException;
import caseonline.judger.web.mapper.LanguageMapper;
import caseonline.judger.web.mapper.UserMapper;
import caseonline.judger.web.model.Language;
import caseonline.judger.web.model.Testcase;
import caseonline.judger.web.model.TestcaseCategory;
import caseonline.judger.web.model.User;
import caseonline.judger.web.service.TestcaseCategoryService;
import caseonline.judger.web.service.TestcaseService;
import caseonline.judger.web.service.UserService;
@Controller
@RequestMapping(value = "/admin/testcase")
public class TestcaseManagerController {
	/**
	 * 显示所有测试案例页面
	 *
	 * @param keyword
	 *            --关键字
	 * @param categoryType
	 *            --案例类型
	 * @param pageNumber
	 *            --页码
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/all-testcases", method = RequestMethod.GET)
	public ModelAndView allTestcasesView(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "categoryType", required = false, defaultValue = "") String categoryType,
			@RequestParam(value = "userName",required=false,defaultValue="") String userName,
			@RequestParam(value = "page", required = false, defaultValue = "1") long pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		long userId = (Long) session.getAttribute("uid");
		User user=userMapper.getUserById(userId);
		if (user.getRole().getRoleType().equals("admin")) {
			userId=0;
		}
		//管理员按案例提交者查询
		if (!userName.isEmpty()) {
			userId=userMapper.getUserByName(userName).getUserId();
		}
		List<TestcaseCategory> categories = categoryService.getTestcaseCategories();
		long totalTestcase = testcaseService.getTestcasesNumberByCondition(keyword, categoryType, true,userId);
		long offset = (pageNumber >= 1 ? pageNumber - 1 : 0) * NUMBER_OF_TESTCASES_PER_PAGE;
		long testcaseIdLowerBound = testcaseService.getFirstIndexOfTestcase() + offset;
		long testcaseIdUpperBound = testcaseService.getUpperBoundWithLimit(testcaseIdLowerBound,
				NUMBER_OF_TESTCASES_PER_PAGE);
		List<Testcase> testcases = testcaseService.getTestcasesByCondition(testcaseIdLowerBound, keyword, categoryType,userId,
				true, NUMBER_OF_TESTCASES_PER_PAGE);
		//获取某一范围内测试案例的类型
		Map<Long, List<TestcaseCategory>> testcaseRelas = categoryService.getTestcastOfCategory(testcaseIdLowerBound,
				testcaseIdUpperBound);
	
		ModelAndView view = new ModelAndView("administration/all-testcases");
		view.addObject("categories", categories);
		view.addObject("selectedCategory", categoryType);
		view.addObject("userName",userName);
		view.addObject("keyword", keyword);
		view.addObject("currentPage", pageNumber);
		view.addObject("testcaseRelas", testcaseRelas);
		view.addObject("totalPages", (long) Math.ceil(totalTestcase * 1.0 / NUMBER_OF_TESTCASES_PER_PAGE));
		view.addObject("testcases", testcases);
		return view;
	}

	/**
	 * 删除
	 *
	 * @param testcases
	 *            the testcases
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/deleteTestcases.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> deleteTestcasesAction(@RequestParam(value = "testcases") String testcases,
			HttpServletRequest request) {
		Map<String, Boolean> result = new HashMap<String, Boolean>(2, 1);
		List<Long> testcaseIds = JSON.parseArray(testcases, Long.class);
		testcaseService.deleteBatchTestcase(testcaseIds);
		result.put("isSuccessful", true);
		return result;
	}

	/**
	 * 显示新增页面
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/new-testcase", method = RequestMethod.GET)
	public ModelAndView newTestcaseView(HttpServletRequest request, HttpServletResponse response) {
		List<TestcaseCategory> categories = categoryService.getTestcaseCategories();
		List<Language> languages = testcaseService.getAllLanguages();
		ModelAndView view = new ModelAndView("administration/new-testcase");
		view.addObject("categories", categories);
		view.addObject("languages", languages);
		return view;
	}

	/**
	 * 新建测试案例
	 * 
	 * @param testcaseName
	 *            the testcase name
	 * @param testcaseDescription
	 *            the testcase description
	 * @param testcaseCode
	 *            the testcase code
	 * @param categoryId
	 *            the category id
	 * @param isPublic
	 *            the is public
	 * @param languageId
	 *            the language id
	 * @param request
	 *            the request
	 * @return the map
	 */

	@RequestMapping(value = "/createTestcase.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createTestcaseAction(
			@RequestParam(value = "testcaseName") String testcaseName,
			@RequestParam(value = "testcaseDescription") String testcaseDescription,
			@RequestParam(value = "testcaseCode") String testcaseCode,
			@RequestParam(value = "categoryId") int categoryId, @RequestParam(value = "isPublic") boolean isPublic,
			@RequestParam(value = "languageId") int languageId,
			@RequestParam(value="className") String className,
			@RequestParam(value="methodName") String methodName,
			@RequestParam(value="returnType") String returnType,
			@RequestParam(value="paramType") String paramType,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		long userId = (Long) session.getAttribute("uid");
		User user = userService.getUserById(userId);
		
		Map<String, Object> result = testcaseService.createTestcase(testcaseName, testcaseCode, isPublic,
				testcaseDescription, languageId, categoryId,user);
		
		String languageName=languageMapper.getLanguageById(languageId).getLanguageName();
		
		/*
		 * 非Java语言测试案例代码参数信息需
		 * 要提交者输入，然后构建成Json字符串形式的参数
		 */
		if (!languageName.equalsIgnoreCase("java")) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("className", className);
			
			JSONObject methodJson=new JSONObject();
			methodJson.put("methodname", methodName);
			methodJson.put("returnTypeName", returnType);
			String [] paramInfo=paramType.split(",");
			methodJson.put("paramInfo",paramInfo);
			
			JSONArray jsonArray=new JSONArray();
			jsonArray.add(methodJson);
			
			jsonObject.put("method", jsonArray);
			
			Testcase testcase=(Testcase) result.get("testcase");
			testcase.setTestcaseParamAttr(jsonObject.toJSONString());
			testcaseService.updateTestcase(testcase);
		}		
	
		return result;
	}

	/**
	 * 显示编辑
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/edit-testcase/{testcaseId}", method = RequestMethod.GET)
	public ModelAndView editTestcaseView(@PathVariable(value = "testcaseId") long testcaseId,
			HttpServletRequest request, HttpServletResponse response) {
		Testcase testcase = testcaseService.getTestcaseById(testcaseId);
		if (testcase == null) {
			throw new ResourceNotFoundException();
		}
		List<TestcaseCategory> testcaseCategories = categoryService.getTestcaseCategories();
		ModelAndView view = new ModelAndView("administration/edit-testcase");
		view.addObject("testcase", testcase);
		view.addObject("testcaseCategories", testcaseCategories);
		return view;
	}

	/**
	 * 编辑
	 *
	 * @param testcaseId
	 *            测试案例ID
	 * @param testcaseName
	 *            测试案例名称
	 * @param testcaseCode
	 *            测试案例代码
	 * @param testcaseDesc
	 *            测试案例描述
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/editTestcase.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> editTestcaseAction(@RequestParam(value = "testcaseId") long testcaseId,
			@RequestParam(value = "testcaseName") String testcaseName,
			@RequestParam(value = "testcaseCode") String testcaseCode,
			@RequestParam(value = "testcaseDesc") String testcaseDesc, HttpServletRequest request) {

		Testcase testcase = testcaseService.getTestcaseById(testcaseId);
		testcase.setTestcaseName(testcaseName);
		testcase.setTestcaseCode(testcaseCode);
		testcase.setTestcaseDescription(testcaseDesc);

		int editResult = testcaseService.editTestcase(testcase);
		Map<String, Integer> result = new HashMap<>();
		result.put("isSuccessful", editResult);
		return result;
	}

	/**
	 * 显示分类
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/testcase-categories", method = RequestMethod.GET)
	public ModelAndView categoryView(HttpServletRequest request, HttpServletResponse response) {
		List<TestcaseCategory> categories = categoryService.getTestcaseCategories();
		ModelAndView view = new ModelAndView("administration/testcase-categories");
		view.addObject("categories", categories);
		return view;
	}

	/**
	 * 创建新的类别
	 *
	 * @param categoryType
	 *            类型
	 * @param categoryName
	 *            类型名称
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/createCategory.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createTestcaseCategoryAction(
			@RequestParam(value = "categoryType") String categoryType,
			@RequestParam(value = "categoryName") String categoryName, HttpServletRequest request) {
		TestcaseCategory category = new TestcaseCategory(categoryType, categoryName, 0);
		int createResult = categoryService.addCategory(category);
		Map<String, Object> result = new HashMap<>();
		result.put("isSuccessful", createResult);
		return result;
	}

	/**
	 * Delete Testcase category action.
	 *
	 * @param categories
	 *            the categories
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/deleteCategories.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteTestcaseCategoryAction(
			@RequestParam(value = "categories") String categories, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(3, 1);
		List<Integer> categoryIds = JSON.parseArray(categories, Integer.class);
		categoryService.deleteBatch(categoryIds);
		result.put("isSuccessful", true);
		return result;
	}
	
	/** The number of testcases per page. */
	public final int NUMBER_OF_TESTCASES_PER_PAGE = 2;
	
	/** The testcase service. */
	@Autowired
	TestcaseService testcaseService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserService userService;
	
	@Autowired
	LanguageMapper languageMapper;
	/** The category service. */
	@Autowired
	TestcaseCategoryService categoryService;	
}
