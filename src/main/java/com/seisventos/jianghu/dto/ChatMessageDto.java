package com.seisventos.jianghu.dto;

import com.seisventos.jianghu.model.MessageType;

public class ChatMessageDto {
    
    private String content;
    private String type = "CHAT";
    private Long roomId;
    
    public ChatMessageDto() {}
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    
    public MessageType getMessageType() {
        try {
            return MessageType.valueOf(type);
        } catch (IllegalArgumentException e) {
            return MessageType.CHAT;
        }
    }
}