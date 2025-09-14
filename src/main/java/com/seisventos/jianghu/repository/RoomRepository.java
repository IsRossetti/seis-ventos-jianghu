package com.seisventos.jianghu.repository;

import com.seisventos.jianghu.model.Room;
import com.seisventos.jianghu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    // buscar salas publicas ativas
    List<Room> findByIsPublicTrueAndIsActiveTrue();
    
    // buscar salas criadas por um usuario
    List<Room> findByCreatorAndIsActiveTrue(User creator);
    
    // buscar salas por nome (case insensitive)
    @Query("SELECT r FROM Room r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')) AND r.isActive = true")
    List<Room> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") String name);
    
    // contar salas ativas
    long countByIsActiveTrue();
    
    // verificar se nome ja existe
    boolean existsByNameAndIsActiveTrue(String name);
}