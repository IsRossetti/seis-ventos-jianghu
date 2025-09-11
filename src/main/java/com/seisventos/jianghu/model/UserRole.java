package com.seisventos.jianghu.model;

// roles de usuario no sistema / user roles in the system
public enum UserRole {
    PLAYER("Jogador"),           // jogador comum / regular player
    GAME_MASTER("Mestre"),       // mestre de jogo / game master
    ADMIN("Administrador");      // administrador do sistema / system admin
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    // verifica se e admin / check if admin
    public boolean isAdmin() {
        return this == ADMIN;
    }
    
    // verifica se e mestre / check if game master
    public boolean isGameMaster() {
        return this == GAME_MASTER || this == ADMIN;
    }
    
    // verifica se pode criar salas / check if can create rooms
    public boolean canCreateRooms() {
        return this == GAME_MASTER || this == ADMIN;
    }
}