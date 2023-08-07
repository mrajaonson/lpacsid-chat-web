package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.dao.DaoFactory;
import fr.lpacsid.chat.dao.UserDao;
import fr.lpacsid.chat.utils.LoggerUtility;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

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
                    // Récupération du user
                    User user = userDao.readUser(username);
                    // Actualisation de la date de dernière connexion
                    user.initLastConnection();
                    // Mise à jour du user
                    userDao.updateUser(user);
                    // Mise en session du user
                    session.setAttribute("userSession", user);
                    response.sendRedirect("Home");
                } else {
                    request.setAttribute("errorLogin", "Login et/ou mot de passe incorrecte");
                    dispatcher = contexte.getRequestDispatcher("/WEB-INF/login.jsp");
                }
            } else {
                dispatcher = contexte.getRequestDispatcher("/WEB-INF/login.jsp");
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        }

        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
