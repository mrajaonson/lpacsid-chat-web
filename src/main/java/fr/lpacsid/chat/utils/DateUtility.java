package fr.lpacsid.chat.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    /**
     * Retourne la date du jour sous le format 2023-06-01T00:31:15.116789
     * @return String
     */
    public static String getLocalDateTimeNowToString() {
        return LocalDateTime.now().toString();
    }

    /**
     * Retourne la date en paramètre sous le format dd/MM/yyyy HH:mm
     * @param date String
     * @return String
     */
    public static String getFormattedDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(outputFormatter);
    }
}
