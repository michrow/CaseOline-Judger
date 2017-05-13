package caseonline.judger.web.controller;

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

import caseonline.judger.web.model.User;
import caseonline.judger.web.service.SubmissionService;
import caseonline.judger.web.service.UserService;

/**
 * 处理用户请求.
 *
 * @author 王肖辉 2017年4月9日
 */

@Controller
@RequestMapping(value = "/accounts")
public class AccountsController {

	/**
	 * 显示登录页面
	 *
	 * @param isLogout
	 *            the is logout
	 * @param forwardUrl
	 *            the forward url
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginView(
			@RequestParam(value = "logout", required = false, defaultValue = "false") boolean isLogout,
			@RequestParam(value = "forward", required = false, defaultValue = "") String forwardUrl,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (isLogout) {
			destroySession(request, session);
		}
		ModelAndView view = null;
		if (isLoggedIn(session)) {
			view = new ModelAndView("redirect:/");
		} else {
			view = new ModelAndView("accounts/login");
			view.addObject("isLogout", isLogout);
			view.addObject("forwardUrl", forwardUrl);
		}
		return view;
	}

	/**
	 * 处理登录请求
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param isAutoLoginAllowed
	 *            the is auto login allowed
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/login.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> loginAction(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "rememberMe") boolean isAutoLoginAllowed, HttpServletRequest request) {
		Map<String, Boolean> result = userService.isAllowedToLogin(username, password);
		LOGGER.info(String.format("%s tried to log in", username));

		if (result.get("isSuccessful")) {
			User user = userService.getUserByNameOrEmail(username);
			createSession(request, user, isAutoLoginAllowed);
		}
		return result;
	}

	/**
	 * 创建Session
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @param isAutoLoginAllowed
	 *            the is auto login allowed
	 */
	private void createSession(HttpServletRequest request, User user, Boolean isAutoLoginAllowed) {
		HttpSession session = request.getSession();
		session.setAttribute("isLoggedIn", true);
		session.setAttribute("isAutoLoginAllowed", isAutoLoginAllowed);
		session.setAttribute("uid", user.getUserId());
		LOGGER.info(String.format("%s logged in at ", new Object[] { user }));
	}

	/**
	 * 显示注册页面.
	 *
	 * @param forwardUrl
	 *            the forward url
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerView(
			@RequestParam(value = "forward", required = false, defaultValue = "") String forwardUrl,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView view = null;

		if (isLoggedIn(session)) {
			view = new ModelAndView("redirect:/");
		} else {
			boolean isAllowRegister = true;
			view = new ModelAndView("accounts/register");
			view.addObject("isAllowRegister", isAllowRegister);
		}
		return view;
	}

	/**
	 * 处理注册请求 供学生注册.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "register.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> registerAction(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "email") String email,
			HttpServletRequest request) {
		boolean isAllowRegister = true;
		int roleId = 2;
		Map<String, Boolean> result = userService.addUser(username, password, email, roleId, isAllowRegister);
		if (result.get("isSuccessful")) {
			User user = userService.getUserByNameOrEmail(username);
			createSession(request, user, false);
			LOGGER.info(String.format("User: [Username=%s] created", new Object[] { username }));
		}
		return result;
	}

	/**
	 * 删除Session
	 *
	 * @param request
	 *            the request
	 * @param session
	 *            the session
	 */
	private void destroySession(HttpServletRequest request, HttpSession session) {
		long userId = (Long) request.getSession().getAttribute("uid");
		User currentUser = userService.getUserById(userId);

		LOGGER.info(String.format("%s logged out an", new Object[] { currentUser }));
		session.setAttribute("isLoggedIn", false);
	}

	/**
	 * User view.
	 *
	 * @param userId
	 *            the user id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ModelAndView userView(@PathVariable("userId") long userId, HttpServletRequest request,
			HttpServletResponse response) {
		User user = userService.getUserById(userId);
		ModelAndView view = new ModelAndView("accounts/user");
		view.addObject("user", user);
		view.addObject("submissions", submissionService.getSubmissionsByUserId(userId));
		return view;
	}

	/**
	 * 显示用户信息页面
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboardView(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView view = null;
		long userId = (Long) session.getAttribute("uid");
		User user = userService.getUserById(userId);
		view = new ModelAndView("accounts/dashboard");
		view.addObject("user", user);
		view.addObject("submissions", submissionService.getSubmissionsByUserId(userId));
		return view;
	}

	/**
	 * 处理修改密码请求
	 *
	 * @param oldPassword
	 *            the old password
	 * @param newPassword
	 *            the new password
	 * @param confirmPassword
	 *            the confirm password
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/changePassword.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> changePasswordInDashboardAction(
			@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword,
			@RequestParam(value = "confirmPassword") String confirmPassword, HttpServletRequest request) {
		long userId = (Long) request.getSession().getAttribute("uid");
		User currentUser = userService.getUserById(userId);
		Map<String, Boolean> result = userService.changePassword(currentUser, oldPassword, newPassword,
				confirmPassword);
		if (result.get("isSuccessful")) {
			LOGGER.info(String.format("%s changed password", new Object[] { currentUser }));
		}
		return result;
	}

	/**
	 * 处理更新用户信息请求
	 *
	 * @param email
	 *            the email
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/updateProfile.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Boolean> updateProfileInDashboardAction(
			@RequestParam(value = "email") String email, HttpServletRequest request) {
		long userId = (Long) request.getSession().getAttribute("uid");
		User currentUser = userService.getUserById(userId);
		Map<String, Boolean> result = userService.updateProfile(currentUser, email);
		if (result.get("isSuccessful")) {
			LOGGER.info(String.format("%s updated profile", new Object[] { currentUser }));
		}
		return result;
	}

	/**
	 * 判断用户是否登录.
	 *
	 * @param session
	 *            the session
	 * @return true, if is logged in
	 */

	private boolean isLoggedIn(HttpSession session) {
		Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
		if (isLoggedIn == null || !isLoggedIn.booleanValue()) {
			return false;
		}
		return true;
	}

	/** The user service. */
	@Autowired

	UserService userService;

	/** The submission service. */
	@Autowired
	SubmissionService submissionService;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(AccountsController.class);

}
