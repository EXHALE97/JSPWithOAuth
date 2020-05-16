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

@WebServlet(urlPatterns = { "/editTechnology"})
public class EditTechnologyServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ViewTechnologiesServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JavaEETechnology technology = null;
        String errorMessage = null;
        String id = request.getParameter("id");

        try {
            technology = TechnologyDao.getTechnologyById(Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            errorMessage = e.getMessage();
        }

        if (errorMessage != null) {
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/view_technologies.jsp").forward(request, response);
        }

        request.setAttribute("t", technology);
        request.setAttribute("errorMessage", errorMessage);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/edit_technology.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JavaEETechnology technology = new JavaEETechnology();
        technology.setId(Integer.valueOf(request.getParameter("id")));
        technology.setName(request.getParameter("name"));
        technology.setVersionForJava4(request.getParameter("versionForJava4"));
        technology.setVersionForJava5(request.getParameter("versionForJava5"));
        technology.setVersionForJava6(request.getParameter("versionForJava6"));
        technology.setVersionForJava7(request.getParameter("versionForJava7"));
        technology.setVersionForJava8(request.getParameter("versionForJava8"));
        technology.setDescription(request.getParameter("description"));

        String errorMessage = null;
        try {
            TechnologyDao.updateTechnology(technology);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            errorMessage = e.getMessage();
        }

        request.setAttribute("technology", technology);
        request.setAttribute("errorMessage", errorMessage);

        if (errorMessage == null) {
            response.sendRedirect(request.getContextPath() + ApplicationConstants.PATH_TECHNOLOGIES);
        } else {
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/edit_technology.jsp").forward(request, response);
        }
    }
}
