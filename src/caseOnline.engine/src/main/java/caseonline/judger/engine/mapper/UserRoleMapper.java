package caseonline.judger.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.engine.model.UserRole;

/**
 * The Interface UserRoleMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface UserRoleMapper {

	/**
	 * Delete user role.
	 *
	 * @param roleId
	 *            the role id
	 * @return the int
	 */
	int deleteUserRole(@Param("roleId") int roleId);

	/**
	 * Creates the user role.
	 *
	 * @param userRole
	 *            the user role
	 * @return the int
	 */
	int createUserRole(UserRole userRole);

	/**
	 * Update user role.
	 *
	 * @param userRoles
	 *            the user roles
	 * @return the int
	 */
	int updateUserRole(UserRole userRoles);

	/**
	 * Gets the user role by id.
	 *
	 * @param roleId
	 *            the role id
	 * @return the user role by id
	 */
	UserRole getUserRoleById(@Param("roleId") int roleId);

	/**
	 * Gets the user role by type.
	 *
	 * @param roleType
	 *            the role type
	 * @return the user role by type
	 */
	UserRole getUserRoleByType(@Param("roleType") String roleType);

	/**
	 * Gets the all user roles.
	 *
	 * @return the all user roles
	 */
	List<UserRole> getAllUserRoles();

}