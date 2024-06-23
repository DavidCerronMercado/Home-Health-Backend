package com.upc.trabajofinal2.serviceImpl;

import com.upc.trabajofinal2.entity.Cita;
import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Paciente;
import com.upc.trabajofinal2.repository.CitaRepository;
import com.upc.trabajofinal2.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaRepository citaRepository;

    @Override
    public void insertar(Cita cita) {
        try {
            citaRepository.save(cita);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar la cita", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            citaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la cita", e);
        }
    }

    @Override
    public Cita listarId(Long id) {
        return citaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    @Override
    public List<Cita> listar() {
        try {
            return citaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las citas", e);
        }
    }

    @Override
    public Boolean isHorarioDisponible(Long medicoId, LocalDate fecha, String turno) {
        try {
            return citaRepository.isHorarioDisponible(medicoId, fecha, turno);
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar la disponibilidad del horario", e);
        }
    }

    @Override
    public Long countCitasByMedicoAndFecha(Long medicoId, LocalDate fecha) {
        try {
            return citaRepository.countCitasByMedicoAndFecha(medicoId, fecha);
        } catch (Exception e) {
            throw new RuntimeException("Error al contar las citas del médico en la fecha dada", e);
        }
    }

    @Override
    public List<Cita> findCitasByPaciente(Long pacienteId) {
        try {
            return citaRepository.findCitasByPaciente(pacienteId);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las citas por paciente", e);
        }
    }

    @Override
    public List<Cita> findCitasByFechaBetween(LocalDate startDate, LocalDate endDate) {
        try {
            return citaRepository.findCitasByFechaBetween(startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las citas entre las fechas dadas", e);
        }
    }

    @Override
    public Double sumPrecioByMedicoId(Long medicoId) {
        try {
            return citaRepository.sumPrecioByMedicoId(medicoId);
        } catch (Exception e) {
            throw new RuntimeException("Error al sumar los precios de las citas por médico", e);
        }
    }

    @Override
    public List<Cita> findCitasByMedicoo(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId);
    }
}
