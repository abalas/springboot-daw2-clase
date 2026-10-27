package es.goya.dwes.saludo;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoRestController {

    private final MessageSource mensajes;

    public SaludoRestController(MessageSource mensajes) {
        this.mensajes = mensajes;
    }

    @GetMapping(value = "/api/saludo", produces = "text/plain;charset=UTF-8")
    public String saludo(@RequestParam(name = "name", defaultValue = "") String name, Locale locale) {
        return name.isBlank()
                ? mensajes.getMessage("saludo.anonimo", null, locale)
                : mensajes.getMessage("saludo.nombre", new Object[] { name }, locale);
    }
}