package com.upc.trabajofinal2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotNull(message = "El apellido no puede estar vacío")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    private String apellido;

    @NotNull(message = "La especialidad no puede estar vacía")
    @Size(min = 3, max = 50, message = "La especialidad debe tener entre 3 y 50 caracteres")
    private String especialidad;

    @NotNull(message = "El hospital no puede estar vacío")
    @Size(min = 3, max = 100, message = "El hospital debe tener entre 3 y 100 caracteres")
    private String hospital;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
