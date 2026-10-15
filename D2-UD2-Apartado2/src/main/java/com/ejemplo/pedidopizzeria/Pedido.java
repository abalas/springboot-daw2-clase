package com.ejemplo.pedidopizzeria;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Pedido {

    // Campos de texto
    @NotBlank(message = "Escribe tu nombre")
    @Size(max = 40, message = "El nombre no puede pasar de 40 caracteres")
    private String nombre;

    @NotBlank(message = "Escribe tu correo")
    @Email(message = "El correo no tiene un formato válido")
    private String correo;

    // Texto que Spring convierte en número
    @NotNull(message = "Indica cuántas pizzas quieres")
    @Min(value = 1, message = "Pide al menos 1 pizza")
    @Max(value = 10, message = "Como mucho 10 pizzas por pedido")
    private Integer cantidad;

    // Radio: llega un solo texto, o nada si no se marca ninguno
    @NotBlank(message = "Elige un tipo de masa")
    @Pattern(regexp = "Fina|Gruesa|Rellena", message = "Ese tipo de masa no existe")
    private String masa;

    // Checkbox múltiple con valores de texto, recibido como lista
    @NotEmpty(message = "Elige al menos un ingrediente")
    @Size(max = 4, message = "Como mucho 4 ingredientes")
    private List<String> ingredientes = new ArrayList<>();

    // Checkbox múltiple con valores enteros, recibido como array
    private Integer[] extras = new Integer[0];

    // Área de texto
    @Size(max = 200, message = "Las observaciones no pueden pasar de 200 caracteres")
    private String observaciones;

    // Checkbox único
    @AssertTrue(message = "Debes aceptar las condiciones")
    private boolean condiciones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Integer[] getExtras() {
        return extras;
    }

    public void setExtras(Integer[] extras) {
        this.extras = extras;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isCondiciones() {
        return condiciones;
    }

    public void setCondiciones(boolean condiciones) {
        this.condiciones = condiciones;
    }
}