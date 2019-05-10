package com.itheima.springmvc.exception;

import static org.hamcrest.CoreMatchers.instanceOf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理器的自定义的实现类
 * @author SsM479丶
 *
 */
public class CustormExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj,
			Exception ex) {
		/*
		 * obj 发生异常的地方:包名+类名+方法名(形参)拼接的字符串
		 * 日志  1.发布tomcat war Eclipse
		 * 		2.发布tomcat 在公司服务器上 linux  Log4j
		 */
		ModelAndView mav = new ModelAndView();
		//判断异常类型
		if(ex instanceof MessageException) {
			//预期异常
			MessageException me = (MessageException)ex;
			mav.addObject("error", me.getMsg());
		}else{
			mav.addObject("error", "未知异常");
		}
		System.out.println(obj);
		mav.setViewName("error");
		return mav;
	}
	
}
