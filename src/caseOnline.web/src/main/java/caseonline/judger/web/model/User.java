package caseonline.judger.web.model;

import java.util.Date;

/**
 * 用户
 */
public class User {

	/**
	 * Instantiates a new user.
	 */
	public User() {
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param role
	 *            the role
	 * @param registerTime
	 *            the register time
	 */
	public User(String userName, String password, String email, UserRole role, Date registerTime) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.registerTime = registerTime;
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param userId
	 *            the user id
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param role
	 *            the role
	 * @param registerTime
	 *            the register time
	 * @param loginTime
	 *            the login time
	 */
	public User(Long userId, String userName, String password, String email, UserRole role, Date registerTime) {
		this(userName, password, email, role, registerTime);
		this.userId = userId;
	}

	/** 用户ID. */
	private long userId;

	/** 用户名. */
	private String userName;

	/** 用户密码. */
	private String password;

	/** 邮箱. */
	private String email;

	/** 角色. */
	private UserRole role;

	/** 注册时间. */
	private Date registerTime;

	/** 备用字段 1. */
	private String userbak1;

	/** 备用字段 2. */
	private String userbak2;

	/** 备用字段 3. */
	private String userbak3;

	/**
	 * Gets the 用户ID.
	 *
	 * @return the 用户ID
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Sets the 用户ID.
	 *
	 * @param userId
	 *            the new 用户ID
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 用户名.
	 *
	 * @return the 用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the 用户名.
	 *
	 * @param userName
	 *            the new 用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the 用户密码.
	 *
	 * @return the 用户密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the 用户密码.
	 *
	 * @param password
	 *            the new 用户密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the 邮箱.
	 *
	 * @return the 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the 邮箱.
	 *
	 * @param email
	 *            the new 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the 角色.
	 *
	 * @return the 角色
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * Sets the 角色.
	 *
	 * @param role
	 *            the new 角色
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * Gets the 注册时间.
	 *
	 * @return the 注册时间
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * Sets the 注册时间.
	 *
	 * @param registerTime
	 *            the new 注册时间
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getUserbak1() {
		return userbak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param usebak1
	 *            the new 备用字段 1
	 */
	public void setUserbak1(String userbak1) {
		this.userbak1 = userbak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getUserbak2() {
		return userbak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param usebak2
	 *            the new 备用字段 2
	 */
	public void setUserbak2(String userbak2) {
		this.userbak2 = userbak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getUserbak3() {
		return userbak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param usebak3
	 *            the new 备用字段 3
	 */
	public void setUserbak3(String userbak3) {
		this.userbak3 = userbak3;
	}

}