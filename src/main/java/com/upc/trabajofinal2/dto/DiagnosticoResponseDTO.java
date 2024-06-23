package com.upc.trabajofinal2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticoResponseDTO {
    private Long id;
    private LocalDate fechaCita;
    private String nombreMedico;
    private String nombrePaciente;
    private String dniPaciente;
    private String descripcion;
    private String receta;
    private String tratamiento;
}
