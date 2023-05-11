package fr.lpacsid.chat;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Auth extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        context.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
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

            boolean canConnect = true;

            if (canConnect) {
                dispatcher = contexte.getRequestDispatcher("/WEB-INF/home.jsp");
            } else {
                dispatcher = contexte.getRequestDispatcher("/WEB-INF/auth.jsp");
            }
        } else {
            dispatcher = contexte.getRequestDispatcher("/WEB-INF/auth.jsp");
        }
        dispatcher.forward(request, response);
    }
}
