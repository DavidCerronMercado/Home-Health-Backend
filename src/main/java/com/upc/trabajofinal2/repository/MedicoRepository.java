package com.upc.trabajofinal2.repository;

import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Medico findByUsuarioUsername(String username);
    @Query("SELECT m FROM Medico m WHERE m.especialidad = :especialidad")
    List<Medico> findByEspecialidad(@Param("especialidad") String especialidad);
    @Query("SELECT m FROM Medico m WHERE m.nombre = :nombre AND m.apellido = :apellido")
    List<Medico> findByNombreCompleto(@Param("nombre") String nombre, @Param("apellido") String apellido);
    @Query("SELECT m FROM Medico m WHERE m.apellido = :apellido")
    List<Medico> findByApellido(@Param("apellido") String apellido);
    @Query("SELECT m FROM Medico m WHERE m.nombre LIKE %:nombre%")
    List<Medico> findByNombreContaining(@Param("nombre") String nombre);
}
