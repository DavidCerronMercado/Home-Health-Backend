package com.upc.trabajofinal2.repository;

import com.upc.trabajofinal2.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByDni(String dni);
    @Query("SELECT p FROM Paciente p WHERE p.apellido = :apellido")
    List<Paciente> findByApellido(@Param("apellido") String apellido);

    @Query("SELECT p FROM Paciente p WHERE p.nombre LIKE %:nombre%")
    List<Paciente> findByNombreContaining(@Param("nombre") String nombre);

    @Query("SELECT DISTINCT p FROM Paciente p JOIN p.citas c WHERE c.fecha BETWEEN :startDate AND :endDate")
    List<Paciente> findPacientesWithRecentDiagnosticos(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(DISTINCT p) FROM Paciente p JOIN p.citas c JOIN c.medico m WHERE m.hospital = :hospital")
    Long countPacientesByHospital(@Param("hospital") String hospital);
    Paciente findByUsuarioUsername(String username);
}
