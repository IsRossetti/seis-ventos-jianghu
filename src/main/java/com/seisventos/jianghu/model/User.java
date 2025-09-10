package com.seisventos.jianghu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

// entidade usuario para jpa / user entity for jpa
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // nome de usuario unico / unique username
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username nao pode estar vazio")
    @Size(min = 3, max = 20, message = "Username deve ter entre 3 e 20 caracteres")
    private String username;
    
    // email unico / unique email
    @Column(unique = true, nullable = false)
    @Email(message = "Email deve ser valido")
    @NotBlank(message = "Email nao pode estar vazio")
    private String email;
    
    // senha criptografada / encrypted password
    @Column(nullable = false)
    @NotBlank(message = "Senha nao pode estar vazia")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;
    
    // role do usuario / user role
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.PLAYER;
    
    // conta ativa / active account
    @Column(nullable = false)
    private Boolean active = true;
    
    // data de criacao / creation date
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    // construtor vazio obrigatorio para jpa / empty constructor required for jpa
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    // construtor com parametros / constructor with parameters
    public User(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // getters e setters / getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}