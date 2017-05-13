package caseonline.judger.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.fastjson.JSON;

import caseonline.judger.web.exception.ResourceNotFoundException;
import caseonline.judger.web.messenger.ApplicationEventListener;
import caseonline.judger.web.model.Language;
import caseonline.judger.web.model.Submission;
import caseonline.judger.web.model.Testcase;
import caseonline.judger.web.model.TestcaseCategory;
import caseonline.judger.web.model.User;
import caseonline.judger.web.model.UserRole;
import caseonline.judger.web.service.GeneralService;
import caseonline.judger.web.service.LanguageService;
import caseonline.judger.web.service.SubmissionService;
import caseonline.judger.web.service.TestcaseCategoryService;
import caseonline.judger.web.service.TestcaseService;
import caseonline.judger.web.service.UserService;
import caseonline.judger.web.util.SessionListener;

/**
 * 系统管理.
 *
 * @author 王肖辉 2017年4月20日
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	/**
	 * 显示主页面
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("administration/index");
		view.addObject("totalUsers", getTotalUsers());
		view.addObject("newUsersToday", getNumberOfUserRegisteredToday());
		view.addObject("onlineUsers", getOnlineUsers());
		view.addObject("totalTestcases", getTotalTestcase());
		view.addObject("totalPublicTestcases", getPublicTotalTestcase());
		view.addObject("submissionsToday", getSubmissionToday());
		view.addObject("memoryUsage", getCurrentMemoryUsage());
		view.addObject("onlineJudgers", getOnlineJudgers());
		return view;
	}

	/**
	 * 显示所有用户
	 *
	 * @param roleType
	 *            --角色类型
	 * @param username
	 *            --用户名
	 * @param pageNumber
	 *            --页码
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/all-users", method = RequestMethod.GET)
	public ModelAndView allUsersView(
			@RequestParam(value = "roleType", required = false, defaultValue = "") String roleType,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "page", required = false, defaultValue = "1") long pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		List<UserRole> roles = userService.getAllRole();
		UserRole role = userService.getUserRoleByType(roleType);
		long totalUsers = userService.getUsersTotalByCondition(role, username);
		long offset = (pageNumber >= 1 ? pageNumber - 1 : 0) * NUMBER_OF_USERS_PER_PAGE;
		List<User> users = userService.getUsersByCondition(role, username, offset, NUMBER_OF_USERS_PER_PAGE);

		ModelAndView view = new ModelAndView("administration/all-users");
		view.addObject("userRoles", roles);
		view.addObject("selectedRole", role);
		view.addObject("username", username);
		view.addObject("currentPage", pageNumber);
		view.addObject("totalPages", (long) Math.ceil(totalUsers * 1.0 / NUMBER_OF_USERS_PER_PAGE));
		view.addObject("users", users);
		return view;
	}

	/**
	 * 处理删除用户请求
	 *
	 * @param users
	 *            the users
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/deleteUsers.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> deleteUsersAction(@RequestParam(value = "users") String users,
			HttpServletRequest request) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		List<Long> userList = JSON.parseArray(users, Long.class);
		userService.deleteBatchUser(userList);

		result.put("isSuccessful", true);
		return result;
	}

	/**
	 * 显示编辑用户信息页面
	 *
	 * @param userId
	 *            the user id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/edit-user/{userId}", method = RequestMethod.GET)
	public ModelAndView editUserView(@PathVariable(value = "userId") long userId, HttpServletRequest request,
			HttpServletResponse response) {
		User user = userService.getUserById(userId);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		List<UserRole> roles = userService.getAllRole();
		ModelAndView view = new ModelAndView("administration/edit-user");
		view.addObject("user", user);
		view.addObject("userRoles", roles);
		return view;
	}

	/**
	 * 处理编辑用户信息请求
	 *
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param roleType
	 *            the role type
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/editUser.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> editUserAction(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "password") String password, @RequestParam(value = "email") String email,
			@RequestParam(value = "userRole") String roleType, HttpServletRequest request) {
		User user = userService.getUserById(userId);
		Map<String, Boolean> result = userService.updateProfile(user, password, email, roleType);
		return result;
	}

	/**
	 * 显示新建用户页面
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/new-user", method = RequestMethod.GET)
	public ModelAndView newUserView(HttpServletRequest request, HttpServletResponse response) {
		List<UserRole> roles = userService.getAllRole();
		ModelAndView view = new ModelAndView("administration/new-user");
		view.addObject("userRoles", roles);
		return view;
	}

	/**
	 * 处理新建用户请求
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param roleType
	 *            the role type
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/newUser.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> newUserAction(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "email") String email,
			@RequestParam(value = "userRole") String roleType, HttpServletRequest request) {
		UserRole role = userService.getUserRoleByType(roleType);
		Map<String, Boolean> result = userService.addUser(username, password, email, role);

		if (result.get("isSuccessful")) {
			LOGGER.info(String.format("User: [Username=%s] was created by admin.", new Object[] { username }));
		}
		return result;
	}

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
	public ModelAndView allTestcases(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "categoryType", required = false, defaultValue = "") String categoryType,
			@RequestParam(value = "page", required = false, defaultValue = "1") long pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		List<TestcaseCategory> categories = categoryService.getTestcaseCategories();
		long totalTestcase = testcaseService.getTestcasesNumberByCondition(keyword, categoryType, true);
		long offset = (pageNumber >= 1 ? pageNumber - 1 : 0) * NUMBER_OF_TESTCASES_PER_PAGE;
		long testcaseIdLowerBound = testcaseService.getFirstIndexOfTestcase() + offset;
		// long testcaseIdUpperBound = testcaseIdLowerBound +
		// NUMBER_OF_TESTCASES_PER_PAGE - 1;
		long testcaseIdUpperBound = testcaseService.getUpperBoundWithLimit(testcaseIdLowerBound,
				NUMBER_OF_TESTCASES_PER_PAGE);
		List<Testcase> testcases = testcaseService.getTestcasesByCondition(testcaseIdLowerBound, keyword, categoryType,
				true, NUMBER_OF_TESTCASES_PER_PAGE);
		Map<Long, List<TestcaseCategory>> testcaseRelas = categoryService.getTestcastOfCategory(testcaseIdLowerBound,
				testcaseIdUpperBound);
		ModelAndView view = new ModelAndView("administration/all-testcases");
		view.addObject("categories", categories);
		view.addObject("selectedCategory", categoryType);
		view.addObject("keyword", keyword);
		view.addObject("currentPage", pageNumber);
		view.addObject("testcaseRelas", testcaseRelas);
		view.addObject("totalPages", (long) Math.ceil(totalTestcase * 1.0 / NUMBER_OF_TESTCASES_PER_PAGE));
		view.addObject("testcases", testcases);
		return view;
	}

	/**
	 * Delete testcases action.
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
	 * New Testcase view.
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
	public @ResponseBody Map<String, Boolean> createTestcaseAction(
			@RequestParam(value = "testcaseName") String testcaseName,
			@RequestParam(value = "testcaseDescription") String testcaseDescription,
			@RequestParam(value = "testcaseCode") String testcaseCode,
			@RequestParam(value = "categoryId") int categoryId, @RequestParam(value = "isPublic") boolean isPublic,
			@RequestParam(value = "languageId") int languageId, HttpServletRequest request) {
		Map<String, Boolean> result = testcaseService.createTestcase(testcaseName, testcaseCode, isPublic,
				testcaseDescription, languageId, categoryId);
		return result;
	}

	/**
	 * Edits the Testcase .
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
	 * Edits the Testcase action.
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param testcaseName
	 *            the testcase name
	 * @param testcaseCode
	 *            the testcase code
	 * @param testcaseDesc
	 *            the testcase desc
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
	 * Category view.
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
	 * Creates the Testcase category action.
	 *
	 * @param categoryType
	 *            the category type
	 * @param categoryName
	 *            the category name
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

	/**
	 * All submissions view.
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param username
	 *            the username
	 * @param pageNumber
	 *            the page number
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/all-submissions", method = RequestMethod.GET)
	public ModelAndView allSubmissionsView(
			@RequestParam(value = "testcaseId", required = false, defaultValue = "0") long testcaseId,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "page", required = false, defaultValue = "1") long pageNumber,
			HttpServletRequest request, HttpServletResponse response) {

		long totalSubmissions = submissionService.getNumberOfSubmissions(null, null);
		long latestSubmissionId = submissionService.getLatestSubmissionId();
		long offset = latestSubmissionId - (pageNumber >= 1 ? pageNumber - 1 : 0) * NUMBER_OF_SUBMISSIONS_PER_PAGE;
		List<Submission> submissions = submissionService.getSubmissionsWithOffset(testcaseId, username, offset,
				NUMBER_OF_SUBMISSIONS_PER_PAGE);

		ModelAndView view = new ModelAndView("administration/all-submissions");
		view.addObject("testcaseId", testcaseId);
		view.addObject("username", username);
		view.addObject("currentPage", pageNumber);
		view.addObject("totalPages", (long) Math.ceil(totalSubmissions * 1.0 / NUMBER_OF_SUBMISSIONS_PER_PAGE));
		view.addObject("submissions", submissions);
		return view;
	}

	/**
	 * Delete submissions action.
	 *
	 * @param submissions
	 *            the submissions
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/deleteSubmissions.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteSubmissionsAction(
			@RequestParam(value = "submissions") String submissions, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(3, 1);
		List<Long> submissionList = JSON.parseArray(submissions, Long.class);
		submissionService.deleteBatchSubmission(submissionList);
		result.put("isSuccessful", true);
		return result;
	}

	/**
	 * Edits the submission view.
	 *
	 * @param submissionId
	 *            the submission id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/detail-submission/{submissionId}", method = RequestMethod.GET)
	public ModelAndView editSubmissionView(@PathVariable(value = "submissionId") long submissionId,
			HttpServletRequest request, HttpServletResponse response) {
		Submission submission = submissionService.getSubmission(submissionId);
		if (submission == null) {
			throw new ResourceNotFoundException();
		}
		ModelAndView view = new ModelAndView("administration/edit-submission");
		view.addObject("submission", submission);
		return view;
	}

	/**
	 * General settings view.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/general-settings", method = RequestMethod.GET)
	public ModelAndView generalSettingsView(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> options = generalService.GetAllOption();
		ModelAndView view = new ModelAndView("administration/general-settings");
		view.addObject("options", options);
		return view;
	}

	/**
	 * Update general action.
	 *
	 * @param version
	 *            the version
	 * @param allowUserRegister
	 *            the allow user register
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/updateGeneral.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> updateGeneralAction(@RequestParam(value = "version") String version,
			@RequestParam(value = "allowUserRegister") boolean allowUserRegister, HttpServletRequest request) {
		String Isallow = allowUserRegister == true ? "1" : "0";
		boolean updateResult = generalService.UpdateOption(version, Isallow) == 1 ? true : false;
		Map<String, Boolean> result = new HashMap<>();
		result.put("isSuccessful", updateResult);
		return result;
	}

	/**
	 * Language settings view.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/language-settings", method = RequestMethod.GET)
	public ModelAndView languageSettingsView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("administration/language-settings");
		view.addObject("formats", languageService.getAllFormat());
		view.addObject("languages", languageService.getAllLanguages());
		return view;
	}

	/**
	 * Update language settings action.
	 *
	 * @param languages
	 *            the languages
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/updateLanguageSettings.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateLanguageSettingsAction(
			@RequestParam(value = "languages") String languages, HttpServletRequest request) {
		List<Language> languagesList = JSON.parseArray(languages, Language.class);
		Map<String, Object> result = languageService.updateLanguageSettings(languagesList);
		return result;
	}

	/**
	 * Gets the submission today.
	 *
	 * @return the submission today
	 */
	private Object getSubmissionToday() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(year, month, date, 0, 0, 0);
		Date startTime = calendar.getTime();

		calendar.set(year, month, date + 1, 0, 0, 0);
		Date endTime = calendar.getTime();
		return submissionService.getNumberOfSubmissions(startTime, endTime);
	}

	/**
	 * Gets the current memory usage.
	 *
	 * @return the current memory usage
	 */
	private Object getCurrentMemoryUsage() {
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();

		return (totalMemory - freeMemory) / 1048576;
	}

	/**
	 * Gets the online judgers.
	 *
	 * @return the online judgers
	 */
	private long getOnlineJudgers() {
		return eventListener.getOnlineJudgers();
	}

	/**
	 * Gets the total testcase.
	 *
	 * @return the total testcase
	 */
	private Object getTotalTestcase() {
		return testcaseService.getTestcasesNumber();
	}

	/**
	 * Gets the public total testcase.
	 *
	 * @return the public total testcase
	 */
	private Object getPublicTotalTestcase() {
		return testcaseService.getTestcasesNumberWithPublic();
	}

	/**
	 * Gets the online users.
	 *
	 * @return the online users
	 */
	private Object getOnlineUsers() {
		return SessionListener.getTotalSessions();
	}

	/**
	 * Gets the number of user registered today.
	 *
	 * @return the number of user registered today
	 */
	private long getNumberOfUserRegisteredToday() {
		return userService.getNumberOfUserRegisteredToday();
	}

	/**
	 * Gets the total users.
	 *
	 * @return the total users
	 */
	private long getTotalUsers() {
		UserRole role = userService.getUserRoleByType("student");
		return userService.getNumberOfUser(role);
	}

	/** The number of users per page. */
	private final int NUMBER_OF_USERS_PER_PAGE = 2;

	/** The number of testcases per page. */
	private final int NUMBER_OF_TESTCASES_PER_PAGE = 5;

	/** The number of submissions per page. */
	private final int NUMBER_OF_SUBMISSIONS_PER_PAGE = 100;

	/** The user service. */
	@Autowired
	UserService userService;

	/** The testcase service. */
	@Autowired
	TestcaseService testcaseService;

	/** The category service. */
	@Autowired
	TestcaseCategoryService categoryService;

	/** The submission service. */
	@Autowired
	SubmissionService submissionService;

	/** The general service. */
	@Autowired

	GeneralService generalService;

	/** The language service. */
	@Autowired
	LanguageService languageService;

	/** The event listener. */
	@Autowired
	private ApplicationEventListener eventListener;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(AdminController.class);
}
