package caseonline.judger.web.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import caseonline.judger.web.model.User;
import caseonline.judger.web.service.UserService;

@Aspect
public class ViewAspect {
	@Around(value = "execution(* caseonline.judger.web.controller.*.*View(..)) && args(.., request, response)")
	public ModelAndView getUserProfile(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		ModelAndView view = null;
		HttpSession session = request.getSession();

		view = (ModelAndView) proceedingJoinPoint.proceed();
		boolean isLoggedIn = isLoggedIn(session);
		if (isLoggedIn) {
			long userId = (Long) session.getAttribute("uid");
			User user = userService.getUserById(userId);

			view.addObject("isLogin", isLoggedIn).addObject("myProfile", user);
			// .addObject("mySubmissionStats",
			// submissionService.getSubmissionStatsOfUser(userId));
		}
		return view;
	}

	/**
	 * 检查用户是否已经登录.
	 * 
	 * @param session
	 *            - HttpSession 对象
	 * @return 用户是否已经登录
	 */
	private boolean isLoggedIn(HttpSession session) {
		Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
		if (isLoggedIn == null || !isLoggedIn.booleanValue()) {
			return false;
		}
		return true;
	}

	@Autowired
	UserService userService;

}
