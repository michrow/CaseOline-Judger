package caseonline.judger.web.mapper;
import static org.junit.Assert.assertEquals;

import org.apache.ibatis.annotations.Lang;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.web.model.Language;
import caseonline.judger.web.model.Testcase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class TestcaseMapperTest {	
	/**
	 * 创建测试案例createTestcase()
	 */
	//@Ignore
	@Test
	public void testCreateTestcase(){
		Language language=languageMapper.getLanguageByName("java");
		Testcase testcase=new Testcase(true,"uitest1",language,"","","",null);
		int result = testcaseMapper.createTestcase(testcase);
		assertEquals(1, result);
	}
	
	/**
	 * 跟新测试案例updateTestcase()
	 */
	//@Ignore
	@Test
	public void testUpdateTestcase(){
		Testcase testcase=testcaseMapper.getTestcaseById(9l);
		testcase.setTestcaseName("test4");
				
		int result=testcaseMapper.updateTestcase(testcase);
		assertEquals(1, result);
	}
	//@Ignore
	@Test
	public void testGetNumberOfTestcase(){
		long result=testcaseMapper.getNumberOfTestcase();
		assertEquals(16l, result);
	}
	
	//@Ignore
	@Test
	public void testGetNumberOfTestcaseByCondition(){
		long number=testcaseMapper.getNumberofTestcaseByCondition("test", 0, true,0);
		assertEquals(2l, number);
	}
	//@Ignore
	@Test
	public void testGetLowerBoundOfTestcase(){
		long result=testcaseMapper.getLowerBoundOfTestcase();
		assertEquals(1l, result);
	}
	//@Ignore
	@Test
	public void testGetUpperBoundOfTestcase(){
		long result=testcaseMapper.getUpperBoundOfTestcase();
		assertEquals(23l, result);
	}
	//@Ignore
	@Test
	public void testGetUpperBoundOfTestcaseWithLimit(){
		long result=testcaseMapper.getUpperBoundOfTestcasesWithLimit(true, 0, 5);
		assertEquals(10l, result);
	}
	
	//@Ignore
	@Test
	public void testGetTestcaseById(){
		Testcase testcase=testcaseMapper.getTestcaseById(1l);
		String testcaseName=testcase.getTestcaseName();
		assertEquals("Java 求最大值", testcaseName);
	}
	//@Ignore
	@Test
	public void testGetTestcaseByName(){
		Testcase testcase=testcaseMapper.getTestcaseByName("Java 求最大值");
		long testcaseId=testcase.getTestcaseId();
		assertEquals(1l, testcaseId);
	}
	//@Ignore
	@Test
	public void testDeleteTestcase(){
		int result=testcaseMapper.deleteTestcase(14l);
		assertEquals(1, result);
	}
	//@Ignore("为实现")
	@Test
	public void testGetAllTestCasByConditon(){
		int number=testcaseMapper.getAllTestCaseByCondition("", 0, true, 0, 5,0).size();
		assertEquals(5, number);
	}
	@Autowired
	TestcaseMapper testcaseMapper;
	@Autowired
	LanguageMapper languageMapper;
}
