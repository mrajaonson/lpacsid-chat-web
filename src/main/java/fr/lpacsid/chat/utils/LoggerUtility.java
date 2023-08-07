package fr.lpacsid.chat.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtility {

    private static final Logger LOGGER = Logger.getLogger(LoggerUtility.class.getName());

    public static void log(Level level, String msg, Throwable thrown) {
        LOGGER.log(level, msg, thrown);
    }

    /**
     * Affiche un log de l'exception
     * @param e Exception
     */
    public static void logException(Exception e) {
        log( Level.SEVERE, null, e);
    }

    public static void logUpdateQuery(String table, int rowsAffected, int id) {
        String msg = table + " - UPDATE - ";
        String msg2;
        if (rowsAffected > 0) {
            msg2 = " row id " + id + " updated successfully.";
        } else {
            msg2 = " row id " + id + " not found.";
        }
        log(Level.INFO, msg.concat(msg2), null);
    }

    public static void logDeleteQuery(String table, int rowsAffected, int id) {
        String msg = table + " - DELETE - ";
        String rows;
        String msg2;
        if (rowsAffected == 1) {
            msg2 = "row id " + id + " deleted successfully.";
        } else if (rowsAffected > 1) {
            msg2 = rowsAffected + " rows deleted successfully with id " + id + " condition";
        } else {
            msg2 = "row id " + id + " not found.";
        }
        log(Level.INFO, msg.concat(msg2), null);
    }

    public static void logInsertQuery(String table, Integer id) {
        String msg = "New " + table + " created successfully. Inserted ID is: " + id;
        log(Level.INFO, msg, null);
    }
}
