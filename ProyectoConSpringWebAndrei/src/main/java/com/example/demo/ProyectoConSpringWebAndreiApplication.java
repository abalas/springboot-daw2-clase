package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Controller
public class ProyectoConSpringWebAndreiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoConSpringWebAndreiApplication.class, args);
    }

    @GetMapping("/hola")
    public String hola(@RequestParam(value = "nombre", defaultValue = "Mundo") String nombre) {
        return "¡Hola <b>" + nombre + "</b>!";
    }
    @RequestMapping(value = "/saludo", method = {RequestMethod.GET, RequestMethod.POST})
    public String saludo(@RequestParam(value = "nombre", required = false) String nombre,
                         @RequestParam(value = "edad", required = false) Integer edad) {
        return "Nombre: <b>" + nombre + "</b><br>Edad: <b>" + edad + "</b>";
    }
}