package com.upc.trabajofinal2.service;

import com.upc.trabajofinal2.entity.Paciente;

import java.time.LocalDate;
import java.util.List;

public interface PacienteService {
    void insertar(Paciente paciente);
    void eliminar(Long id);
    Paciente listarId(Long id);
    List<Paciente> listar();
    Paciente findByDni(String dni);
    List<Paciente> findByApellido(String apellido);
    List<Paciente> findByNombreContaining(String nombre);
    List<Paciente> findPacientesWithRecentDiagnosticos(LocalDate startDate, LocalDate endDate);
    Long countPacientesByHospital(String hospital);
    void modificar(Paciente paciente);
    boolean existeDni(String dni);
    void cambiarEstadoUsuario(Long id, boolean enabled);
    Paciente findByUsername(String username);
}
