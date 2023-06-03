package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.db.UserDB;
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

public class Auth extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();

        String userSession = (String) session.getAttribute("user");

        if (userSession != null) {
            response.sendRedirect("Home");
        } else {
            context.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext contexte = getServletContext();
        RequestDispatcher dispatcher = null;

        // Auth form
        String authForm = request.getParameter("auth");
        if (authForm != null) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            UserDB userDB = new UserDB();
            boolean canConnect = false;
            try {
                canConnect = userDB.validateUser(login, password);
            } catch (SQLException e) {
                Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, e);
            }

            if (canConnect) {
                session.setAttribute("user", login);
                response.sendRedirect("Home");
            } else {
                dispatcher = contexte.getRequestDispatcher("/WEB-INF/auth.jsp");
            }
        } else {
            dispatcher = contexte.getRequestDispatcher("/WEB-INF/auth.jsp");
        }
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
