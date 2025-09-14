package com.seisventos.jianghu.repository;

import com.seisventos.jianghu.model.Room;
import com.seisventos.jianghu.model.RoomParticipant;
import com.seisventos.jianghu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomParticipantRepository extends JpaRepository<RoomParticipant, Long> {
    
    // buscar participacao de usuario em sala especifica
    Optional<RoomParticipant> findByRoomAndUser(Room room, User user);
    
    // buscar todas as salas que usuario participa
    List<RoomParticipant> findByUser(User user);
    
    // buscar todos participantes de uma sala
    List<RoomParticipant> findByRoom(Room room);
    
    // contar participantes de uma sala
    long countByRoom(Room room);
    
    // verificar se usuario ja esta na sala
    boolean existsByRoomAndUser(Room room, User user);
    
    // buscar game masters de uma sala
    List<RoomParticipant> findByRoomAndIsGameMasterTrue(Room room);
}