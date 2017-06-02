package caseonline.judger.web.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.web.model.User;
import caseonline.judger.web.model.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class UserMapperTest {
	/**
	 * 测试创建用户createUser()
	 */
	@Test
	public void testCreateUser() {
		Calendar calendar = Calendar.getInstance();
		Date data = calendar.getTime();
		User user = new User();
		int result=userMapper.createUser(user);		
		assertEquals(1, result);
	}
	/**
	 * 测试更新用户updateUser()
	 */
	@Ignore("finished")
	@Test
	public void testUpdateUser() {
		Calendar calendar = Calendar.getInstance();
		Date data = calendar.getTime();
		User user = new User();
		userMapper.updateUser(user);
	}
	/**
	 * 测试通过用户ID查询用户getUserById()
	 */
	@Ignore("finished")
	@Test
	public void testGetUserById() {
		User user = userMapper.getUserById(1);		
		assertNotNull(user);
		String userName = user.getUserName();
		assertEquals("wxh", userName);
	}
	/**
	 * 测试通过用户邮箱查询用户getUserByEmail()
	 */
	@Ignore("finished")
	@Test
	public void testGetUserByEmail() {
		User user = userMapper.getUserByEmail("wxh054511@sina.com");
		assertNotNull(user);
		String userName = user.getUserName();
		assertEquals("wxh", userName);
	}
	/**
	 * 测试通过用户名查询用户getUserByName()
	 */
	@Ignore("finished")
	@Test
	public void testGetUserByName() {
		User user = userMapper.getUserByName("wxh");
		assertNotNull(user);
		String userName = user.getUserName();
		assertEquals("wxh", userName);
	}
	/**
	 * 测试按条件查询用户数量getNumberOfUserByCondtion()
	 */
	@Ignore("finished")
	@Test
	public void testGetNumberOfUserByCondtion() {
		UserRole userRole = new UserRole();
		userRole.setRoleId(1);
		long result = userMapper.getNumberOfUserByCondition(userRole, "wxh");
		assertEquals(1, result);
	}
	/**
	 * 测试按条件查询用户getAllUserByConditon() 
	 */
	@Ignore("finished")
	@Test
	public void testGetAllUserByConditon() {
		UserRole userRole = new UserRole();
		userRole.setRoleId(1);
		List<User> users = userMapper.getAllUserByConditon(userRole, "wxh", 0, 1);
		int result = users.size();
		assertEquals(1, result);
	}
	/**
	 * 测试删除用户deleteUser()
	 */
	@Ignore("finished")
	@Test
	public void testDeleteUser() {
		userMapper.deleteUser(12);
		long result = userMapper.getNumberOfUserByCondition(null, null);
		assertEquals(3, result);
	}

	@Autowired
	UserMapper userMapper;
}
