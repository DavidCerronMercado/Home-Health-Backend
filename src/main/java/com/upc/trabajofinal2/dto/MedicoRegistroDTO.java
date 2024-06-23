package com.upc.trabajofinal2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoRegistroDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String hospital;
    private String username;
    private String password;
}
