package caseonline.judger.web.controller;

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

import com.alibaba.fastjson.JSON;

import caseonline.judger.web.exception.ResourceNotFoundException;
import caseonline.judger.web.model.ExcellenceUsecase;
import caseonline.judger.web.model.Submission;
import caseonline.judger.web.service.SubmissionService;

/**
 * 提交请求处理.
 *
 * @author 王肖辉 2017年5月13日
 */
@Controller
@RequestMapping(value = "/admin/submission")
public class SubmissionManagerController {

	/**
	 * 显示所有提交.
	 *
	 * @param testcaseId
	 *            测试案例ID
	 * @param username
	 *            用户名
	 * @param pageNumber
	 *            页数
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/all-submissions", method = RequestMethod.GET)
	public ModelAndView allSubmissionsView(
			@RequestParam(value = "testcaseId", required = false, defaultValue = "0") long testcaseId,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "page", required = false, defaultValue = "1") long pageNumber,
			HttpServletRequest request, HttpServletResponse response) {

		// 获取总的提交量
		long totalSubmissions = submissionService.getNumberOfSubmissions(null, null);
		long latestSubmissionId = submissionService.getLatestSubmissionId();

		// 获取偏移量
		long offset = latestSubmissionId - (pageNumber >= 1 ? pageNumber : 0) * NUMBER_OF_SUBMISSIONS_PER_PAGE;

		// 按条件查询当前页的提交
		List<Submission> submissions = submissionService.getSubmissionsWithOffset(testcaseId, username, offset,
				NUMBER_OF_SUBMISSIONS_PER_PAGE);
		ModelAndView view = new ModelAndView("administration/all-submissions");
		view.addObject("testcaseId", testcaseId);
		view.addObject("username", username);
		view.addObject("currentPage", pageNumber);
		view.addObject("totalPages", (long) Math.ceil(totalSubmissions * 1.0 / NUMBER_OF_SUBMISSIONS_PER_PAGE));
		view.addObject("submissions", submissions);
		return view;
	}

	/**
	 * 显示所有优秀提交用例
	 *
	 * @param testcaseId
	 *            测试案例ID
	 * @param usecaseCoverage
	 *            覆盖率
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/excellence-submissions", method = RequestMethod.GET)
	public ModelAndView excellentSubmissionsView(
			@RequestParam(value = "testcaseId", required = false, defaultValue = "0") Long testcaseId,
			@RequestParam(value = "usecaseCoverage", required = false, defaultValue = "0") float usecaseCoverage,
			HttpServletRequest request, HttpServletResponse response) {

		List<ExcellenceUsecase> excellentSubmissions = submissionService.getExcellentUsecase(testcaseId,
				usecaseCoverage);
		ModelAndView view = new ModelAndView("administration/excellence-submissions");
		view.addObject("exceSubmissions", excellentSubmissions);
		return view;
	}

	/**
	 * Delete submissions action.
	 *
	 * @param submissions
	 *            the submissions
	 * @param request
	 *            the request
	 * @return the map
	 */
	@RequestMapping(value = "/deleteSubmissions.action", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteSubmissionsAction(
			@RequestParam(value = "submissions") String submissions, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Long> submissionList = JSON.parseArray(submissions, Long.class);
		submissionService.deleteBatchSubmission(submissionList);
		result.put("isSuccessful", true);
		return result;
	}

	/**
	 * 更新提交
	 *
	 * @param submissionId
	 *            the submission id
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@RequestMapping(value = "/detail-submission/{submissionId}", method = RequestMethod.GET)
	public ModelAndView editSubmissionView(@PathVariable(value = "submissionId") long submissionId,
			HttpServletRequest request, HttpServletResponse response) {
		Submission submission = submissionService.getSubmission(submissionId);
		if (submission == null) {
			throw new ResourceNotFoundException();
		}
		ModelAndView view = new ModelAndView("administration/edit-submission");
		view.addObject("submission", submission);
		return view;
	}

	/** The number of submissions per page. */
	private final int NUMBER_OF_SUBMISSIONS_PER_PAGE = 2;
	/** The submission service. */
	@Autowired
	SubmissionService submissionService;
}
