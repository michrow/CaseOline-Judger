package caseonline.judger.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caseonline.judger.web.mapper.TestcaseCategoryMapper;
import caseonline.judger.web.mapper.TestcaseRelaCateMapper;
import caseonline.judger.web.model.TestcaseCategory;
import caseonline.judger.web.model.TestcaseRelaCate;

// TODO: Auto-generated Javadoc
/**
 * The Class TestcaseCategoryService.
 */
@Service
public class TestcaseCategoryService{
	
	/**
	 * 获取所有分类.
	 *
	 * @return the testcase categories
	 */
	public List<TestcaseCategory> getTestcaseCategories() {
		return testcaseCategoriesMapper.getAllTestcaseCategories();
	}	
	
	/**
	 * 添加分类.
	 *
	 * @param category the category
	 * @return the int
	 */
	public int addCategory(TestcaseCategory category){
		return testcaseCategoriesMapper.createCategory(category);
	}
	
	/**
	 * 更新分类.
	 *
	 * @param category the category
	 * @return the int
	 */
	public int  editCategory(TestcaseCategory category) {
		return testcaseCategoriesMapper.updateCategory(category);
	}
	
	/**
	 * 删除分类.
	 *
	 * @param categoryId the category id
	 * @return the int
	 */
	public int deletCategory(int categoryId) {
		return testcaseCategoriesMapper.deleteCategory(categoryId);
	}
	
	/**
	 * 批量删除分类.
	 *
	 * @param categoryIds the category ids
	 * @return the map
	 */
	public Map<Integer, Boolean> deleteBatch(List<Integer> categoryIds){
		Map<Integer, Boolean> result=new HashMap<>();
		for (Integer categoryId : categoryIds) {
			boolean success=testcaseCategoriesMapper.deleteCategory(categoryId)==1?true:false;
			result.put(categoryId, success);
		}
		return result;
	}
	
	public Map<Long, List<TestcaseCategory>> getTestcastOfCategory(long testcaseIdLowerBound, long testcaseIdUpperBound ){
		List<TestcaseRelaCate> testcaseRelaCates=testcaseRelaCateMapper.getTestcaseCategoriesByTestcases(testcaseIdLowerBound, testcaseIdUpperBound);
		Map<Long, List<TestcaseCategory>> testcaseCategory = new HashMap<>();
		for (TestcaseRelaCate testcaseRelaCate : testcaseRelaCates) {
			long testcaseId=testcaseRelaCate.getTestcaseId();
			if(!testcaseCategory.containsKey(testcaseId)){
				testcaseCategory.put(testcaseId, new ArrayList<TestcaseCategory>());
			}
			List<TestcaseCategory> testcaseCategories=testcaseCategory.get(testcaseId);
			List<TestcaseRelaCate> testcaseRelaCates2=testcaseRelaCateMapper.getTestcaseCategoriesByTestcaseId(testcaseId);
			for (TestcaseRelaCate cate : testcaseRelaCates2) {
				testcaseCategories.add(cate.getCategory());
			}
		}
		return 	testcaseCategory;
	}
	
	/** The testcase categories mapper. */
	@Autowired
	TestcaseCategoryMapper testcaseCategoriesMapper;
	
	@Autowired
	TestcaseRelaCateMapper testcaseRelaCateMapper;
}
