package com.javaeehandbook.authorization;

import com.javaeehandbook.ApplicationConstants;
import org.riversun.oauth2.google.OAuthSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OAuthSession.getInstance().clearOAuth2State(request);
        LoginState.setLoginState(request, LoginState.NOT_LOGGED_IN);
        LoginState.setSessionToken(request, null);
        response.sendRedirect(request.getContextPath() + ApplicationConstants.PATH_HOME);
        request.getSession().setAttribute("picture_url", "");
        request.getSession().setAttribute("user_name", "");
        request.getSession().setAttribute("login_display", "block");
    }
}
