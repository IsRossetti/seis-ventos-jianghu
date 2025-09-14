package com.seisventos.jianghu.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoomCreationDto {
    
    @NotBlank(message = "Nome da sala nao pode estar vazio")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String name;
    
    @Size(max = 500, message = "Descricao nao pode ter mais que 500 caracteres")
    private String description;
    
    private String password;
    
    private boolean isPublic = true;
    
    @Min(value = 2, message = "Minimo de 2 jogadores")
    @Max(value = 10, message = "Maximo de 10 jogadores")
    private int maxPlayers = 6;
    
    public RoomCreationDto() {}
    
    // getters e setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public boolean getIsPublic() { return isPublic; }
    public void setIsPublic(boolean isPublic) { this.isPublic = isPublic; }
    
    public int getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }
    
    public boolean hasPassword() {
        return password != null && !password.trim().isEmpty();
    }
}