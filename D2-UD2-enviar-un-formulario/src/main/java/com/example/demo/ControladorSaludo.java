package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorSaludo {

    @GetMapping("/saludo")
    public String formularioSaludo(Model modelo) {
        modelo.addAttribute("saludo", new Saludo());
        return "saludo";
    }

    @PostMapping("/saludo")
    public String enviarSaludo(@ModelAttribute Saludo saludo, Model modelo) {
        modelo.addAttribute("saludo", saludo);
        return "resultado";
    }

}