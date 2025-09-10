package com.seisventos.jianghu.repository;

import com.seisventos.jianghu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// repositorio para operacoes com usuario / repository for user operations
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // busca usuario por username / find user by username
    Optional<User> findByUsername(String username);
    
    // busca usuario por email / find user by email
    Optional<User> findByEmail(String email);
    
    // verifica se username ja existe / check if username already exists
    boolean existsByUsername(String username);
    
    // verifica se email ja existe / check if email already exists
    boolean existsByEmail(String email);
    
    // busca usuarios ativos / find active users
    java.util.List<User> findByActiveTrue();
}