package fr.lpacsid.chat.servlets;

import fr.lpacsid.chat.beans.Message;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.dao.ConversationDao;
import fr.lpacsid.chat.dao.DaoFactory;
import fr.lpacsid.chat.dao.MessageDao;
import fr.lpacsid.chat.dao.UserDao;
import fr.lpacsid.chat.enums.ConversationTypes;
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

public class Conversation extends HttpServlet {
    private UserDao userDao;
    private ConversationDao conversationDao;
    private MessageDao messageDao;

    private HttpServletRequest request;
    private HttpSession session;
    private User user;

    @Override
    public void init() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
        this.conversationDao = daoFactory.getConversationDao();
        this.messageDao = daoFactory.getMessageDao();
    }

    /**
     * Affiche un log de l'exception
     * @param e Exception
     */
    public void logException(Exception e) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.request = request;
            this.session = request.getSession();
            ServletContext context = getServletContext();

            // Redirection si la session utilisateur est expirée
            this.redirectNullSession(request, response);

            this.setUserConversationsInSession();

            // Get users list
            List<User> users = userDao.readAllUsers(this.user.getId());
            session.setAttribute("users", users);

            context.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            logException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.request = request;
            this.session = request.getSession();
            ServletContext context = getServletContext();
            RequestDispatcher requestDispatcher;

            // Redirection si la session utilisateur est expirée
            this.redirectNullSession(request, response);

            this.createChannelPost();
            this.createGroupPost();
            this.createDiscussionPost();

            this.setCurrentConversationInSession();

            requestDispatcher = context.getRequestDispatcher("/WEB-INF/home.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            logException(e);
        }
    }

    /**
     * Redirection si la session utiliateur est expirée
     */
    private void redirectNullSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        this.session = request.getSession();
        this.user = (User) this.session.getAttribute("userSession");
        if (user == null) {
            context.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
        }
    }

    /**
     * Met en session la liste des conversations de l'utilisateur
     */
    private void setUserConversationsInSession() {
        try {
            List<fr.lpacsid.chat.beans.Conversation> conversations = conversationDao.readAllUserConversations(user.getId());
            session.setAttribute("userConversations", conversations);
        } catch (SQLException e) {
            logException(e);
        }
    }

    /**
     * Permet de créer une conversation
     =    * @param participants Liste des participants
     * @param type Type de conversation
     */
    private void createConversation(List<String> participants, ConversationTypes type) {
        try {
            if (!participants.isEmpty()) {
                fr.lpacsid.chat.beans.Conversation conversation = new fr.lpacsid.chat.beans.Conversation(user, type);
                for (String userId : participants) {
                    User userParticipant = userDao.readUserById(Integer.valueOf(userId));
                    conversation.addParticipant(userParticipant);
                }
                conversationDao.createConversation(conversation);
            }
        } catch (SQLException e) {
            logException(e);
        }
    }

    private void createChannelPost() {
        String createChannelForm = request.getParameter("createChannel");
        if (createChannelForm != null) {
            List<String> channelSelectedUsers = List.of(request.getParameterValues("channelSelectedUsers"));
            createConversation(channelSelectedUsers, ConversationTypes.CHANNEL);
            setUserConversationsInSession();
        }
    }

    private void createGroupPost() {
        String createGroupForm = request.getParameter("createGroup");
        if (createGroupForm != null) {
            List<String> groupSelectedUsers = List.of(request.getParameterValues("groupSelectedUsers"));
            createConversation(groupSelectedUsers, ConversationTypes.GROUP);
            setUserConversationsInSession();
        }
    }

    private void createDiscussionPost() {
        String createDiscussionForm = request.getParameter("createDiscussion");
        if (createDiscussionForm != null) {
            List<String> selectedParticipant = List.of(request.getParameter("selectedParticipant"));
            createConversation(selectedParticipant, ConversationTypes.DISCUSSION);
            setUserConversationsInSession();
        }
    }

    private void setCurrentConversationInSession() throws SQLException {
        String currentConversationForm = request.getParameter("setCurrentConversationId");
        if (currentConversationForm != null) {
            int currentConversationId = Integer.parseInt(currentConversationForm);
            // Get all conversation messages
            List<Message> currentConversationMessages = messageDao.readAllConversationMessages(currentConversationId);

            // Get conversation
            fr.lpacsid.chat.beans.Conversation currentConversationObj = conversationDao.readConversation(currentConversationId);
            currentConversationObj.setMessages(currentConversationMessages);
            currentConversationObj.setDiscussionLabel(this.user.getId());

            // Set current conversation to session
            session.setAttribute("currentConversation", currentConversationObj);
        }
    }
}
