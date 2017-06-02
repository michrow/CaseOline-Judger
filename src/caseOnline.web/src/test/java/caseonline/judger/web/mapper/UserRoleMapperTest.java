package caseonline.judger.web.mapper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.web.model.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class UserRoleMapperTest {

	/**
	 * 测试创建用户角色createUserRole()
	 */
	@Ignore
	@Test
	public void testCreateUserRole() {
		UserRole userRole=new UserRole("test", "test");
		int result=userRoleMapper.createUserRole(userRole);
		assertEquals(1, result);
	}
	
	/**
	 * 测试通过角色类型查询用户角色getUserRoleByType()
	 */
	//@Ignore
	@Test
	public void testGetUserRoleByType() {
		UserRole userRole=userRoleMapper.getUserRoleByType("test");
		assertNotNull(userRole);
		
	}
	
	/**
	 * 测试更细用户角色updateUserRole
	 */
	//@Ignore
	@Test
	public void testUpdateUserRole(){
		 UserRole userRole=userRoleMapper.getUserRoleByType("test");
		 userRole.setRoleName("newTest");
		 userRoleMapper.updateUserRole(userRole);
		 String roleName=userRole.getRoleName();
		 assertEquals("newTest", roleName);
	}
	/**
	 * 测试通过角色ID查询用户角色getUserRoleById()
	 */
	//@Ignore
	@Test
	public void testGetUserRoleById() {
		UserRole userRole=userRoleMapper.getUserRoleById(2);
		assertNotNull(userRole);
		String roleType=userRole.getRoleType();
		assertEquals("test", roleType);
	}
	
	/**
	 * 测试获取所有用户角色getAllUserRoles()
	 */
	//@Ignore
	@Test
	public void testGetAllUserRoles(){
		List<UserRole> userRoles=userRoleMapper.getAllUserRoles();
		int result=userRoles.size();
		assertEquals(1, result);
	}
	
	/**
	 * 测试删除用户角色deleteUserRole()
	 */
	@Ignore
	@Test
	public void TestDeleteUserRole(){
		userRoleMapper.deleteUserRole(3);
		List<UserRole> userRoles=userRoleMapper.getAllUserRoles();
		int result=userRoles.size();
		assertEquals(1, result);

	}

	@Autowired
	UserRoleMapper userRoleMapper;
}
