package com.seisventos.jianghu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// dto para formulario de registro / dto for registration form
public class UserRegistrationDto {
    
    @NotBlank(message = "Username nao pode estar vazio")
    @Size(min = 3, max = 20, message = "Username deve ter entre 3 e 20 caracteres")
    private String username;
    
    @Email(message = "Email deve ser valido")
    @NotBlank(message = "Email nao pode estar vazio")
    private String email;
    
    @NotBlank(message = "Senha nao pode estar vazia")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;
    
    @NotBlank(message = "Confirmacao de senha nao pode estar vazia")
    private String confirmPassword;
    
    // construtor vazio / empty constructor
    public UserRegistrationDto() {}
    
    // getters e setters / getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    
    // validacao customizada / custom validation
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}