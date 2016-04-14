package com.personal.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.personal.Constants;
import com.personal.entity.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		if (user == null) {
			String loginPath = request.getContextPath() + "/user/login";
			response.sendRedirect(loginPath);
			return false;
		}
		return true;
	}

}
