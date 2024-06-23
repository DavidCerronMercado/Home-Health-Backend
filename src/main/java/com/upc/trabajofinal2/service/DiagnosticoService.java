package com.upc.trabajofinal2.service;

import com.upc.trabajofinal2.entity.Diagnostico;

import java.util.List;

public interface DiagnosticoService {
    void insertar(Diagnostico diagnostico);
    void eliminar(Long id);
    Diagnostico listarId(Long id);
    List<Diagnostico> listar();
    List<Diagnostico> findDiagnosticosByPaciente(Long pacienteId);
    List<Diagnostico> findByMedicoId(Long medicoId);
}
