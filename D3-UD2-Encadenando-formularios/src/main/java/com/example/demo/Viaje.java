package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Viaje {

    // Grupos de validación: cada paso valida SOLO sus campos
    public interface Paso1 {}
    public interface Paso2 {}
    public interface Paso3 {}

    // ---------- Paso 1: datos del viajero ----------
    @NotBlank(message = "El nombre es obligatorio", groups = Paso1.class)
    @Size(max = 50, message = "El nombre no puede pasar de 50 caracteres", groups = Paso1.class)
    @Pattern(regexp = "^[\\p{L} ]*$", message = "El nombre solo puede tener letras y espacios", groups = Paso1.class)
    private String nombre;

    @NotBlank(message = "El email es obligatorio", groups = Paso1.class)
    @Email(message = "El email no tiene un formato válido", groups = Paso1.class)
    private String email;

    @NotNull(message = "La edad es obligatoria", groups = Paso1.class)
    @Min(value = 18, message = "Tienes que ser mayor de edad", groups = Paso1.class)
    @Max(value = 99, message = "La edad máxima es 99", groups = Paso1.class)
    private Integer edad;

    // ---------- Paso 2: destino ----------
    @NotBlank(message = "Elige un destino", groups = Paso2.class)
    private String destino;

    @NotBlank(message = "Elige un tipo de alojamiento", groups = Paso2.class)
    private String alojamiento;

    @NotNull(message = "Indica el número de noches", groups = Paso2.class)
    @Min(value = 1, message = "Mínimo 1 noche", groups = Paso2.class)
    @Max(value = 30, message = "Máximo 30 noches", groups = Paso2.class)
    private Integer noches;

    @NotNull(message = "Indica cuántas personas viajan", groups = Paso2.class)
    @Min(value = 1, message = "Mínimo 1 persona", groups = Paso2.class)
    @Max(value = 10, message = "Máximo 10 personas", groups = Paso2.class)
    private Integer personas;

    // ---------- Paso 3: preferencias ----------
    @NotEmpty(message = "Elige al menos una actividad", groups = Paso3.class)
    @Size(max = 4, message = "Elige como mucho 4 actividades", groups = Paso3.class)
    private List<String> actividades = new ArrayList<>();

    @Size(max = 200, message = "Los comentarios no pueden pasar de 200 caracteres", groups = Paso3.class)
    private String comentarios;

    // ---------- Getters y setters ----------
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getAlojamiento() { return alojamiento; }
    public void setAlojamiento(String alojamiento) { this.alojamiento = alojamiento; }

    public Integer getNoches() { return noches; }
    public void setNoches(Integer noches) { this.noches = noches; }

    public Integer getPersonas() { return personas; }
    public void setPersonas(Integer personas) { this.personas = personas; }

    public List<String> getActividades() { return actividades; }
    public void setActividades(List<String> actividades) { this.actividades = actividades; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}