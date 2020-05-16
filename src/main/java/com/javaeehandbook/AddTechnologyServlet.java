package com.javaeehandbook;

import com.javaeehandbook.bean.JavaEETechnology;
import com.javaeehandbook.dao.TechnologyDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/addTechnology"})
public class AddTechnologyServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ViewTechnologiesServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/add_technology.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JavaEETechnology tech = new JavaEETechnology();
        tech.setName(request.getParameter("name"));
        tech.setVersionForJava4(request.getParameter("versionForJava4"));
        tech.setVersionForJava5(request.getParameter("versionForJava5"));
        tech.setVersionForJava6(request.getParameter("versionForJava6"));
        tech.setVersionForJava7(request.getParameter("versionForJava7"));
        tech.setVersionForJava8(request.getParameter("versionForJava8"));
        tech.setDescription(request.getParameter("description"));

        String errorMessage = null;
        try {
            TechnologyDao.addTechnology(tech);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            errorMessage = e.getMessage();
        }

        request.setAttribute("errorMessage", errorMessage);

        if (errorMessage == null) {
            response.sendRedirect(request.getContextPath() + ApplicationConstants.PATH_TECHNOLOGIES);
        } else {
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/add_technology.jsp").forward(request, response);
        }
    }
}
