package com.seisventos.jianghu.controller;

import com.seisventos.jianghu.dto.ChatMessageResponseDto;
import com.seisventos.jianghu.model.ChatMessage;
import com.seisventos.jianghu.model.MessageType;
import com.seisventos.jianghu.model.Room;
import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.service.ChatService;
import com.seisventos.jianghu.service.RoomService;
import com.seisventos.jianghu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private UserService userService;
    
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageResponseDto sendMessage(@DestinationVariable Long roomId, Map<String, String> payload, Principal principal) {
        
        System.out.println("=== DEBUG CHAT ===");
        System.out.println("Room ID recebido: " + roomId);
        System.out.println("Principal: " + (principal != null ? principal.getName() : "null"));
        System.out.println("Payload: " + payload);
        
        try {
            Optional<Room> roomOpt = roomService.findById(roomId);
            System.out.println("Sala encontrada: " + roomOpt.isPresent());
            
            if (roomOpt.isEmpty()) {
                System.out.println("ERRO: Sala com ID " + roomId + " nao foi encontrada!");
                return null;
            }
            
            Room room = roomOpt.get();
            System.out.println("Sala: " + room.getName());
            
            if (principal == null) {
                System.out.println("ERRO: Principal é null!");
                return null;
            }
            
            User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
            
            System.out.println("Usuario: " + user.getUsername());
            
            String content = payload.get("content");
            String typeStr = payload.getOrDefault("type", "CHAT");
            MessageType type;
            
            try {
                type = MessageType.valueOf(typeStr);
            } catch (IllegalArgumentException e) {
                type = MessageType.CHAT;
            }
            
            System.out.println("Mensagem: " + content + " | Tipo: " + type);
            
            ChatMessage savedMessage = chatService.saveMessage(content, room, user, type);
            System.out.println("Mensagem salva com ID: " + savedMessage.getId());
            
            // Retorna DTO em vez da entidade
            return new ChatMessageResponseDto(savedMessage);
            
        } catch (Exception e) {
            System.err.println("Erro completo no chat: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @GetMapping("/api/chat/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageResponseDto> getRoomMessages(@PathVariable Long roomId, Principal principal) {
        
        System.out.println("=== DEBUG LOAD MESSAGES ===");
        System.out.println("Room ID: " + roomId);
        System.out.println("Principal: " + (principal != null ? principal.getName() : "null"));
        
        try {
            Room room = roomService.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Sala nao encontrada"));
            
            System.out.println("Sala encontrada: " + room.getName());
            
            User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
            
            System.out.println("Usuario: " + user.getUsername());
            
            boolean isParticipant = roomService.getRoomParticipants(room).stream()
                .anyMatch(p -> p.getUser().getId().equals(user.getId()));
            
            System.out.println("Usuario é participante: " + isParticipant);
            
            if (!isParticipant) {
                throw new RuntimeException("Voce nao esta nesta sala");
            }
            
            List<ChatMessage> messages = chatService.getRecentMessages(room, 50);
            System.out.println("Mensagens carregadas: " + messages.size());
            
            // Converter para DTOs
            return messages.stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar mensagens: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}