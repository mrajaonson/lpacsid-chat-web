package fr.lpacsid.chat.enums;

public enum UserStatus {
    AVAILABLE("Available", "Disponible"),
    BUSY("Busy", "Occupé"),
    DO_NOT_DISTURB("Do not disturb", "Ne pas déranger"),
    ABSENT("Absent", "Absent"),
    OFFLINE("Offline", "Hors ligne");

    private final String en;
    private final String fr;

    public String getEn() {
        return en;
    }

    public String getFr() {
        return fr;
    }

    UserStatus(String en, String fr) {
        this.en = en;
        this.fr = fr;
    }
}
