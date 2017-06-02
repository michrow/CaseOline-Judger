package caseonline.judger.web.controller;

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
import caseonline.judger.web.model.User;
import caseonline.judger.web.model.UserRole;
import caseonline.judger.web.service.UserService;

/**
 * 用户管理请求处理
 * @author 王肖辉
 * 2017年5月13日
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserManagerController {
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
	
	/** The number of users per page. */
	private final int NUMBER_OF_USERS_PER_PAGE = 2;
	
	/** The user service. */
	@Autowired
	UserService userService;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(UserManagerController.class);
}
