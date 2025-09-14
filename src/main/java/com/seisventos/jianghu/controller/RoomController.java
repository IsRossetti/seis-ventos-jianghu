package com.seisventos.jianghu.controller;

import com.seisventos.jianghu.dto.RoomCreationDto;
import com.seisventos.jianghu.model.Room;
import com.seisventos.jianghu.model.RoomParticipant;
import com.seisventos.jianghu.model.User;
import com.seisventos.jianghu.service.RoomService;
import com.seisventos.jianghu.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private UserService userService;
    
    // listar salas publicas
    @GetMapping
    public String listRooms(Model model, Authentication auth) {
        List<Room> publicRooms = roomService.getPublicRooms();
        
        User currentUser = userService.findByUsername(auth.getName()).orElse(null);
        List<RoomParticipant> userRooms = roomService.getUserRooms(currentUser);
        
        model.addAttribute("publicRooms", publicRooms);
        model.addAttribute("userRooms", userRooms);
        
        return "rooms/list";
    }
    
    // formulario de criacao de sala
    @GetMapping("/create")
    public String createRoomForm(Model model) {
        model.addAttribute("room", new RoomCreationDto());
        return "rooms/create";
    }
    
    // processar criacao de sala
    @PostMapping("/create")
    public String createRoom(@Valid @ModelAttribute("room") RoomCreationDto roomDto,
                           BindingResult result,
                           Authentication auth,
                           RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "rooms/create";
        }
        
        try {
            User creator = userService.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
            
            Room room = roomService.createRoom(
                roomDto.getName(),
                roomDto.getDescription(),
                roomDto.getPassword(),
                roomDto.getIsPublic(),
                roomDto.getMaxPlayers(),
                creator
            );
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Sala '" + room.getName() + "' criada com sucesso!");
            
            return "redirect:/rooms/" + room.getId();
            
        } catch (RuntimeException e) {
            result.rejectValue("name", "error.room", e.getMessage());
            return "rooms/create";
        }
    }
    
    // entrar em sala
    @PostMapping("/{id}/join")
    public String joinRoom(@PathVariable Long id,
                         @RequestParam(required = false) String password,
                         Authentication auth,
                         RedirectAttributes redirectAttributes) {
        
        try {
            User user = userService.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
            
            roomService.joinRoom(id, user, password);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Voce entrou na sala com sucesso!");
            
            return "redirect:/rooms/" + id;
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/rooms";
        }
    }
    
    // sair da sala
    @PostMapping("/{id}/leave")
    public String leaveRoom(@PathVariable Long id,
                          Authentication auth,
                          RedirectAttributes redirectAttributes) {
        
        try {
            User user = userService.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
            
            roomService.leaveRoom(id, user);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Voce saiu da sala.");
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/rooms";
    }
    
    // visualizar sala
    @GetMapping("/{id}")
    public String viewRoom(@PathVariable Long id, Model model, Authentication auth) {
        Room room = roomService.findById(id)
            .orElseThrow(() -> new RuntimeException("Sala nao encontrada"));
        
        User currentUser = userService.findByUsername(auth.getName()).orElse(null);
        List<RoomParticipant> participants = roomService.getRoomParticipants(room);
        
        boolean isParticipant = participants.stream()
            .anyMatch(p -> p.getUser().getId().equals(currentUser.getId()));
        
        model.addAttribute("room", room);
        model.addAttribute("participants", participants);
        model.addAttribute("isParticipant", isParticipant);
        model.addAttribute("currentUser", currentUser);
        
        return "rooms/view";
    }
}