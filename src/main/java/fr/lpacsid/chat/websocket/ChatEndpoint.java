package fr.lpacsid.chat.websocket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.beans.Message;
import fr.lpacsid.chat.dao.ConversationDao;
import fr.lpacsid.chat.dao.DaoFactory;
import fr.lpacsid.chat.dao.MessageDao;
import fr.lpacsid.chat.dao.UserDao;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {
    private Session session;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static final HashMap<String, User> users = new HashMap<>();

    private final UserDao userDao;
    private final MessageDao messageDao;

    public ChatEndpoint() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
        this.messageDao = daoFactory.getMessageDao();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException, SQLException {
        this.session = session;
        chatEndpoints.add(this);

        User user = this.userDao.readUser(username);
        users.put(session.getId(), user);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException, SQLException {
        message.setSender(users.get(session.getId()));
        message.initDate();

        // Persist
        this.messageDao.createMessage(message);

        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
    }

}
