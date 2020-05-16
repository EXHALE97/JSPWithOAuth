package com.javaeehandbook.authorization;

import com.javaeehandbook.ApplicationConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public enum LoginState {
    NOT_LOGGED_IN,
    LOGGED_IN;

    public static LoginState getLoginState(HttpServletRequest request) {
        final HttpSession session = request.getSession();

        LoginState loginState = (LoginState) session.getAttribute(ApplicationConstants.SESSION_LOGIN_STATE);
        if (loginState == null) {
            loginState = NOT_LOGGED_IN;
        }

        return loginState;
    }

    public static void setLoginState(HttpServletRequest request, LoginState loginState) {
        final HttpSession session = request.getSession();
        session.setAttribute(ApplicationConstants.SESSION_LOGIN_STATE, loginState);
    }

    public static String getSessionToken(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(ApplicationConstants.SESSION_TOKEN);

        return token == null ? "" : token;
    }

    public static void setSessionToken(HttpServletRequest request, String token) {
        final HttpSession session = request.getSession();
        session.setAttribute(ApplicationConstants.SESSION_TOKEN, token);
    }
}
