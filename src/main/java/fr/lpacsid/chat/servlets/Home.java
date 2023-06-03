package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.beans.Conversation;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.db.ConversationDB;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();

        String userSession = (String) session.getAttribute("user");
        UserDB userDB = new UserDB();
        User u1;
        ArrayList<Conversation> conversations;
        try {
            u1 = userDB.readUser(userSession);
            // Récupération des conversations du user
            ConversationDB conversationDB = new ConversationDB();
            conversations = conversationDB.readAllUserConversations(u1.getLogin());
            session.setAttribute("userConversations", conversations);
        } catch (SQLException e) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
        }

        context.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext contexte = getServletContext();
        RequestDispatcher dispatcher;

        String userSession = (String) session.getAttribute("user");
        UserDB userDB = new UserDB();
        User u1 = null;
        try {
            u1 = userDB.readUser(userSession);
        } catch (SQLException e) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
        }

        // Add contact form
        String addContactForm = request.getParameter("addContact");
        if (addContactForm != null) {
            String userSearch = request.getParameter("userSearch");

            try {
                User u2 = userDB.readUser(userSearch);

                Conversation conversation = new Conversation(u1, u2);

                ConversationDB conversationDB = new ConversationDB();
                conversationDB.createConversation(conversation);
            } catch (SQLException e) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
            }

        }
        dispatcher = contexte.getRequestDispatcher("/WEB-INF/home.jsp");
        dispatcher.forward(request, response);
    }
}
