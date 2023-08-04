package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.beans.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Home extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Conversation");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher;

        User userSession = (User) session.getAttribute("userSession");

        // Redirect if not connected
        if (userSession == null) {
            context.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
        }

        // Logout
        String logout = request.getParameter("logout");
        if ("logout".equals(logout)) {
            session.setAttribute("userSession", null);
            session.setAttribute("userConversations", null);
            session.setAttribute("currentConversation", null);
            context.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
        }

        dispatcher = context.getRequestDispatcher("/WEB-INF/home.jsp");
        dispatcher.forward(request, response);
    }
}
