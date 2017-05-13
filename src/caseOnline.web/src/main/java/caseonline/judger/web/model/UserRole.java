package caseonline.judger.web.model;

/**
 * 用户角色.
 */
public class UserRole {

	/**
	 * Instantiates a new user role.
	 */
	public UserRole() {
	}

	/**
	 * Instantiates a new user role.
	 *
	 * @param roleType
	 *            the role type
	 * @param roleName
	 *            the role name
	 */
	public UserRole(String roleType, String roleName) {
		super();
		this.roleType = roleType;
		this.roleName = roleName;
	}

	/**
	 * Instantiates a new user role.
	 *
	 * @param roleId
	 *            the role id
	 * @param roleType
	 *            the role type
	 * @param roleName
	 *            the role name
	 */
	public UserRole(Integer roleId, String roleType, String roleName) {
		this(roleType, roleName);
		this.roleId = roleId;
	}

	/** 角色ID. */
	private Integer roleId;

	/** 角色类型. */
	private String roleType;

	/** 角色名字. */
	private String roleName;

	/** 备用字段 1. */
	private String rolebak1;

	/** 备用字段 2. */
	private String rolebak2;

	/** 备用字段 3. */
	private String rolebak3;

	/**
	 * Gets the 角色ID.
	 *
	 * @return the 角色ID
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * Sets the 角色ID.
	 *
	 * @param roleId
	 *            the new 角色ID
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * Gets the 角色类型.
	 *
	 * @return the 角色类型
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * Sets the 角色类型.
	 *
	 * @param roleType
	 *            the new 角色类型
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * Gets the 角色名字.
	 *
	 * @return the 角色名字
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the 角色名字.
	 *
	 * @param roleName
	 *            the new 角色名字
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getRolebak1() {
		return rolebak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param rolebak1
	 *            the new 备用字段 1
	 */
	public void setRolebak1(String rolebak1) {
		this.rolebak1 = rolebak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getRolebak2() {
		return rolebak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param rolebak2
	 *            the new 备用字段 2
	 */
	public void setRolebak2(String rolebak2) {
		this.rolebak2 = rolebak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getRolebak3() {
		return rolebak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param rolebak3
	 *            the new 备用字段 3
	 */
	public void setRolebak3(String rolebak3) {
		this.rolebak3 = rolebak3;
	}

}