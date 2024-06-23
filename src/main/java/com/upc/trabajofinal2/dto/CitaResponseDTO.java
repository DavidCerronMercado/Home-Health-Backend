package com.upc.trabajofinal2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaResponseDTO {
    private Long id;
    private LocalDate fecha;
    private String turno;
    private String nombrePaciente;
    private String dniPaciente;
    private String nombreMedico;
    private String especialidadMedico;
    private String hospitalMedico;
    private Double precio;
}
