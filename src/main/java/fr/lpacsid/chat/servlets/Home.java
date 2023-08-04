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
        response.sendRedirect("Conversation");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher;

            User userSession = (User) session.getAttribute("userSession");

            // Redirect if not connected
            if (userSession == null) {
                context.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
            }

            // Display conversation form
            String currentConversationForm = request.getParameter("setCurrentConversationId");
            if (currentConversationForm != null) {
                int currentConversationId = Integer.parseInt(currentConversationForm);
                // Get all conversation messages
                List<Message> currentConversationMessages = messageDao.readAllConversationMessages(currentConversationId);

                // Get conversation
                Conversation currentConversationObj = conversationDao.readConversation(currentConversationId);
                currentConversationObj.setMessages(currentConversationMessages);
                assert userSession != null;
                currentConversationObj.setDiscussionLabel(userSession.getId());

                // Set current conversation to session
                session.setAttribute("currentConversation", currentConversationObj);
            }

            // Send message
            String sendMessage = request.getParameter("sendMessage");
            if (sendMessage != null) {
                String messageInput = request.getParameter("messageInput");

                // Get the current conversation
                Conversation currentConversationObj = (Conversation) session.getAttribute("currentConversation");

                assert userSession != null;
                if (!messageInput.isEmpty()) {
                    Message message = new Message(currentConversationObj.getId(), userSession, messageInput);
                    messageDao.createMessage(message);

                    // Refresh messages list
                    List<Message> currentConversationMessages = messageDao.readAllConversationMessages(currentConversationObj.getId());
                    currentConversationObj.setMessages(currentConversationMessages);
                    session.setAttribute("currentConversation", currentConversationObj);
                }
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

        } catch (SQLException e) {
            logException(e);
        }
    }

    /**
     * Affiche un log de l'exception
     * @param e Exception
     */
    public void logException(Exception e) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
    }
}
