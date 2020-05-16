package com.javaeehandbook.authorization;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = { "/addTechnology", "/deleteTechnology", "/editTechnology" })
public class AuthorizationFilter implements Filter {
    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final LoginState loginState = LoginState.getLoginState(request);

        switch (loginState) {
            case NOT_LOGGED_IN:
                request.setAttribute("errorTitle", "Authorization Error");
                request.setAttribute("errorMessage", "For changing service resources authorization required!");
                request.getRequestDispatcher("/WEB-INF/views/error_page.jsp").forward(request, response);
                break;
            case LOGGED_IN:
                filterChain.doFilter(servletRequest, servletResponse);
                break;
        }
    }

    public void destroy() {

    }
}
