package caseonline.judger.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import caseonline.judger.web.messenger.ApplicationEventListener;
import caseonline.judger.web.model.Judgers;
import caseonline.judger.web.model.Language;
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
		HttpSession session = request.getSession();
		long userId = (Long)session.getAttribute("uid");
		User user = userService.getUserById(userId);		
		
		//管理员可见信息
		if (user.getRole().getRoleType().equals("admin")) {
			view.addObject("totalUsers", getTotalUsers());
			view.addObject("newUsersToday", getNumberOfUserRegisteredToday());
			view.addObject("onlineUsers", getOnlineUsers());
			view.addObject("submissionsToday", getSubmissionToday());
			view.addObject("memoryUsage", getCurrentMemoryUsage());
			view.addObject("onlineJudgers", getOnlineJudgerSize());
			view.addObject("sysVersion",getSysVersion());
			userId=0;
		}
		view.addObject("totalTestcases", getTotalTestcase(userId));
		view.addObject("totalPublicTestcases", getPublicTotalTestcase(userId));
		return view;
	}

	private Object getSysVersion() {
		return generalService.getVersion();
	}

	/**
	 * 系统属性设置页面显示.
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
	
	@RequestMapping(value="/judger",method=RequestMethod.GET)
	public ModelAndView getOnlineJudgersView(HttpServletRequest request,HttpServletResponse response){
		List<Judgers> judgers=getOnlineJudgers();
		ModelAndView view = new ModelAndView("administration/judgers");
		view.addObject("judgers",judgers);
		return view;
	}

	/**
	 * 更新系统属性.
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
	 * 语言设置.
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
	 * 更新语言.
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
	 * 获取当天提交数.
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
	 * 获取当前内存.
	 *
	 * @return the current memory usage
	 */
	private Object getCurrentMemoryUsage() {
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();

		return (totalMemory - freeMemory) / 1048576;
	}

	/**
	 * 获取在线测评机数.
	 *
	 * @return the online judgers
	 */
	private long getOnlineJudgerSize() {
		return eventListener.getOnlineJudgerSize();
	}
	
	private List<Judgers> getOnlineJudgers(){
		List<Judgers> judgers=new ArrayList<>();
		Map<String, Map<String, Object>> onlineJudgers=eventListener.getOnlineJudgers();
		for (Iterator<Entry<String, Map<String, Object>>> itr = onlineJudgers.entrySet().iterator(); itr
				.hasNext();) {
			Entry<String, Map<String, Object>> entry = itr.next();
			String judgerName=entry.getKey();
			Date heartbeatTime = (Date) entry.getValue().get("heartbeatTime");
			String judgerDesc = (String) entry.getValue().get("description");
			Judgers judger=new Judgers(judgerName, heartbeatTime, judgerDesc);
			judgers.add(judger);
		}
		return judgers;
	}

	/**
	 * 获取总的测试案例数.
	 *
	 * @return the total testcase
	 */
	private Object getTotalTestcase(long userId) {
		return testcaseService.getTestcasesNumberByCondition("", "", false, userId);
	}

	/**
	 * 获取开放测试案例数.
	 *
	 * @return the public total testcase
	 */
	private Object getPublicTotalTestcase(long userId) {
		return testcaseService.getTestcasesNumberByCondition("", "", true, userId);
	}

	/**
	 * 获取在现用户.
	 *
	 * @return the online users
	 */
	private Object getOnlineUsers() {
		return SessionListener.getTotalSessions();
	}

	/**
	 * 获取今日注册用户数.
	 *
	 * @return the number of user registered today
	 */
	private long getNumberOfUserRegisteredToday() {
		return userService.getNumberOfUserRegisteredToday();
	}

	/**
	 * 获取总用户数.
	 *
	 * @return the total users
	 */
	private long getTotalUsers() {
		UserRole role = userService.getUserRoleByType("student");
		return userService.getNumberOfUser(role);
	}





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

	/** The user service. */
	@Autowired
	UserService userService;
	
}
