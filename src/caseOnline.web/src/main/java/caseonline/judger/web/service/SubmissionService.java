package caseonline.judger.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caseonline.judger.web.mapper.LanguageMapper;
import caseonline.judger.web.mapper.SubmissionMapper;
import caseonline.judger.web.mapper.TestcaseMapper;
import caseonline.judger.web.messenger.MessageSender;
import caseonline.judger.web.model.Language;
import caseonline.judger.web.model.Submission;
import caseonline.judger.web.model.Testcase;
import caseonline.judger.web.model.User;
import caseonline.judger.web.util.DateUtil;
import caseonline.judger.web.util.ProductUsecase;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmissionService.
 */
@Service
public class SubmissionService {

	/**
	 * 新增提交.
	 *
	 * @param 当前提交用户
	 * @param 测试案例ID
	 * @param 语言ID
	 * @param 原始的用例
	 * @return the map
	 */
	public Map<String, Object> addSubmission(User currentUser, long testcaseId, int languageId,
			String originUsecase) {
		Map<String, Boolean> Verifyresult = SubmissionParamVerify(currentUser, testcaseId, languageId, originUsecase);
		Map<String,Object> result=new HashMap<>();
		if (Verifyresult.get("isSuccessful")) {
			Map<String, Object> createResult = ProductUsecase.createUsecase(originUsecase);
			if ((boolean) createResult.get("isSuccessful")) {
				String usecase = (String) createResult.get("usecase");
				int usecaseSize = (int) createResult.get("size");
				Testcase testcase = testcaseMapper.getTestcaseById(testcaseId);
				Date SubmissionTime = DateUtil.getDate();
				Language language = languageMapper.getLanguageById(languageId);
				Submission submission = new Submission(testcase, currentUser, language, SubmissionTime, usecase,
						usecaseSize);
				submissionMapper.createSubmission(submission);
				long submissionId = submission.getSubmissionId();
				createSubmissionTask(submissionId);
				result.put("submissionId", submissionId);
			}
		}
	
		result.put("isSuccessful", true);
		return result;
	}

	/**
	 * 创建提交任务.
	 *
	 * @param submissionId the submission id
	 */
	private void createSubmissionTask(long submissionId) {
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("event", "SubmissionCreated");
		mapMessage.put("submissionId", submissionId);
		messageSender.sendMessage(mapMessage);
	}

	/**
	 * 删除提交.
	 * 本方法可能用不到
	 * @param submissionId the submission id
	 * @return the int
	 */
	public int deleteSubmission(long submissionId){
		return submissionMapper.deleteSubmission(submissionId);
	}
	
	/**
	 * 编辑提交.
	 *
	 * @param submission the submission
	 * @return the int
	 */
	public int editSubmission(Submission submission){
		return submissionMapper.updateSubmission(submission);
	}
	
	/**
	 * 批量删除.
	 *
	 * @param submissionIds the submission ids
	 * @return the map
	 */
	public Map<Long, Boolean> deleteBatchSubmission(List<Long> submissionIds){
		Map<Long, Boolean> result=new HashMap<>();
		for (Long submissionId : submissionIds) {
			boolean success=submissionMapper.deleteSubmission(submissionId)==1?true:false;
			result.put(submissionId, success);
		}
		return result;
	}
	
	
	/**
	 * 根据提交ID获取提交对象.
	 *
	 * @param 提交ID
	 * @return the submission
	 */
	public Submission getSubmission(long submissionId) {
		return submissionMapper.getSubmissionById(submissionId);
	}
	
	/**
	 * 根据用户ID获取提交对象.
	 *
	 * @param userId the user id
	 * @return the submissions by user id
	 */
	public List<Submission> getSubmissionsByUserId(long userId) {
		return submissionMapper.getLatestSubmissionsOfUser(userId);
	}
	
	/**
	 * 获取某一用户的提交总量.
	 *
	 * @param userId the user id
	 * @return the total submission by user id
	 */
	public long getTotalSubmissionByUserId(long userId){
		return submissionMapper.getTotalSubmissionByUserId(userId);
	}
	
	
	/**
	 * 获取某一语言的案例提交的总量.
	 *
	 * @param languageId the language id
	 * @return the submissions by language id
	 */
	public List<Submission> getSubmissionsByLanguageId(int languageId){
		return submissionMapper.getSubmissionsByLanguageId(languageId);
	}
	
	
	/**
	 * 获取提交.
	 *
	 * @param --测试案例ID
	 * @param --用户名
	 * @param --每次查询量
	 * @return the submissions
	 */
	public List<Submission> getSubmissions(long testcaseId, String userName, int limit) {
		return submissionMapper.getSubmissionsByTcIdOrUId(testcaseId, userName, limit);
	}

	/**
	 * 根据偏移量获取提交.
	 *
	 * @param --测试案例ID
	 * @param --用户名
	 * @param --偏移量
	 * @param --每次查询量
	 * @return the submissions with offset
	 */
	public List<Submission> getSubmissionsWithOffset(long testcaseId, String userName, long offset, int limit) {
		List<Submission> submissions = submissionMapper.getSubmissionsByOffset(testcaseId, userName, offset, limit);
		return submissions;
	}
	
	public long getNumberOfSubmissions(Date startTime, Date endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String startTimeString = "0000-00-00 00:00:00";
		String endTimeString = "9999-12-31 23:59:59";
		
		if ( startTime != null ) {
			startTimeString = sdf.format(startTime);
		}
		if ( endTime != null ) {
			endTimeString = sdf.format(endTime);
		}
		return submissionMapper.getNumberOfSubmissions(startTimeString, endTimeString);
	}
	/**
	 * 根据偏移量获取最新提交.
	 *
	 * @param --测试案例ID
	 * @param --用户名
	 * @param --偏移量
	 * @param 每次查询量
	 * @return the latest submissions with offset
	 */
	public List<Submission> getLatestSubmissionsWithOffset(long testcaseId, String userName, long offset, int limit) {
		List<Submission> submissions = submissionMapper.getLatestSubmissionsByOffset(testcaseId, userName, offset, limit);
		return submissions;
	}

	/**
	 * 提交验证.
	 *
	 * @param --提交用户
	 * @param --测试案例ID 
	 * @param --语言ID
	 * @param --原始测试用例
	 * @return the map
	 */
	private Map<String, Boolean> SubmissionParamVerify(User currentUser, long testcaseId, int languageId,
			String originUsecase) {
		Map<String, Boolean> result=new HashMap<>();
		result.put("isUserNull", currentUser==null);
		result.put("isTestcaseNull",testcaseMapper.getTestcaseById(testcaseId)==null);
		result.put("isLangugageNull",languageMapper.getLanguageById(languageId)==null);
		result.put("isUsecaseNull",originUsecase.isEmpty());
		
		if (!result.get("isUserNull")&& !result.get("isTestcaseNull")
				&&!result.get("isLangugageNull")&&!result.get("isUsecaseNull")) {
			result.put("isSuccessful",true);
		}
		return result;
	}

	/** The submission mapper. */
	@Autowired
	SubmissionMapper submissionMapper;

	/** The language mapper. */
	@Autowired
	LanguageMapper languageMapper;

	/** The testcase mapper. */
	@Autowired
	TestcaseMapper testcaseMapper;

	@Autowired
	MessageSender messageSender;
	
	public long getLatestSubmissionId() {
		return submissionMapper.getLatestSubmissionId();
	}

	
}
