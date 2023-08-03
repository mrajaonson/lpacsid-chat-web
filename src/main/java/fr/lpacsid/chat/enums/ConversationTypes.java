package fr.lpacsid.chat.enums;

public enum ConversationTypes {
    DISCUSSION("Direct message", "Message privé"),
    GROUP("Group", "Groupe"),
    CHANNEL("Channel", "Canal");

    private final String en;
    private final String fr;

    public String getEn() {
        return en;
    }

    public String getFr() {
        return fr;
    }

    ConversationTypes(String en, String fr) {
        this.en = en;
        this.fr = fr;
    }
}
