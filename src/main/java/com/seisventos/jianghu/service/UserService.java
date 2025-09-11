package com.seisventos.jianghu.service;

import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.model.UserRole;
import com.seisventos.jianghu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// servico para logica de negocio de usuarios / service for user business logic
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    // criar novo usuario / create new user
    public User createUser(String username, String email, String password) {
        // verifica se username ja existe / check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username ja existe");
        }
        
        // verifica se email ja existe / check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email ja existe");
        }
        
        // cria usuario com senha criptografada / create user with encrypted password
        User user = new User(username, email, passwordEncoder.encode(password));
        return userRepository.save(user);
    }
    
    // buscar usuario por username / find user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // buscar usuario por email / find user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // buscar usuario por id / find user by id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    // listar todos usuarios ativos / list all active users
    public List<User> findActiveUsers() {
        return userRepository.findByActiveTrue();
    }
    
    // verificar senha / verify password
    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
    
    // promover usuario para game master / promote user to game master
    public User promoteToGameMaster(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        user.setRole(UserRole.GAME_MASTER);
        return userRepository.save(user);
    }
    
    // desativar usuario / deactivate user
    public User deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        user.setActive(false);
        return userRepository.save(user);
    }
}