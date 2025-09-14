package com.seisventos.jianghu.service;

import com.seisventos.jianghu.model.Room;
import com.seisventos.jianghu.model.RoomParticipant;
import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.repository.RoomParticipantRepository;
import com.seisventos.jianghu.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomParticipantRepository participantRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    // criar nova sala
    public Room createRoom(String name, String description, String password, 
                          boolean isPublic, int maxPlayers, User creator) {
        
        if (roomRepository.existsByNameAndIsActiveTrue(name)) {
            throw new RuntimeException("Ja existe uma sala com este nome");
        }
        
        Room room = new Room(name, description, creator);
        room.setIsPublic(isPublic);
        room.setMaxPlayers(maxPlayers);  
        
        if (password != null && !password.trim().isEmpty()) {
            room.setPasswordHash(passwordEncoder.encode(password));
           // room.setIsPublic(false); 
        }
        
        Room savedRoom = roomRepository.save(room);
        
        // criador automaticamente entra na sala como GM
        RoomParticipant creatorParticipant = new RoomParticipant(savedRoom, creator);
        creatorParticipant.setIsGameMaster(true);
        participantRepository.save(creatorParticipant);
        
        return savedRoom;
    }
    
    // entrar em sala
    public RoomParticipant joinRoom(Long roomId, User user, String password) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Sala nao encontrada"));
            
        if (!room.getIsActive()) {
            throw new RuntimeException("Esta sala nao esta ativa");
        }
        
        if (participantRepository.existsByRoomAndUser(room, user)) {
            throw new RuntimeException("Voce ja esta nesta sala");
        }
        
        if (room.isFull()) {
            throw new RuntimeException("Sala esta lotada");
        }
        
        // verificar senha se necessario
        if (room.hasPassword()) {
            if (password == null || !passwordEncoder.matches(password, room.getPasswordHash())) {
                throw new RuntimeException("Senha incorreta");
            }
        }
        
        RoomParticipant participant = new RoomParticipant(room, user);
        return participantRepository.save(participant);
    }
    
    // sair da sala
    public void leaveRoom(Long roomId, User user) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Sala nao encontrada"));
            
        RoomParticipant participant = participantRepository.findByRoomAndUser(room, user)
            .orElseThrow(() -> new RuntimeException("Voce nao esta nesta sala"));
            
        participantRepository.delete(participant);
        
        // se era o criador e ultimo participante, desativar sala
        if (room.getCreator().getId().equals(user.getId()) && 
            participantRepository.countByRoom(room) == 0) {
            room.setIsActive(false);
            roomRepository.save(room);
        }
    }
    
    // listar salas publicas
    public List<Room> getPublicRooms() {
        return roomRepository.findByIsPublicTrueAndIsActiveTrue();
    }
    
    // listar salas do usuario
    public List<RoomParticipant> getUserRooms(User user) {
        return participantRepository.findByUser(user);
    }
    
    // buscar sala por id
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }
    
    // buscar participantes de uma sala
    public List<RoomParticipant> getRoomParticipants(Room room) {
        return participantRepository.findByRoom(room);
    }
}