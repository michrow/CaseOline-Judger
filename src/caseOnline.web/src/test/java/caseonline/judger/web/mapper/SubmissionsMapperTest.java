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

import caseonline.judger.web.model.Language;
import caseonline.judger.web.model.Submission;
import caseonline.judger.web.model.Testcase;
import caseonline.judger.web.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class SubmissionsMapperTest {

	/**
	 * deleteSubmission()
	 */
	//@Ignore("finished")
	@Test
	public void testDeleteSubmission() {
		int result = submissionMapper.deleteSubmission(63l);
		assertEquals(1, result);
	}

	/**
	 * createSubmission
	 */
	//@Ignore("finished")
	@Test
	public void testCreateSubmission() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		Testcase testcase=testcaseMapper.getTestcaseById(1l);
		Language language=languageMapper.getLanguageById(1);
		User user=userMapper.getUserById(1l);
		Submission submissions = new Submission(testcase,user,language,date,"test",0);
		int result = submissionMapper.createSubmission(submissions);
		assertEquals(1, result);
	}

	/**
	 * updateSubmission
	 */
	//@Ignore("finished")
	@Test
	public void testUpdateSubmission() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		Submission submission = submissionMapper.getSubmissionById(18l);
		submission.setSubmissionSubmitTime(date);
		int result = submissionMapper.updateSubmission(submission);
		assertEquals(1, result);
	}

	/**
	 * getSubmissionByLanguageId()
	 */
	//@Ignore("finished")
	@Test
	public void testGetSubmissionByLanguageId() {
		List<Submission> submissions = submissionMapper.getSubmissionsByLanguageId(1);
		assertNotNull(submissions);

	}

	/**
	 * getSubmissionBySubmissionId()
	 */
//	@Ignore("finished")
	@Test
	public void testGetSubmissionBySubmissionId() {
		Submission submission = submissionMapper.getSubmissionById(1l);
		assertNotNull(submission);
	}

	/**
	 * getTotalSubmissionsByUserId()
	 */
//	@Ignore("finished")
	@Test
	public void getTotalSubmissionsByUserId() {
		long result = submissionMapper.getTotalSubmissionByUserId(1l);
		assertEquals(66, result);
	}

	/**
	 * getSubmissionsByTestcaseIdAndUserId()
	 */
	//@Ignore("finished")
	@Test
	public void testGetSubmissionsByTestcaseIdAndUserId() {
		List<Submission> submission = submissionMapper.getSubmissionsByTcIdOrUId(1l, "admin5", 10);
		int result = submission.size();
		assertEquals(10, result);
	}

	/**
	 * getSubmissionsByOffset()
	 */
//	@Ignore("finished")
	@Test
	public void testGetSubmissionsByOffset() {
		List<Submission> submissions = submissionMapper.getSubmissionsByOffset(1l, "admin5", 0, 15);
		int result = submissions.size();
		assertEquals(15, result);
	}

	/**
	 * getLatestSubmissionsByOffset
	 */
	//@Ignore("finished")
	@Test
	public void testGetLatestSubmissionsByOffset() {
		List<Submission> submissions = submissionMapper.getLatestSubmissionsByOffset(1l, "admin5", 0, 15);
		int result = submissions.size();
		assertEquals(15, result);
	}
	//@Ignore
	@Test
	public void testGetNumberOfSubmissions() {
		String startTimeString = "2017-05-27 00:00:00";
		String endTimeString = "2017-05-28 23:59:59";
		long submissions = submissionMapper.getNumberOfSubmissions(startTimeString, endTimeString);
		assertEquals(6, submissions);

	}
	//@Ignore
	@Test
	public void testGetLatestSubmissionId(){
		long latestId=submissionMapper.getLatestSubmissionId();
		assertEquals(69, latestId);
	}
	@Autowired
	SubmissionMapper submissionMapper;
	@Autowired
	TestcaseMapper testcaseMapper;
	@Autowired
	LanguageMapper languageMapper;
	@Autowired
	UserMapper userMapper;
}
