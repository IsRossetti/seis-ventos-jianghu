package com.seisventos.jianghu.service;

import com.seisventos.jianghu.model.ChatMessage;
import com.seisventos.jianghu.model.MessageType;
import com.seisventos.jianghu.model.Room;
import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatService {
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    // salvar mensagem de chat
    public ChatMessage saveMessage(String content, Room room, User user, MessageType type) {
        ChatMessage message = new ChatMessage(content, room, user, type);
        return chatMessageRepository.save(message);
    }
    
    // buscar mensagens de uma sala
    public List<ChatMessage> getRoomMessages(Room room) {
        return chatMessageRepository.findByRoomOrderBySentAtAsc(room);
    }
    
    // buscar ultimas mensagens de uma sala
    public List<ChatMessage> getRecentMessages(Room room, int limit) {
        List<ChatMessage> messages = chatMessageRepository.findLastMessagesByRoom(room, limit);
        // inverter ordem para mostrar mais antigas primeiro
        java.util.Collections.reverse(messages);
        return messages;
    }
    
    // criar mensagem do sistema
    public ChatMessage createSystemMessage(String content, Room room) {
        // usuario sistema ficticio
        User systemUser = new User();
        systemUser.setUsername("Sistema");
        
        ChatMessage message = new ChatMessage(content, room, systemUser, MessageType.SYSTEM);
        return chatMessageRepository.save(message);
    }
}