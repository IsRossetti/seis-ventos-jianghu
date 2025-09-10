package com.seisventos.jianghu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// controller para paginas principais / controller for main pages
@Controller
public class HomeController {
    
    // rota para pagina inicial / route for home page
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // retorna o template home.html / returns home.html template
    }
}