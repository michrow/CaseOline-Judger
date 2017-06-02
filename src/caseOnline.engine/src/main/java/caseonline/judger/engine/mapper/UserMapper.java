package caseonline.judger.engine.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.engine.model.User;
import caseonline.judger.engine.model.UserRole;

/**
 * The Interface UserMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface UserMapper {

	/**
	 * Delete user.
	 *
	 * @param userId
	 *            the user id
	 * @return the int
	 */
	int deleteUser(@Param("userId") long userId);

	/**
	 * Creates the user.
	 *
	 * @param user
	 *            the user
	 * @return the int
	 */
	int createUser(User user);

	/**
	 * Update user.
	 *
	 * @param user
	 *            the user
	 * @return the int
	 */
	int updateUser(User user);

	/**
	 * Gets the user by id.
	 *
	 * @param userId
	 *            the user id
	 * @return the user by id
	 */
	User getUserById(@Param("userId") long userId);

	/**
	 * Gets the user by name.
	 *
	 * @param userName
	 *            the user name
	 * @return the user by name
	 */
	User getUserByName(@Param("userName") String userName);

	/**
	 * Gets the user by email.
	 *
	 * @param email
	 *            the email
	 * @return the user by email
	 */
	User getUserByEmail(@Param("email") String email);

	/**
	 * 多条件查询用户数量
	 *
	 * @param userRole
	 *            --用户角色
	 * @param userName
	 *            --用户名
	 * @return the number of user by condition
	 */
	long getNumberOfUserByCondition(@Param("userRole") UserRole userRole, @Param("userName") String userName);

	/**
	 * 多条件查询所有用户.
	 *
	 * @param userRole
	 *            --用户角色
	 * @param userName
	 *            --用户名
	 * @param offset
	 *            --偏移量
	 * @param limit
	 *            --每次查询量
	 * @return the all user by conditon
	 */
	List<User> getAllUserByConditon(@Param("userRole") UserRole userRole, @Param("userName") String userName,
			@Param("offset") long offset, @Param("limit") int limit);

	/**
	 * 查询某时间内用户的注册数量
	 * 
	 * @param startTime
	 *            --开始时间
	 * @param endTime
	 *            --结束时间
	 * @return
	 */
	long getNumberOfUserRegistered(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}