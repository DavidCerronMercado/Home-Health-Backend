package com.upc.trabajofinal2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaRegistroDTO {
    private Long id;

    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate fecha;

    @NotEmpty(message = "El turno no puede estar vacío")
    private String turno;

    @NotEmpty(message = "El DNI del paciente no puede estar vacío")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
    private String dniPaciente;

    @NotNull(message = "El ID del médico no puede estar vacío")
    private Long idMedico;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precio;
}
