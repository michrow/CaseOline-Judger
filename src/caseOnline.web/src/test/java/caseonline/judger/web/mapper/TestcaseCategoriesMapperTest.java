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

import caseonline.judger.web.model.TestcaseCategory;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class TestcaseCategoriesMapperTest {
	@Ignore
	@Test
	public void testCreateCategory(){
		TestcaseCategory category=new TestcaseCategory("math", "数学类", 0);
		int result=testcaseCategoryMapper.createCategory(category);
		assertEquals(1, result);
	}
	
	@Test
	public void testUpdateCategory(){
		TestcaseCategory category=new TestcaseCategory(1,"tool", "数学类", 0);
		int result=testcaseCategoryMapper.updateCategory(category);
		assertEquals(1, result);
	}
	
	@Test
	public void testGetCategoryById(){
		TestcaseCategory testcaseCategories=testcaseCategoryMapper.getCategoryById(1);
		assertNotNull(testcaseCategories);
		String categoryName=testcaseCategories.getCategoryName();
		assertEquals("tool", categoryName);
	}
	
	@Test
	public void testGetCategoryByName(){
		TestcaseCategory testcaseCategories=testcaseCategoryMapper.getCategoryByName("数学类");
		assertNotNull(testcaseCategories);
		String categoryName=testcaseCategories.getCategoryName();
		assertEquals("math", categoryName);
	}
	@Test
	public void testGetCategoryByType(){
		TestcaseCategory testcaseCategories=testcaseCategoryMapper.getCategoryByType("math");
		assertNotNull(testcaseCategories);
		String categoryName=testcaseCategories.getCategoryName();
		assertEquals("数学类", categoryName);
	}
	@Test
	public void testGetAllCategories(){
		List<TestcaseCategory> testcaseCategories=testcaseCategoryMapper.getAllTestcaseCategories();
		int result=testcaseCategories.size();
		assertEquals(2, result);
	}
	
	
	@Autowired
	TestcaseCategoryMapper testcaseCategoryMapper;
}
