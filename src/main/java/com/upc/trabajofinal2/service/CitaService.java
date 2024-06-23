package com.upc.trabajofinal2.service;

import com.upc.trabajofinal2.entity.Cita;
import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Paciente;

import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    void insertar(Cita cita);
    void eliminar(Long id);
    Cita listarId(Long id);
    List<Cita> listar();
    Boolean isHorarioDisponible(Long medicoId, LocalDate fecha, String turno);
    Long countCitasByMedicoAndFecha(Long medicoId, LocalDate fecha);
    List<Cita> findCitasByPaciente(Long pacienteId);
    List<Cita> findCitasByFechaBetween(LocalDate startDate, LocalDate endDate);
    Double sumPrecioByMedicoId(Long medicoId);
    List<Cita> findCitasByMedicoo(Long medicoId);
}
