package com.seisventos.jianghu.model;

public enum MessageType {
    CHAT("Conversa"),
    SYSTEM("Sistema"),
    DICE_ROLL("Rolagem de Dados"),
    NARRATION("Narração");
    
    private final String displayName;
    
    MessageType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}