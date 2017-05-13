package caseonline.judger.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 缺省controller 处理一些公共请求
 * @author 王肖辉
 * 2017年4月9日
 */
@Controller
@RequestMapping(value="/")
public class DefaultController {
	
	/**
	 * 显示首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView indexView(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view=new ModelAndView("index");
		return view;
	}
}
