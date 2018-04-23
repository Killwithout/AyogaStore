package com.cjk.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cjk.common.web.session.SessionProvider;
import com.cjk.core.bean.user.Buyer;

/**
 * 处理上下文
 * 处理session
 * 处理全局
 * @author cjk
 *
 */
public class SpringmvcCnterceptor implements HandlerInterceptor{
	
	@Autowired
	private SessionProvider sessionProvider;
	//设置常量
	private static final String INTERCEPTOR_URL = "/buyer/";
	
	/**
	 * 方法前
	 * 拦截buyer
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, Constants.BUYER_SESSION);
		boolean flag = false;
		String names = "";
		if(null != buyer){
			flag = true;
			names = buyer.getUsername();
		}
		request.setAttribute("isLogin",flag);
		request.setAttribute("names", names);
		
		//是否拦截
		//url:localhost:8080/buyer/login.shtml
		//uri:/buyer/login.shtml
		String requestURI = request.getRequestURI();
		if(requestURI.startsWith(INTERCEPTOR_URL)){
			//必须登录
			if(null == buyer){
				response.sendRedirect("/shopping/login.shtml?returnUrl="+request.getParameter("returnUrl"));
				return false;
			}
		}
		return true;
	}
	/**
	 * 方法后
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}
	/**
	 * 页面渲染后
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
