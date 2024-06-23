package com.upc.trabajofinal2.repository;

import com.upc.trabajofinal2.entity.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    @Query("SELECT d FROM Diagnostico d WHERE d.cita.paciente.id = :pacienteId")
    List<Diagnostico> findDiagnosticosByPaciente(@Param("pacienteId") Long pacienteId);

    @Query("SELECT d FROM Diagnostico d WHERE d.cita.medico.id = :medicoId")
    List<Diagnostico> findByMedicoId(@Param("medicoId") Long medicoId);
}
