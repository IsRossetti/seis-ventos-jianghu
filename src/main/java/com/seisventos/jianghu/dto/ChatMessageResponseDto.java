package com.seisventos.jianghu.dto;

import com.seisventos.jianghu.model.ChatMessage;
import com.seisventos.jianghu.model.MessageType;
import java.time.LocalDateTime;

public class ChatMessageResponseDto {
    
    private Long id;
    private String content;
    private MessageType type;
    private LocalDateTime sentAt;
    private String username;
    private Long roomId;
    
    public ChatMessageResponseDto() {}
    
    public ChatMessageResponseDto(ChatMessage message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.type = message.getType();
        this.sentAt = message.getSentAt();
        this.username = message.getUser().getUsername();
        this.roomId = message.getRoom().getId();
    }
    
    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
    
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    
    // objeto simulando user para compatibilidade com JavaScript
    public UserDto getUser() {
        return new UserDto(this.username);
    }
    
    public static class UserDto {
        private String username;
        
        public UserDto(String username) {
            this.username = username;
        }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }
}