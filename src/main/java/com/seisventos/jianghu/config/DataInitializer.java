package com.seisventos.jianghu.config;

import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.model.UserRole;
import com.seisventos.jianghu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Admin
            User admin = new User("admin", "admin@jianghu.com", passwordEncoder.encode("admin123"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
            System.out.println("Usuario admin criado!");
            
            // Mestre
            User mestre = new User("mestre", "mestre@jianghu.com", passwordEncoder.encode("mestre123"));
            mestre.setRole(UserRole.GAME_MASTER);
            userRepository.save(mestre);
            System.out.println("Usuario mestre criado!");
            
            // Jogador
            User jogador = new User("jogador", "jogador@jianghu.com", passwordEncoder.encode("jogador123"));
            userRepository.save(jogador);
            System.out.println("Usuario jogador criado!");
            
            System.out.println("Inicializacao de dados concluida!");
        }
    }
}