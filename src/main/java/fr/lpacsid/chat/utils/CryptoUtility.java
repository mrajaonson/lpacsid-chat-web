package fr.lpacsid.chat.utils;
import org.mindrot.jbcrypt.BCrypt;

public class CryptoUtility {
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static Boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
