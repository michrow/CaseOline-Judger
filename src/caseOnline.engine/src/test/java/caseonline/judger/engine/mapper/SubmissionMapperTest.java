package caseonline.judger.engine.mapper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.engine.model.Language;
import caseonline.judger.engine.model.Submission;
import caseonline.judger.engine.model.Testcase;
import caseonline.judger.engine.model.User;
import caseonline.judger.engine.util.DigestUtils;
import caseonline.judger.engine.util.Preprocessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context.xml" })
//测试完成
public class SubmissionMapperTest {
	/**
	 * createSubmission
	 */
	@Ignore("finished")
	@Test
	public void testCreateSubmission(){
		Language language=languageMapper.getLanguageById(4);		
		Testcase testcase=testcaseMapper.getTestcaseById(2l);		
		Testcase testcase1=testcaseMapper.getTestcaseById(5l);	
		User user=userMapper.getUserById(1l);		
		User user1=userMapper.getUserById(2l);		
		
		Calendar calendar=Calendar.getInstance();
		Date submissionSubmitTime=calendar.getTime();
		
		Submission submission=new Submission(testcase, user1, language, submissionSubmitTime, "use", 5);
		Submission submission1=new Submission(testcase1, user, language, submissionSubmitTime, "use1", 7);
		Submission submission2=new Submission(testcase, user1, language, submissionSubmitTime, "use", 4);
		Submission submission3=new Submission(testcase1, user, language, submissionSubmitTime, "use1", 1);
		Submission submission4=new Submission(testcase, user1, language, submissionSubmitTime, "use1", 9);
		
		submissionMapper.createSubmission(submission1);
		submissionMapper.createSubmission(submission2);
		submissionMapper.createSubmission(submission3);
		submissionMapper.createSubmission(submission4);
		
		int result=submissionMapper.createSubmission(submission);
		assertEquals(1, result);
	}
	
	/**
	 * deleteSubmission()
	 */
	@Ignore("finished")
	@Test
	public void testDeleteSubmission(){
		int result=submissionMapper.deleteSubmission(1l);
		assertEquals(1, result);
	}

	
	/**
	 * updateSubmission
	 */
	@Ignore("finished")
	@Test
    public void testUpdateSubmission(){
		
		Submission submission=submissionMapper.getSubmissionById(5l);
		submission.setUsecaseAmount(10);
		int result=submissionMapper.updateSubmission(submission);
		assertEquals(1, result);
	}
	
	/**
	 * getSubmissionByLanguageId()
	 */
	@Ignore("finished")
	@Test
	public void testGetSubmissionById(){
		Submission submission=submissionMapper.getSubmissionById(5l);		
		assertNotNull(submission);		
	}
	
	/**
	 * getSubmissionBySubmissionId()
	 */
	@Ignore("finished")
	@Test
	public void testGetSubmissionByUserId(){
		List<Submission> submissions=submissionMapper.getLatestSubmissionsOfUser(2l);
		assertNotNull(submissions);
		int result=submissions.size();
		assertEquals(3, result);
	}
	
	/**
	 * getSubmissionsByTestcaseIdAndUserId()
	 */
	@Ignore("finished")
	@Test
	public void testGetSubmissionsByTestcaseIdAndUserId(){
		List<Submission> submissions=submissionMapper.getSubmissionsByTcIdOrUId(3l, "", 10);
		int result =submissions.size();
		assertEquals(2, result);
	}
	/**
	 * getSubmissionsByOffset()
	 */
	@Ignore("finished")
	@Test
	public void testGetSubmissionsByOffset(){
		List<Submission> submissions=submissionMapper.getSubmissionsByOffset(2l, "wxh", 0, 15);
		int result=submissions.size();
		assertEquals(3, result);
	}
	/**
	 * getLatestSubmissionsByOffset
	 */
	@Ignore("finished")
	@Test
	public void testGetLatestSubmissionsByOffset(){
		List<Submission> submissions=submissionMapper.getLatestSubmissionsByOffset(2l, "wxh", 0, 2);
		int result=submissions.size();
		
		System.out.println("最新");
		for (Submission sub : submissions) {
			System.out.println(sub.getSubmissionId());
		}
		assertEquals(2, result);
	}
	
	/**
	 * getTotalSubmissionsByUserId()
	 */
	@Ignore("finished")
	@Test
	public void getTotalSubmissionsByUserId(){
		long result=submissionMapper.getTotalSubmissionByUserId(1l);
		assertEquals(2, result);
	}
	
	@Test
	public void Test(){
		Submission submission=submissionMapper.getSubmissionById(58l);
		String baseFileName = DigestUtils.getRandomString(12, DigestUtils.Mode.ALPHA);
		try {
			preprocessor.createUseCode(submission, workBaseDirectory, baseFileName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	
	private Preprocessor preprocessor;
	@Autowired
	SubmissionMapper submissionMapper;
	
	@Autowired
	TestcaseMapper testcaseMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	LanguageMapper languageMapper;
	
	@Value("${judger.workDir}")
	private String workBaseDirectory;
}
