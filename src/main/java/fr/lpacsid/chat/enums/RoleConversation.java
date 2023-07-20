package fr.lpacsid.chat.enums;

public enum RoleConversation {
    ADMIN("administrator", "administrateur"),
    MODERATOR("moderator", "modérateur"),
    PARTICIPANT("participant", "participant");

    private final String en;
    private final String fr;

    public String getEn() {
        return en;
    }

    public String getFr() {
        return fr;
    }

    RoleConversation(String en, String fr) {
        this.en = en;
        this.fr = fr;
    }
}
