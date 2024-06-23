package com.upc.trabajofinal2.serviceImpl;

import com.upc.trabajofinal2.entity.Diagnostico;
import com.upc.trabajofinal2.repository.DiagnosticoRepository;
import com.upc.trabajofinal2.service.DiagnosticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiagnosticoServiceImpl implements DiagnosticoService {
    @Autowired
    private DiagnosticoRepository diagnosticoRepository;

    @Override
    public void insertar(Diagnostico diagnostico) {
        diagnosticoRepository.save(diagnostico);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Diagnostico diagnostico = diagnosticoRepository.findById(id).orElse(null);
        if (diagnostico != null) {
            diagnosticoRepository.delete(diagnostico);
        }
    }
    @Override
    public Diagnostico listarId(Long id) {
        return diagnosticoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Diagnostico> listar() {
        return diagnosticoRepository.findAll();
    }

    @Override
    public List<Diagnostico> findDiagnosticosByPaciente(Long pacienteId) {
        return diagnosticoRepository.findDiagnosticosByPaciente(pacienteId);
    }
    @Override
    public List<Diagnostico> findByMedicoId(Long medicoId) {
        return diagnosticoRepository.findByMedicoId(medicoId);
    }
}
