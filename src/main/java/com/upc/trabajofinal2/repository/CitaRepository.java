package com.upc.trabajofinal2.repository;

import com.upc.trabajofinal2.entity.Cita;
import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByMedicoId(Long medicoId);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Cita c WHERE c.medico.id = :medicoId AND c.fecha = :fecha AND c.turno = :turno")
    Boolean isHorarioDisponible(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha, @Param("turno") String turno);

    @Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.id = :medicoId AND c.fecha = :fecha")
    Long countCitasByMedicoAndFecha(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM Cita c WHERE c.paciente.id = :pacienteId")
    List<Cita> findCitasByPaciente(@Param("pacienteId") Long pacienteId);

    @Query("SELECT c FROM Cita c WHERE c.fecha BETWEEN :startDate AND :endDate")
    List<Cita> findCitasByFechaBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("SELECT SUM(c.precio) FROM Cita c WHERE c.medico.id = :medicoId")
    Double sumPrecioByMedicoId(@Param("medicoId") Long medicoId);
}

