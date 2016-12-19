package com.personal.web.interceptor;

import com.personal.Constants;
import com.personal.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
