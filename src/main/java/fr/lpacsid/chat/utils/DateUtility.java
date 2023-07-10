package fr.lpacsid.chat.utils;

import java.time.LocalDateTime;

public class DateUtility {

    /**
     * Retourne la date du jour sous le format 2023-06-01T00:31:15.116789
     * @return String
     */
    public static String getLocalDateTimeNowToString() {
        return LocalDateTime.now().toString();
    }
}
