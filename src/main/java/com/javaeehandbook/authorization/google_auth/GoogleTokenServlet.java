package com.javaeehandbook.authorization.google_auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.javaeehandbook.ApplicationConstants;
import com.javaeehandbook.authorization.LoginState;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;


@WebServlet(urlPatterns = {"/google.token"})
public class GoogleTokenServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GoogleTokenServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (LoginState.getLoginState(request)) {
            case NOT_LOGGED_IN:
                try {
                    getAccessToken(request);
                    getUserInfo(request);
                    response.sendRedirect(request.getContextPath() + ApplicationConstants.PATH_HOME);

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
                break;
            case LOGGED_IN:
                response.sendRedirect(request.getContextPath() + ApplicationConstants.PATH_HOME);
                break;
        }
    }

    private JsonObject getTokenJson(String destination, String message) throws IOException {
        HttpPost apiRequest;
        ByteArrayOutputStream stream;

        apiRequest = new HttpPost(destination);
        apiRequest.setEntity(new StringEntity(message, ContentType.create("application/x-www-form-urlencoded")));

        stream = new ByteArrayOutputStream();
        new DefaultHttpClient().execute(apiRequest).getEntity().writeTo(stream);
        JsonObject obj = new JsonParser().parse(stream.toString()).getAsJsonObject();
        stream.close();

        return obj;
    }

    private void getAccessToken(HttpServletRequest request) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("authorization");

        final String destination = bundle.getString("google.uri_token");
        String message = "client_id=" + bundle.getString("google.client_id") + "&" +
                "client_secret=" + bundle.getString("google.client_secret") + "&" +
                "redirect_uri=" + bundle.getString("google.redirect_uri") + "&" +
                "grant_type=authorization_code" + "&" +
                "code=" + request.getParameter("code");

        JsonObject tokenJson = getTokenJson(destination, message);
        final JsonElement access_token = tokenJson.get("access_token");
        if(access_token.isJsonNull()) {
            final JsonElement error_description = tokenJson.get("error_description");
            log.error(error_description);
            throw new Exception(error_description.toString());
        } else {
            String token = access_token.getAsString();
            LoginState.setLoginState(request, LoginState.LOGGED_IN);
            LoginState.setSessionToken(request, token);
        }
    }

    private void getUserInfo(HttpServletRequest request) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("authorization");

        final String requestUrl = bundle.getString("google.user_info_url") + "?" +
                "alt=json" + "&" +
                "access_token=" + LoginState.getSessionToken(request);
        JsonObject userInfo = getUserJson(requestUrl);

        setUserInfoOnMenu(request, userInfo);
    }

    private void setUserInfoOnMenu(HttpServletRequest request, JsonObject userInfo) {
        request.getSession().setAttribute("user_name", userInfo.get("name").getAsString());
        request.getSession().setAttribute("picture_url", userInfo.get("picture").getAsString());
        request.getSession().setAttribute("login_display", "none");
    }

    private JsonObject getUserJson(String requestUrl) throws IOException {
        HttpGet apiRequest;
        ByteArrayOutputStream stream;

        apiRequest = new HttpGet(requestUrl);

        stream = new ByteArrayOutputStream();
        new DefaultHttpClient().execute(apiRequest).getEntity().writeTo(stream);
        JsonObject obj = new JsonParser().parse(stream.toString()).getAsJsonObject();
        stream.close();

        return obj;
    }
}
