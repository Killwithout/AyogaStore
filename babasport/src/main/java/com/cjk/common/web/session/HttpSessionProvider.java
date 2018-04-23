package com.cjk.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 本地session
 * @author cjk
 *
 */
public class HttpSessionProvider implements SessionProvider{

	public void setAttribute(HttpServletRequest request, String name, Serializable value) {
		//默认true,表示request中有session,就用，如果没有新创建一个session 通过 Cookie JSESSIONID来判断有没有session
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

	public Serializable getAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if(session != null){
			return (Serializable) session.getAttribute(name);
		}
		return null;
	}

	public void logout(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}
		//Cookie JSESSIONID
		
	}

	public String getSessionId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//request.getRequestedSessionId(); //获取url上的sessionID
		return request.getSession().getId();
	}

}
