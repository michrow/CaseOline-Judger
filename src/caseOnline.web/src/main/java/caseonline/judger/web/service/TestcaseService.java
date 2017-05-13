package caseonline.judger.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import caseonline.judger.web.mapper.LanguageMapper;
import caseonline.judger.web.mapper.TestcaseCategoryMapper;
import caseonline.judger.web.mapper.TestcaseMapper;
import caseonline.judger.web.mapper.TestcaseRelaCateMapper;
import caseonline.judger.web.model.Language;
import caseonline.judger.web.model.Testcase;
import caseonline.judger.web.model.TestcaseCategory;
import caseonline.judger.web.model.TestcaseRelaCate;
import caseonline.judger.web.util.TestcaseParse;

// TODO: Auto-generated Javadoc
/**
 * The Class TestcaseService.
 */
@Service
public class TestcaseService{


	/**
	 * 按条件获取测试案例.
	 *
	 * @param offset the offset
	 * @param keyword the keyword
	 * @param categoryType the category type
	 * @param isPublic the is public
	 * @param limit the limit
	 * @return the testcases by condition
	 */
	public List<Testcase> getTestcasesByCondition(long offset, String keyword, String categoryType, boolean isPublic,
			int limit) {
		if (offset<0) {
			offset=0;
		}
		TestcaseCategory testcaseCategories=testcaseCategoriesMapper.getCategoryByType(categoryType);
		int categoryId=0;
		if (testcaseCategories!=null) {
			categoryId=testcaseCategories.getCategoryId();
		}
		
		return testcaseMapper.getAllTestCaseByCondition(keyword, categoryId, isPublic, offset, limit);
	}

	public Testcase getTestcaseById(long testcaseId){
		return testcaseMapper.getTestcaseById(testcaseId);
	}
	/**
	 * 按条件获取测试案例数量.
	 *
	 * @param keyword the keyword
	 * @param categoryType the category type
	 * @param isPublic the is public
	 * @return the testcases number by condition
	 */
	public long getTestcasesNumberByCondition(String keyword, String categoryType, boolean isPublic) {
		TestcaseCategory testcaseCategories=testcaseCategoriesMapper.getCategoryByType(categoryType);
		int categoryId=0;
		if (testcaseCategories!=null) {
			categoryId=testcaseCategories.getCategoryId();
		}
		return testcaseMapper.getNumberofTestcaseByCondition(keyword, categoryId, isPublic);
	}

	public long getTestcasesNumber(){
		return testcaseMapper.getNumberOfTestcase();
	}
	
	public long getTestcasesNumberWithPublic(){
		return testcaseMapper.getNumberofTestcaseByCondition("", 0, true);
	}
	
	public List<TestcaseRelaCate> get(long testcaseId){
		 return testcaseRelaCateMapper.getTestcaseCategoriesByTestcaseId(testcaseId);
	}
	
	/**
	 * 获取测试案例的最小ID.
	 *
	 * @return the first index of testcase
	 */
	public long getFirstIndexOfTestcase() {
		return testcaseMapper.getLowerBoundOfTestcase();
	}
	
	public long getUpperBoundWithLimit(long offset,int limit){
		return testcaseMapper.getUpperBoundOfTestcasesWithLimit(true, offset, limit);
	}
	/**
	 * 新增测试案例.
	 * @param testcase the testcase
	 * @return the int
	 */
	public int addTestcase(Testcase testcase,int categoryId){
		int result=testcaseMapper.createTestcase(testcase);
		
		TestcaseCategory category=testcaseCategoriesMapper.getCategoryById(categoryId);
		TestcaseRelaCate testcaseRelaCate=new TestcaseRelaCate(testcase.getTestcaseId(), category);
		testcaseRelaCateMapper.createTestcaseCategoryRela(testcaseRelaCate);
		return result;
	}
	
	
	/**
	 * 更新测试案例.
	 *
	 * @param testcase the testcase
	 * @return the int
	 */
	public int editTestcase(Testcase testcase){
		return testcaseMapper.updateTestcase(testcase);
	}
	
	/**
	 * 删除测试案例.
	 *
	 * @param testcaseId the testcase id
	 * @return the int
	 */
	public int deleteTestcase(Long testcaseId){
		testcaseRelaCateMapper.deleteTestcaseCategoryRela(testcaseId);
		return testcaseMapper.deleteTestcase(testcaseId);
	}
	
	/**
	 * 批量删除测试案例.
	 *
	 * @param testcasaeIds the testcasae ids
	 * @return the map
	 */
	public Map<Long, Boolean> deleteBatchTestcase(List<Long> testcasaeIds){
		Map<Long, Boolean> result=new HashMap<>();
		for (Long testcaseId : testcasaeIds) {
			boolean success=testcaseMapper.deleteTestcase(testcaseId)==1?true:false;
			if(success) testcaseRelaCateMapper.deleteTestcaseCategoryRela(testcaseId);
			result.put(testcaseId, success);
		}
		return result;
	}
	
	public int updateTestcase(Testcase testcase) {
		return testcaseMapper.updateTestcase(testcase);
	}
	
	public Map<String, Boolean> createTestcase(String testcaseName, String testcaseCode, boolean isPublic, String testcaseDescription,
			int languageId, int categoryId) {
		
		Language language=languageMapper.getLanguageById(languageId);
		
		Testcase testcase =new Testcase(isPublic, testcaseName, language, testcaseDescription, null, testcaseCode);
		
		Map<String, Boolean>result=verifyTestcaseInfo(testcase);
		if ((boolean) result.get("isSuccessful")) {
			addTestcase(testcase, categoryId);
			String languageName=language.getLanguageName();
			if (languageName.equalsIgnoreCase("java")) {
				String testcaseParamAttr=TestcaseParse.parseTestcase(testcase.getTestcaseId(), workBaseDirectory, testcaseCode);
				testcase.setTestcaseParamAttr(testcaseParamAttr);
				updateTestcase(testcase);
			}
		}
		return result;
	}
	
	public List<Language> getAllLanguages() {
		return languageMapper.getAllLanguages();
	}
	
	private Map<String, Boolean> verifyTestcaseInfo(Testcase testcase) {
		Map<String, Boolean> result=new HashMap<>();
		result.put("IsEmptyTestcaseName", testcase.getTestcaseName().isEmpty());
		result.put("IsEmptytestcaseCode", testcase.getTestcaseCode().isEmpty());
		
		if (!(boolean)result.get("IsEmptyTestcaseName")&& !(boolean)result.get("IsEmptytestcaseCode")) {
			result.put("isSuccessful",true);
		}else{
			result.put("isSuccessful",false);
		}
		return result;
	}

	/** The testcase mapper. */
	@Autowired
	TestcaseMapper testcaseMapper;
	
	/** The testcase categories mapper. */
	@Autowired
	TestcaseCategoryMapper testcaseCategoriesMapper;
	
	@Autowired
	TestcaseRelaCateMapper testcaseRelaCateMapper;
	
	@Autowired
	LanguageMapper languageMapper;

	@Value("${judger.workDir}")
	private String workBaseDirectory;
}
