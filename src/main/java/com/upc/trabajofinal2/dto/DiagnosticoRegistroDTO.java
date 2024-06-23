package com.upc.trabajofinal2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticoRegistroDTO {
    private Long id;
    private String descripcion;
    private String receta;
    private String tratamiento;
    private Long citaId;
}
