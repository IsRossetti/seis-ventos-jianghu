package com.seisventos.jianghu.config;

import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.model.UserRole;
import com.seisventos.jianghu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// inicializador de dados para desenvolvimento / data initializer for development
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        // cria usuarios de teste se nao existirem / create test users if they don't exist
        if (userService.findByUsername("admin").isEmpty()) {
            User admin = userService.createUser("admin", "admin@jianghu.com", "admin123");
            admin.setRole(UserRole.ADMIN);
            System.out.println("Usuario admin criado!");
        }
        
        if (userService.findByUsername("mestre").isEmpty()) {
            User gm = userService.createUser("mestre", "mestre@jianghu.com", "mestre123");
            gm.setRole(UserRole.GAME_MASTER);
            System.out.println("Usuario mestre criado!");
        }
        
        if (userService.findByUsername("jogador").isEmpty()) {
            userService.createUser("jogador", "jogador@jianghu.com", "jogador123");
            System.out.println("Usuario jogador criado!");
        }
        
        System.out.println("Inicializacao de dados concluida!");
    }
}