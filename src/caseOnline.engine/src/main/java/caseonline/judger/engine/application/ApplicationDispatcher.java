package caseonline.judger.engine.application;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import caseonline.judger.engine.core.Dispatcher;
import caseonline.judger.engine.mapper.JudgeResultMapper;
import caseonline.judger.engine.mapper.SubmissionMapper;
import caseonline.judger.engine.messenger.MessageSender;
import caseonline.judger.engine.model.Submission;

/**
 * 消息处理器.
 *
 * @author 王肖辉 2017年3月19日
 */
@Component
public class ApplicationDispatcher {

	/**
	 * 处理新的提交被创建.
	 *
	 * @param submissionId
	 *            the submission id
	 */
	public void onSubmissionCreated(long submissionId) {
		try {
			judgerDispatcher.createNewTask(submissionId);
		} catch (Exception ex) {
			LOGGER.catching(ex);
		}
	}

	/**
	 * 处理错误.
	 *
	 * @param submissionId
	 *            the submission id
	 */
	public void onErrorOccurred(long submissionId) {
		updateSubmission(submissionId, "SE", "system error");
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("event", "ErrorOccurred");
		mapMessage.put("submissionId", submissionId);
		messageSender.sendMessage(mapMessage);
	}

	/**
	 * 处理临时文件创建完成.
	 *
	 * @param submissionId
	 *            the submission id
	 * @param result
	 *            the result
	 */
	public void onFileCreateFinished(Long submissionId, Map<String, Object> result) {

		boolean isSuccessful = (Boolean) result.get("isSuccessful");
		String log = (String) result.get("log");

		if (!isSuccessful) {
			updateSubmission(submissionId, "CFE", log);
		}
		updateSubmission(submissionId, "PD", log);
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("event", "FileCreateFinished");
		mapMessage.put("submissionId", submissionId);
		mapMessage.put("isSuccessful", isSuccessful);
		mapMessage.put("log", log);
		messageSender.sendMessage(mapMessage);
	}

	/**
	 * 构建完成.
	 *
	 * @param submissionId
	 *            the submission id
	 * @param result
	 *            the result
	 */
	public void onRunBuildFinished(Long submissionId, Map<String, Object> result) {

		boolean isSuccessful = (Boolean) result.get("isSuccessful");
		String log = (String) result.get("log");
		if (!isSuccessful) {
			updateSubmission(submissionId, "AE", log);
		}
		updateSubmission(submissionId, "PD", log);
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("event", "RunBuildFinished");
		mapMessage.put("submissionId", submissionId);
		mapMessage.put("isSuccessful", isSuccessful);
		mapMessage.put("log", log);
		messageSender.sendMessage(mapMessage);
	}

	/**
	 * 覆盖率解析完成.
	 *
	 * @param submissionId
	 *            the submission id
	 * @param result
	 *            the result
	 */
	public void onCoverageParseFinished(Long submissionId, Map<String, Object> result) {

		boolean isSuccessful = (Boolean) result.get("isSuccessful");
		String log = (String) result.get("log");
		if (!isSuccessful) {
			updateSubmission(submissionId, "PHE", log);
		}
		updateSubmission(submissionId, "FJ", log);
		String coverageInfo = (String) result.get("coverageInfo");
		Map<String, Object> mapMessage = new HashMap<String, Object>();
		mapMessage.put("event", "CoverageParseFinished");
		mapMessage.put("submissionId", submissionId);
		mapMessage.put("isSuccessful", isSuccessful);
		mapMessage.put("log", log);
		mapMessage.put("coverageInfo", coverageInfo);
		messageSender.sendMessage(mapMessage);
	}

	/**
	 * 跟新提交.
	 *
	 * @param submissionId
	 *            --提交ID
	 * @param judgeResult
	 *            --执行结果
	 * @param log
	 *            --执行日志
	 */
	private void updateSubmission(long submissionId, String judgeResult, String log) {
		Submission submission = submissionMapper.getSubmissionById(submissionId);
		submission.setJudgeResult(judgeResultMapper.getJudgeResultByFlag(judgeResult));
		String Log = log;
		if (submission.getJudgeLog() != null) {
			Log = submission.getJudgeLog() + log;
		}
		submission.setJudgeLog(Log);
		submissionMapper.updateSubmission(submission);
	}

	/** The judger dispatcher. */
	@Autowired
	private Dispatcher judgerDispatcher;

	/** The message sender. */
	@Autowired
	private MessageSender messageSender;

	/** The submission mapper. */
	@Autowired
	private SubmissionMapper submissionMapper;

	/** The judge result mapper. */
	@Autowired

	private JudgeResultMapper judgeResultMapper;
	/**
	 * 日志记录器.
	 */
	private static final Logger LOGGER = LogManager.getLogger(ApplicationDispatcher.class);
}
