package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContadorController {

    private final AtomicInteger visitas = new AtomicInteger(0);

    @GetMapping("/")
    public String contar() {
        int total = visitas.incrementAndGet();
        return "Esta página ha sido visitada " + total + " veces";
    }
}