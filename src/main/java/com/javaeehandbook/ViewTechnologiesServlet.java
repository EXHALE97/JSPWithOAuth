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
import java.util.List;

@WebServlet(urlPatterns = {"/viewTechnologies"})
public class ViewTechnologiesServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ViewTechnologiesServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<JavaEETechnology> list = null;
        String errorMessage = null;

        try {
            list = TechnologyDao.getAllTechnologies();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            errorMessage = e.getMessage();
        }

        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("list", list);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/view_technologies.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
