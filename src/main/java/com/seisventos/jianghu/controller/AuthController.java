package com.seisventos.jianghu.controller;

import com.seisventos.jianghu.dto.UserRegistrationDto;
import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// controller para autenticacao / controller for authentication
@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    // pagina de login / login page
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    // pagina de registro / registration page
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "auth/register";
    }
    
    // processar registro / process registration
    @PostMapping("/register")
    public String processRegistration(
            @Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        
        // validar se senhas coincidem / validate if passwords match
        if (!registrationDto.isPasswordMatch()) {
            result.rejectValue("confirmPassword", "error.user", "Senhas nao coincidem");
        }
        
        // se tem erros, voltar para o formulario / if has errors, return to form
        if (result.hasErrors()) {
            return "auth/register";
        }
        
        try {
            // criar usuario / create user
            User user = userService.createUser(
                registrationDto.getUsername(),
                registrationDto.getEmail(), 
                registrationDto.getPassword()
            );
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Usuario criado com sucesso! Faca login para continuar.");
            return "redirect:/login";
            
        } catch (RuntimeException e) {
            // usuario ja existe / user already exists
            result.rejectValue("username", "error.user", e.getMessage());
            return "auth/register";
        }
    }
    
    // dashboard apos login / dashboard after login
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}