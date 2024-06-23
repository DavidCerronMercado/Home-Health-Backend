package com.upc.trabajofinal2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La descripción no puede estar vacía")
    @Size(min = 3, max = 500, message = "La descripción debe tener entre 3 y 500 caracteres")
    private String descripcion;

    @NotNull(message = "La receta no puede estar vacía")
    @Size(min = 3, max = 500, message = "La receta debe tener entre 3 y 500 caracteres")
    private String receta;

    @NotNull(message = "El tratamiento no puede estar vacío")
    @Size(min = 3, max = 500, message = "El tratamiento debe tener entre 3 y 500 caracteres")
    private String tratamiento;

    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;
}
