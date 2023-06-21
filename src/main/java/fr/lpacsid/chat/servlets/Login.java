package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.dao.DaoFactory;
import fr.lpacsid.chat.dao.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();

        User userSession = (User) session.getAttribute("userSession");

        if (userSession != null) {
            response.sendRedirect("Home");
        } else {
            context.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext contexte = getServletContext();
        RequestDispatcher dispatcher = null;

        // Auth form
        try {
            String authForm = request.getParameter("login");

            if (authForm != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                boolean canConnect = userDao.validateUser(username, password);

                if (canConnect) {
                    User user = userDao.readUser(username);
                    session.setAttribute("userSession", user);
                    response.sendRedirect("Home");
                } else {
                    dispatcher = contexte.getRequestDispatcher("/WEB-INF/login.jsp");
                }
            } else {
                dispatcher = contexte.getRequestDispatcher("/WEB-INF/login.jsp");
            }
        } catch (SQLException e) {
            Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, e);
        }

        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
