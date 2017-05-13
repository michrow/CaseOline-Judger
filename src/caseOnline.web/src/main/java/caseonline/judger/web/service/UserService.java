/*
 * 
 */
package caseonline.judger.web.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caseonline.judger.web.mapper.UserMapper;
import caseonline.judger.web.mapper.UserRoleMapper;
import caseonline.judger.web.model.User;
import caseonline.judger.web.model.UserRole;
import caseonline.judger.web.util.DigestUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class UserService.
 */
@Service
public class UserService {

	/**
	 * 创建用户.
	 *
	 * @param username the username
	 * @param password the password
	 * @param email the email
	 * @param roleId the role id
	 * @param isAllowRegister the is allow register
	 * @return 创建结果
	 */
	
	public Map<String, Boolean> addUser(String username, String password, String email, int roleId,
			boolean isAllowRegister) {
		Calendar calendar = Calendar.getInstance();
		Date registerTime = calendar.getTime();
		UserRole userRole = userRoleMapper.getUserRoleById(roleId);
		User user = new User(username, DigestUtils.md5Hex(password), email, userRole, registerTime);
		Map<String, Boolean> result = verifyRegisterInfo(user, password, isAllowRegister);
		if (result.get("isSuccessful")) {
			userMapper.createUser(user);
		}
		return result;
	}
	
	public Map<String, Boolean> addUser(String username, String password, String email, UserRole role) {
		Calendar calendar = Calendar.getInstance();
		Date registerTime = calendar.getTime();
		User user = new User(username, DigestUtils.md5Hex(password), email, role, registerTime);
		Map<String, Boolean> result = verifyRegisterInfo(user, password, true);
		if (result.get("isSuccessful")) {
			userMapper.createUser(user);
		}
		return result;
	}
	/**
	 * 登录验证.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the map
	 */
	public Map<String, Boolean> isAllowedToLogin(String username, String password) {
		Map<String, Boolean> result = new HashMap<>();
		result.put("isUsernameEmpty", username.isEmpty());
		result.put("isPasswordEmpty", password.isEmpty());
		result.put("isAccountValid", false);
		result.put("isSuccessful", false);

		if (!result.get("isUsernameEmpty") && !result.get("isPasswordEmpty")) {
			User user = getUserByNameOrEmail(username);
			if (user != null && user.getPassword().equals(password)) {
				result.put("isAccountValid", true);
				result.put("isSuccessful", true);
			}
		}
		return result;
	}

	/**
	 * 通过邮箱 用户名获取用户对象.
	 *
	 * @param username the username
	 * @return the user by name or email
	 */
	public User getUserByNameOrEmail(String username) {
		boolean isUsingEmail = username.indexOf('@') != -1;
		User user = null;

		if (!isUsingEmail) {
			user = userMapper.getUserByName(username);
		} else {
			user = userMapper.getUserByEmail(username);
		}
		return user;
	}

	public long getNumberOfUserRegisteredToday(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		
		calendar.set(year, month, date, 0, 0, 0);
		Date startTime = calendar.getTime();
		calendar.set(year, month, date, 23, 59, 59);
		Date endTime = calendar.getTime();
		
		return userMapper.getNumberOfUserRegistered(startTime, endTime);
	}
	
	/**
	 * 通过ID获取用户对象.
	 *
	 * @param userId the user id
	 * @return the user by id
	 */
	public User getUserById(long userId) {
		return userMapper.getUserById(userId);
	}
	
	public UserRole getUserRoleByType(String roleType){
		return userRoleMapper.getUserRoleByType(roleType);
	}
	
	public long getNumberOfUser(UserRole role) {
		return userMapper.getNumberOfUserByCondition(role, "");
	}
	/**
	 * 删除用户.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUser(long userId){
		return userMapper.deleteUser(userId);
	}
	
	/**
	 * 批量删除用户.
	 *
	 * @param userIds the user ids
	 * @return the map
	 */
	public Map<Long, Boolean> deleteBatchUser(List<Long> userIds){
		Map<Long, Boolean> result=new HashMap<>();
		for (Long userId : userIds) {
			boolean success=userMapper.deleteUser(userId)==1?true:false;
				result.put(userId, success);
		}
		return result;
	}
	
	/**
	 * 更新用户.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int editUser(User user){
		return userMapper.updateUser(user);
	}
	
	public List<UserRole> getAllRole(){
		return userRoleMapper.getAllUserRoles();
	}
	
	public long getUsersTotalByCondition(UserRole userRole,String userName){
		return userMapper.getNumberOfUserByCondition(userRole, userName);
	}
	
	public List<User> getUsersByCondition(UserRole userRole,String userName,long offset,int limit){
		return userMapper.getAllUserByConditon(userRole, userName, offset, limit);
	}

	/**
	 * 验证注册信息.
	 *
	 * @param user the user
	 * @param password the password
	 * @param isAllowRegister the is allow register
	 * @return the map
	 */
	private Map<String, Boolean> verifyRegisterInfo(User user, String password, boolean isAllowRegister) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isUsernameEmpty", user.getUserName().isEmpty());
		result.put("isUsernameLegal", isUsernameLegal(user.getUserName()));
		result.put("isPasswordEmpty", password.isEmpty());
		result.put("isPasswordLegal", isPasswordLegal(password));
		result.put("isEmailEmpty", user.getEmail().isEmpty());
		result.put("isEmailLegal", isEmailLegal(user.getEmail()));
		result.put("isAllowRegister", isAllowRegister);

		Boolean isSuccessful = !result.get("isUsernameEmpty") && result.get("isUsernameLegal")
				&& !result.get("isPasswordEmpty") && result.get("isPasswordLegal") && !result.get("isEmailEmpty")
				&& result.get("isEmailLegal") && result.get("isAllowRegister");
		result.put("isSuccessful", isSuccessful);
		return result;
	}

	/**
	 * 验证邮箱的合法性.
	 *
	 * @param email the email
	 * @return the boolean
	 */
	private Boolean isEmailLegal(String email) {
		int emailLength = email.length();
		Boolean format = emailLength <= 64 && email.matches("^[A-Za-z0-9\\._-]+@[A-Za-z0-9_-]+\\.[A-Za-z0-9\\._-]+$");
		if (format) {
			User user = userMapper.getUserByEmail(email);
			return user == null;
		}
		return false;

	}

	/**
	 * 验证密码的合法性.
	 *
	 * @param password the password
	 * @return the boolean
	 */
	private Boolean isPasswordLegal(String password) {
		int passwordLength = password.length();
		return passwordLength >= 6 && passwordLength <= 12;
	}

	/**
	 * 验证用户名的合法性.
	 *
	 * @param userName the user name
	 * @return the boolean
	 */
	private Boolean isUsernameLegal(String userName) {
		Boolean format = userName.matches("^[A-Za-z][A-Za-z0-9_]{5,15}$");
		if (format) {
			User user = userMapper.getUserByName(userName);
			return user == null;
		}
		return false;
	}
	public Map<String, Boolean> changePassword(User user, String oldPassword, String newPassword,
			String confirmPassword) {
		Map<String, Boolean> result = getChangePasswordResult(user, oldPassword, newPassword, confirmPassword);
		
		if (result.get("isSuccessful") ) {
			user.setPassword(DigestUtils.md5Hex(newPassword));
			userMapper.updateUser(user);
		}
		
		return result;
	}
	
	public Map<String, Boolean> updateProfile(User user, String email) {
		
		Map<String, Boolean> result = getUpdateProfileResult(user, email);
		
		if ( result.get("isSuccessful") ) {
			user.setEmail(email);
			userMapper.updateUser(user);
		}
		return result;
	}
	
	public Map<String, Boolean> updateProfile(User user, String password, String email, String roleType) {
		Map<String, Boolean> result = getUpdateProfileResult(user, email);
		UserRole role=userRoleMapper.getUserRoleByType(roleType);
		if ( result.get("isSuccessful") ) {
			user.setEmail(email);
			user.setPassword(DigestUtils.md5Hex(password));
			user.setRole(role);
			userMapper.updateUser(user);
		}
		return result;
	}
	
	private Map<String, Boolean> getUpdateProfileResult(User user, String email) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isEmailEmpty", email.isEmpty());
		result.put("isEmailLegal", isEmailLegal(email));
		result.put("isEmailExists", isEmailExists(user.getEmail(), email));
		
		
		boolean isSuccessful = !result.get("isEmailEmpty")   && result.get("isEmailLegal")	&&
							   !result.get("isEmailExists") ;
		result.put("isSuccessful", isSuccessful);
		return result;
	}

	private Boolean isEmailExists(String oldEmail, String email) {
		if ( email.equals(oldEmail) ) {
			return false;
		}
		User user = userMapper.getUserByEmail(email);
		return user != null;
	}

	private Map<String, Boolean> getChangePasswordResult(User user, String oldPassword, String newPassword,
			String confirmPassword) {
		Map<String, Boolean> result = new HashMap<String, Boolean>(5, 1);
		result.put("isOldPasswordCorrect", isOldPasswordCorrect(user.getPassword(), oldPassword));
		result.put("isNewPasswordEmpty", newPassword.isEmpty());
		result.put("isNewPasswordLegal", isPasswordLegal(newPassword));
		result.put("isConfirmPasswordMatched", newPassword.equals(confirmPassword));
		
		boolean isSuccessful = result.get("isOldPasswordCorrect") && !result.get("isNewPasswordEmpty") &&
							   result.get("isNewPasswordLegal")   &&  result.get("isConfirmPasswordMatched");
		result.put("isSuccessful", isSuccessful);
		return result;
	}


	private Boolean isOldPasswordCorrect(String password, String oldPassword) {
		if ( password.isEmpty() ) {
			return true;
		}
		return oldPassword.equals(DigestUtils.md5Hex(password));
	}


	/** The user mapper. */
	@Autowired
	UserMapper userMapper;

	/** The user role mapper. */
	@Autowired
	UserRoleMapper userRoleMapper;



	





}
