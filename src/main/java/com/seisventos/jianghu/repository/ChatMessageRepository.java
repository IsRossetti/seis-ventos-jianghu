package com.seisventos.jianghu.repository;

import com.seisventos.jianghu.model.ChatMessage;
import com.seisventos.jianghu.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    // buscar mensagens de uma sala ordenadas por data
    List<ChatMessage> findByRoomOrderBySentAtAsc(Room room);
    
    // buscar ultimas N mensagens de uma sala
    @Query("SELECT m FROM ChatMessage m WHERE m.room = :room ORDER BY m.sentAt DESC LIMIT :limit")
    List<ChatMessage> findLastMessagesByRoom(@Param("room") Room room, @Param("limit") int limit);
    
    // contar mensagens de uma sala
    long countByRoom(Room room);
}