package com.javaeehandbook.authorization.github_auth;

import com.javaeehandbook.ApplicationConstants;
import com.javaeehandbook.authorization.LoginState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;


@WebServlet(urlPatterns = {"/github.auth"})
public class GithubAuthorizationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (LoginState.getLoginState(request)) {
            case NOT_LOGGED_IN:
                ResourceBundle bundle = ResourceBundle.getBundle("authorization");
                String uri = bundle.getString("github.uri_auth");
                String client_id = bundle.getString("github.client_id");
                String scope = bundle.getString("github.scope");

                String locationURI = "https://" + uri + "?" +
                        "client_id=" + client_id + "&" +
                        "scope=" + scope;

                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Accept", "application/json");
                response.setHeader("Location", locationURI);

                break;
            case LOGGED_IN:
                response.sendRedirect(request.getContextPath() + ApplicationConstants.PATH_HOME);
                break;
        }
    }
}
