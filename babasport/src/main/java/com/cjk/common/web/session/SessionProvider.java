package com.cjk.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * session 供应类
 * @author cjk
 *
 */
public interface SessionProvider {
	
	/**
	 * 往session中设置值
	 * @param name  constants 
	 * @param value 用户对象
	 * 
	 */
	public void setAttribute(HttpServletRequest request , String name , Serializable value);
	/**
	 * 从session中取值
	 * @param request
	 * @param name
	 * @return
	 */
	public Serializable getAttribute(HttpServletRequest request , String name);
	
	/**
	 * 退出登录
	 * @param request
	 */
	public void logout(HttpServletRequest request);
	
	/**
	 * 获取session ID
	 * @param request
	 * @return
	 */
	public String getSessionId(HttpServletRequest request);
	
}
