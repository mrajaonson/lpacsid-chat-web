package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.beans.Conversation;
import fr.lpacsid.chat.beans.Message;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.dao.ConversationDao;
import fr.lpacsid.chat.dao.DaoFactory;
import fr.lpacsid.chat.dao.MessageDao;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home extends HttpServlet {

    private UserDao userDao;
    private ConversationDao conversationDao;
    private MessageDao messageDao;

    @Override
    public void init() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
        this.conversationDao = daoFactory.getConversationDao();
        this.messageDao = daoFactory.getMessageDao();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();

        String userSession = (String) session.getAttribute("user");
        if (userSession != null) {
            User u1;
            List<Conversation> conversations;
            try {
                u1 = userDao.readUser(userSession);
                // Récupération des conversations du user
                conversations = conversationDao.readAllUserConversations(u1.getLogin());
                session.setAttribute("userConversations", conversations);
            } catch (SQLException e) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
            }
            context.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        } else {
            context.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext contexte = getServletContext();
        RequestDispatcher dispatcher;

        String userSession = (String) session.getAttribute("user");
        User u1 = null;
        try {
            u1 = userDao.readUser(userSession);
        } catch (SQLException e) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
        }

        // Add contact form
        String addContactForm = request.getParameter("addContact");
        if (addContactForm != null) {
            String userSearch = request.getParameter("userSearch");
            try {
                User u2 = userDao.readUser(userSearch);
                Conversation conversation = new Conversation(u1, u2);
                conversationDao.createConversation(conversation);
            } catch (SQLException e) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        // Display conversation form
        String currentConversation = request.getParameter("setCurrentConversationId");
        if (currentConversation != null) {
            int currentConversationId = Integer.parseInt(currentConversation);
            session.setAttribute("currentConversationId", currentConversationId);
            Conversation currentConversationObj;
            List<Message> currentConversationMessages;
            try {
                currentConversationObj = conversationDao.readConversation(currentConversationId);
                currentConversationMessages = messageDao.readAllConversationMessages(currentConversationId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            session.setAttribute("currentConversationObj", currentConversationObj);
            session.setAttribute("currentConversationMessages", currentConversationMessages);
        }

        // Send message
        String sendMessage = request.getParameter("sendMessage");
        if (sendMessage != null) {
            String messageInput = request.getParameter("messageInput");
            Conversation currentConversationObj = (Conversation) session.getAttribute("currentConversationObj");
            assert u1 != null;
            Message message = new Message(currentConversationObj.getId(), u1.getId(), messageInput);
            List<Message> currentConversationMessages;
            try {
                messageDao.createMessage(message);
                currentConversationMessages = messageDao.readAllConversationMessages(currentConversationObj.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            session.setAttribute("currentConversationMessages", currentConversationMessages);
        }

        dispatcher = contexte.getRequestDispatcher("/WEB-INF/home.jsp");
        dispatcher.forward(request, response);
    }
}
