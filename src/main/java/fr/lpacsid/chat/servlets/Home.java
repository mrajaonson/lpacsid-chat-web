package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.beans.Chat;
import fr.lpacsid.chat.beans.Participant;
import fr.lpacsid.chat.beans.User;
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

public class Home extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        context.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext contexte = getServletContext();
        RequestDispatcher dispatcher = null;

        // Add contact form
        String addContactForm = request.getParameter("addContact");
        if (addContactForm != null) {
            String userSearch = request.getParameter("userSearch");

            UserDB userDB = new UserDB();
            try {
                User userDBSearch = userDB.readUser(userSearch);
                Participant p1 = new Participant(userDBSearch.getId(), userDBSearch.getLogin());
                Chat chat = new Chat();
                chat.addParticipant(p1);
                System.out.println("NEW " + userDBSearch.getLogin());
            } catch (SQLException e) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
            }

        }
        dispatcher = contexte.getRequestDispatcher("/WEB-INF/home.jsp");
        dispatcher.forward(request, response);
    }
}
