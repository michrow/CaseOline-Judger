package caseonline.judger.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import caseonline.judger.web.mapper.TestcaseMapper;
import caseonline.judger.web.mapper.UserMapper;
import caseonline.judger.web.model.Testcase;
import caseonline.judger.web.model.User;
import caseonline.judger.web.service.SubmissionService;
import caseonline.judger.web.service.TestcaseCategoryService;
import caseonline.judger.web.service.TestcaseService;
import caseonline.judger.web.service.UserService;

/**
 * 案例处理
 */
@Controller
@RequestMapping(value = "/testcase")
public class TestcaseController {

	/**
	 * 显示案例
	 *
	 * @param startIndex
	 *            开始页
	 * @param keyword
	 *            案例名
	 * @param categoryType
	 *            类型
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView testcaseView(
			@RequestParam(value = "start", required = false, defaultValue = "1") long startIndex,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "category", required = false) String categoryType, HttpServletRequest request,
			HttpServletResponse response) {
		long startIndexOfTestcase = getFirstIndexOfTestcase();
		if (startIndex < startIndexOfTestcase) {
			startIndex = startIndexOfTestcase;
		}
		List<Testcase> testcases = testcaseService.getTestcasesByCondition(startIndex, keyword, categoryType, 0, true,
				NUMBER_OF_TESTCASES_PER_PAGE);
		long totalTestcases = testcaseService.getTestcasesNumberByCondition(keyword, categoryType, true, 0);
		
		ModelAndView view = new ModelAndView("testcases/testcases");
		view.addObject("testcases", testcases).addObject("startIndexOfTestcase", startIndexOfTestcase)
				.addObject("numberOfTestcasesPerPage", NUMBER_OF_TESTCASES_PER_PAGE)
				.addObject("totalTestcases", totalTestcases).addObject("keyword", keyword)
				.addObject("testcaseCategories", testcaseCategoryService.getTestcaseCategories())
				.addObject("selectedCategoryType", categoryType);
		return view;
	}

	/**
	 * 按条件查询
	 *
	 * @param startIndex
	 *            the start index
	 * @param keyword
	 *            the keyword
	 * @param categoryType
	 *            the category type
	 * @param request
	 *            the request
	 * @return the testcase action
	 */
	@RequestMapping(value = "/getTestcase.action", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getTestcaseAction(@RequestParam(value = "startIndex") long startIndex,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "category", required = false) String categoryType,
			HttpServletRequest request) {
		List<Testcase> testcases = testcaseService.getTestcasesByCondition(startIndex, keyword, categoryType, 0, true,
				NUMBER_OF_TESTCASES_PER_PAGE);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccessful", testcases != null && !testcases.isEmpty());
		result.put("testcases", testcases);
		return result;
	}

	/**
	 * 详细信息
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/{testcaseId}", method = RequestMethod.GET)
	public ModelAndView TestcaseView(@PathVariable("testcaseId") long testcaseId, HttpServletRequest request,
			HttpServletResponse response) {

		Testcase testcase = testcaseMapper.getTestcaseById(testcaseId);
		if (testcase == null) {
			throw new RuntimeException("资源没发现");
		}
		ModelAndView view = new ModelAndView("testcases/testcase");
		view.addObject("testcase", testcase);
		return view;
	}

	/**
	 * 处理提交请求
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param languageId
	 *            the language id
	 * @param usecase
	 *            the usecase
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/createSubmission.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createSubmissionAction(@RequestParam(value = "testcaseId") long testcaseId,
			@RequestParam(value = "languageId") int languageId, @RequestParam(value = "usecase") String usecase,
			HttpServletRequest request) {

		//获取当前用户
		HttpSession session = request.getSession();
		long userId = (Long) session.getAttribute("uid");
		User user = userService.getUserById(userId);

		Map<String, Object> result = submissionService.addSubmission(user, testcaseId, languageId, usecase);
		if ((boolean) result.get("isSuccessful")) {
			long submissionId = (Long) result.get("submissionId");
			LOGGER.info(String.format("User: {%s} submitted code with SubmissionId #%s",
					new Object[] { user, submissionId }));
		}
		return result;
	}

	/**
	 * 获取最小的测试案例ID
	 *
	 * @return the first index of testcase
	 */
	private long getFirstIndexOfTestcase() {
		return testcaseService.getFirstIndexOfTestcase();
	}

	/**
	 * 每次请求所加载试题数量.
	 */
	private static final int NUMBER_OF_TESTCASES_PER_PAGE = 2;

	/** The testcase mapper. */
	@Autowired
	TestcaseMapper testcaseMapper;
	@Autowired
	UserMapper userMapper;

	/** The testcase service. */
	@Autowired
	TestcaseService testcaseService;

	/** The testcase category service. */
	@Autowired
	TestcaseCategoryService testcaseCategoryService;

	/** The user service. */
	@Autowired
	UserService userService;

	/** The submission service. */
	@Autowired
	SubmissionService submissionService;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(TestcaseController.class);
}
