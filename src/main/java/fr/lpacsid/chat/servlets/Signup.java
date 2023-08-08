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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Signup extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        context.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession();

        // Formulaire de création de compte
        String signup = request.getParameter("signup");
        if (signup != null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("passwordConfirm");

            try {
                // Vérification si username existant
                boolean userCheck = userDao.checkUsername(username);
                if (!userCheck) {
                    request.setAttribute("errorLogin", "Login non disponible");
                    dispatcher = context.getRequestDispatcher("/WEB-INF/signup.jsp");
                } else {
                    // Vérification si saisie formulaire correcte
                    if (password.equals(passwordConfirm)) {
                        User user = new User(username, password);
                        userDao.createUser(user);

                        // Vérification si User créé
                        User userDB = userDao.readUser(username);
                        if (userDB != null) {
                            session.setAttribute("userSession", userDB);
                            response.sendRedirect("Home");
                        } else {
                            request.setAttribute("errorSignup", "Erreur lors de la création du compte");
                            dispatcher = context.getRequestDispatcher("/WEB-INF/signup.jsp");
                        }
                    } else {
                        request.setAttribute("errorPassword", "Saisie confirmation mot de passe incorrecte");
                        dispatcher = context.getRequestDispatcher("/WEB-INF/signup.jsp");
                    }
                }
            } catch (SQLException e) {
                request.setAttribute("errorSignup", "Erreur lors de la création du compte");
                dispatcher = context.getRequestDispatcher("/WEB-INF/signup.jsp");
                LoggerUtility.logException(e);
            }
        }

        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
