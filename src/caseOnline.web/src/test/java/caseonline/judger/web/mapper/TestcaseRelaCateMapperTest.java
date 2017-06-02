package caseonline.judger.web.mapper;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.web.model.TestcaseRelaCate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class TestcaseRelaCateMapperTest {
	@Ignore
	@Test
	public void testCreate(){
		TestcaseRelaCate testcaseRelaCate=new TestcaseRelaCate();
		int result=testcaseRelaCateMapper.createTestcaseCategoryRela(testcaseRelaCate);
		assertEquals(1, result);
	}
	
	@Test
	public void testGetTestcaseCateRelaById(){
		List<TestcaseRelaCate> testcaseRelaCates=testcaseRelaCateMapper.getTestcaseCategoriesByTestcaseId(4l);
		int result=testcaseRelaCates.size();
		assertEquals(2, result);
	}
	
	@Test
	public void testGetTestcaseCateRelaByLimit(){
		List<TestcaseRelaCate>testcaseRelaCates=testcaseRelaCateMapper.getTestcaseCategoriesByTestcases(1, 4);
		int result=testcaseRelaCates.size();
		assertEquals(5, result);
	}
	//@Ignore
	@Test
	public void testDelete(){
		int result=testcaseRelaCateMapper.deleteTestcaseCategoryRela(2);
		assertEquals(1, result);
	}
	@Autowired
	TestcaseRelaCateMapper testcaseRelaCateMapper;
}
