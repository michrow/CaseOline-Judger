package caseonline.judger.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import caseonline.judger.web.exception.ResourceNotFoundException;
import caseonline.judger.web.messenger.ApplicationEventListener;
import caseonline.judger.web.model.Submission;
import caseonline.judger.web.model.User;
import caseonline.judger.web.service.SubmissionService;
import caseonline.judger.web.service.UserService;

/**
 * 处理提交
 */
@Controller
@RequestMapping(value = "/submission")
public class SubmissionController {

	/**
	 * 显示提交页面
	 *
	 * @param testcaseId
	 *            测试用例ID
	 * @param username
	 *            用户名
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView submissionsView(
			@RequestParam(value = "testcaseId", required = false, defaultValue = "0") long testcaseId,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			HttpServletRequest request, HttpServletResponse response) {
		List<Submission> submissions = submissionService.getSubmissions(testcaseId, username,
				NUMBER_OF_SUBMISSION_PER_PAGE);
		return new ModelAndView("submissions/submissions").addObject("submissions", submissions);
	}

	/**
	 * 按条件获取所有提交信息.
	 *
	 * @param testcaseId
	 *            the testcase id
	 * @param username
	 *            the username
	 * @param startIndex
	 *            --其实提价ID
	 * @param request
	 *            the request
	 * @return the submissions action
	 */
	@RequestMapping(value = "/getSubmissions.action", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSubmissionsAction(
			@RequestParam(value = "testcaseId", required = false, defaultValue = "0") long testcaseId,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "startIndex") long startIndex, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(3, 1);
		List<Submission> submissions = submissionService.getSubmissionsWithOffset(testcaseId, username, startIndex,
				NUMBER_OF_SUBMISSION_PER_PAGE);
		result.put("isSuccessful", submissions != null && !submissions.isEmpty());
		result.put("submissions", submissions);
		return result;
	}

	/**
	 * 根据ID获取提交信息
	 *
	 * @param submissionId
	 *            the submission id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/{submissionId}", method = RequestMethod.GET)
	public ModelAndView submissionView(@PathVariable("submissionId") long submissionId, HttpServletRequest request,
			HttpServletResponse response) {
		Submission submission = submissionService.getSubmission(submissionId);
		if (submission == null) {
			throw new ResourceNotFoundException();
		}
		ModelAndView view = new ModelAndView("submissions/submission");
		view.addObject("submission", submission);
		return view;
	}

	/**
	 * 获取实时测评信息（暂不使用此中方式）
	 *
	 * @param submissionId
	 *            the submission id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the real time judge result action
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/getRealTimeJudgeResult.action")
	public SseEmitter getRealTimeJudgeResultAction(@RequestParam(value = "submissionId") long submissionId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		long userId = (Long) request.getSession().getAttribute("uid");
		User currentUser = userService.getUserById(userId);
		Submission submission = submissionService.getSubmission(submissionId);
		if (submission == null || !(submission.getUser().getUserId()==currentUser.getUserId())) {
			throw new ResourceNotFoundException();
		}
		response.addHeader("X-Accel-Buffering", "no");
		SseEmitter sseEmitter = new SseEmitter();
		submissionEventListener.addSseEmitters(submissionId, sseEmitter);
		sseEmitter.send("Established");
		return sseEmitter;
	}

	/**
	 * 根据提价ID获取提交信息（使用定时方式获取提交的跟新信息）
	 *
	 * @param submissionId
	 *            the submission id
	 * @param request
	 *            the request
	 * @return the submission action
	 */
	@RequestMapping(value = "/getSubmission.action", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSubmissionAction(
			@RequestParam(value = "submissionId") long submissionId, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(3, 1);

		Submission submission = submissionService.getSubmission(submissionId);
		result.put("isSuccessful", submission != null);
		result.put("submission", submission);

		return result;
	}

	/**
	 * 每次请求所加载评测记录的数量.
	 */
	private static final int NUMBER_OF_SUBMISSION_PER_PAGE = 2;

	/** The submission service. */
	@Autowired
	SubmissionService submissionService;

	/** The user service. */
	@Autowired
	UserService userService;

	/** The submission event listener. */
	@Autowired
	private ApplicationEventListener submissionEventListener;
}
