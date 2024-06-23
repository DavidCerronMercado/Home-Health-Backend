package com.upc.trabajofinal2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticoDTO {
    private Long id;
    private String descripcion;
    private String receta;
    private String tratamiento;
    private CitaDTO cita;
}
