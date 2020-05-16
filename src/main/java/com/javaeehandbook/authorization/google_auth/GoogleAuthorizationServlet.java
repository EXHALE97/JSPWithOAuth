package com.javaeehandbook.authorization.google_auth;

import com.javaeehandbook.ApplicationConstants;
import com.javaeehandbook.authorization.LoginState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ResourceBundle;


@WebServlet(urlPatterns = {"/google.auth"})
public class GoogleAuthorizationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (LoginState.getLoginState(request)) {
            case NOT_LOGGED_IN:
                ResourceBundle bundle = ResourceBundle.getBundle("authorization");
                String uri = bundle.getString("google.uri_auth");
                String client_id = bundle.getString("google.client_id");
                String redirect_uri = bundle.getString("google.redirect_uri");
                String scope = bundle.getString("google.scope");
                String response_type = bundle.getString("google.response_type");

                String locationURI = "https://" + uri + "?" +
                        "client_id=" + client_id + "&" +
                        "redirect_uri=" + URLEncoder.encode(redirect_uri, "UTF-8") + "&" +
                        "scope=" + scope + "&" +
                        "response_type=" + response_type;

                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", locationURI);

                break;
            case LOGGED_IN:
                response.sendRedirect(request.getContextPath() + ApplicationConstants.PATH_HOME);
                break;
        }
    }
}
