package com.example.demo;

import jakarta.validation.Valid;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Controller
public class ControladorWeb implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registro) {
        registro.addViewController("/resultado").setViewName("resultado");
    }

    @GetMapping("/")
    public String mostrarFormulario(FormularioPersona formularioPersona) {
        return "formulario";
    }

    @PostMapping("/")
    public String comprobarPersona(@Valid FormularioPersona formularioPersona, BindingResult resultadoValidacion) {

        if (resultadoValidacion.hasErrors()) {
            return "formulario";
        }

        return "redirect:/resultado";
    }
}