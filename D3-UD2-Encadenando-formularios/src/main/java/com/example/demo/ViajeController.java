package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ViajeController {

    // Opciones de los controles de selección (una sola fuente de verdad)
    private static final List<String> DESTINOS =
            List.of("Roma", "París", "Londres", "Tokio", "Nueva York");
    private static final List<String> ALOJAMIENTOS =
            List.of("Hotel", "Apartamento", "Hostal", "Camping");
    private static final List<String> ACTIVIDADES =
            List.of("Museos", "Gastronomía", "Playa", "Senderismo", "Vida nocturna", "Compras");

    // Estos métodos añaden las listas al modelo en TODAS las peticiones de este controlador
    @ModelAttribute("destinos")
    public List<String> destinos() {
        return DESTINOS;
    }

    @ModelAttribute("alojamientos")
    public List<String> alojamientos() {
        return ALOJAMIENTOS;
    }

    @ModelAttribute("listaActividades")
    public List<String> listaActividades() {
        return ACTIVIDADES;
    }

    // ===================== Entrada =====================

    @GetMapping("/")
    public String inicio() {
        return "redirect:/paso1";
    }

    @GetMapping("/paso1")
    public String mostrarPaso1(Model model) {
        model.addAttribute("viaje", new Viaje());
        return "paso1";
    }

    // Sin sesión no hay nada guardado: si alguien escribe /paso2 en la barra, empieza de cero
    @GetMapping({ "/paso2", "/paso3","/paso4", "/resumen" })
    public String sinDatos() {
        return "redirect:/paso1";
    }

    // ===================== Paso 1 =====================

    @PostMapping("/paso1")
    public String procesarPaso1(@Validated(Viaje.Paso1.class) @ModelAttribute("viaje") Viaje viaje,
                                BindingResult result) {
        if (hayErrores(result, "nombre", "email", "edad")) {
            return "paso1";          // se queda en el paso 1 mostrando los errores
        }
        return "paso2";              // todo bien: pinta el paso 2 (con el paso 1 en hidden)
    }

    // ===================== Paso 2 =====================

    @PostMapping(value = "/paso2", params = "atras")
    public String volverAPaso1(@ModelAttribute("viaje") Viaje viaje, BindingResult result) {
        return "paso1";              // atrás: no se valida, solo se vuelve
    }

    @PostMapping(value = "/paso2", params = "siguiente")
    public String procesarPaso2(@Validated(Viaje.Paso2.class) @ModelAttribute("viaje") Viaje viaje,
                                BindingResult result) {
        // Validación extra en el controlador: el valor tiene que ser uno de los que ofrecemos
        if (!result.hasFieldErrors("destino") && !DESTINOS.contains(viaje.getDestino())) {
            result.rejectValue("destino", "destino.invalido", "Elige un destino de la lista");
        }
        if (!result.hasFieldErrors("alojamiento") && !ALOJAMIENTOS.contains(viaje.getAlojamiento())) {
            result.rejectValue("alojamiento", "alojamiento.invalido", "Elige un alojamiento de la lista");
        }
        if (hayErrores(result, "destino", "alojamiento", "noches", "personas")) {
            return "paso2";
        }
        return "paso3";
    }

    // ===================== Paso 3 =====================

    @PostMapping(value = "/paso3", params = "atras")
    public String volverAPaso2(@ModelAttribute("viaje") Viaje viaje, BindingResult result) {
        return "paso2";
    }

    @PostMapping(value = "/paso3", params = "siguiente")
    public String procesarPaso3(@Validated(Viaje.Paso3.class) @ModelAttribute("viaje") Viaje viaje,
                                BindingResult result, Model model) {
        if (!result.hasFieldErrors("actividades") && !ACTIVIDADES.containsAll(viaje.getActividades())) {
            result.rejectValue("actividades", "actividades.invalidas", "Hay alguna actividad que no existe");
        }
        if (hayErrores(result, "actividades", "comentarios")) {
            return "paso3";
        }
/*        model.addAttribute("resumen", construirResumen(viaje));
        return "resumen";
    }*/
        return "paso4";
    }
    
    //===================== Paso 4 =========================
    private static final List<String> TRANSPORTES = List.of("Avión", "Tren", "Autobús", "Coche");

    @ModelAttribute("transportes")
    public List<String> transportes() {
        return TRANSPORTES;
    }
    
    @PostMapping(value = "/paso4", params = "atras")
    public String atrasPaso4(@ModelAttribute("viaje") Viaje viaje, BindingResult result) {
        return "paso3";
    }

    @PostMapping(value = "/paso4", params = "siguiente")
    public String paso4(@Validated(Viaje.Paso4.class) @ModelAttribute("viaje") Viaje viaje,
                        BindingResult result, Model model) {
        if (result.hasFieldErrors("transporte")) {
            return "paso4";
        }
        model.addAttribute("resumen", construirResumen(viaje));
        return "resumen";
    }

    // ===================== Resumen =====================

    @PostMapping(value = "/resumen", params = "atras")
    public String volverAPaso3(@ModelAttribute("viaje") Viaje viaje, BindingResult result) {
        return "paso4";
    }

    // ===================== Métodos auxiliares =====================

    /** Devuelve true si alguno de los campos indicados tiene errores. */
    private boolean hayErrores(BindingResult result, String... campos) {
        for (String campo : campos) {
            if (result.hasFieldErrors(campo)) {
                return true;
            }
        }
        return false;
    }

    /** Una línea (String) por etapa: añadir o quitar etapas es añadir o quitar un add(). */
    private List<String> construirResumen(Viaje v) {
        List<String> lineas = new ArrayList<>();
        lineas.add("Viajero: " + v.getNombre() + ", " + v.getEdad() + " años, " + v.getEmail());
        lineas.add("Destino: " + v.getDestino() + ", " + v.getNoches() + " noche(s) para "
                + v.getPersonas() + " persona(s) en " + v.getAlojamiento().toLowerCase());
        String preferencias = "Actividades: " + String.join(", ", v.getActividades());
        if (v.getComentarios() != null && !v.getComentarios().isBlank()) {
            preferencias += ". Comentarios: " + v.getComentarios();
        }
        lineas.add(preferencias);
        lineas.add("Transporte: " + v.getTransporte());
        return lineas;
    }
}