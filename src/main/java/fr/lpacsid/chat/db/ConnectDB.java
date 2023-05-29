package fr.lpacsid.chat.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String urlDB = "jdbc:mysql://localhost:3306/chat?allowPublicKeyRetrieval=true&useSSL=false";
            String userDB = "chat";
            String passDB = "chat123";

            Logger.getLogger(ConnectDB.class.getName()).log(Level.INFO, "Connected to DB");
            return DriverManager.getConnection(urlDB, userDB, passDB);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
