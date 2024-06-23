package com.upc.trabajofinal2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDTO {
    private Long id;
    private LocalDate fecha;
    private String turno;
    private PacienteDTO paciente;
    private MedicoDTO medico;
    //no falta nada
}
