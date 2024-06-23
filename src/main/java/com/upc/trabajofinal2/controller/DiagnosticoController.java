package com.upc.trabajofinal2.controller;

import com.upc.trabajofinal2.dto.DiagnosticoDTO;
import com.upc.trabajofinal2.dto.DiagnosticoRegistroDTO;
import com.upc.trabajofinal2.dto.DiagnosticoResponseDTO;
import com.upc.trabajofinal2.entity.Cita;
import com.upc.trabajofinal2.entity.Diagnostico;
import com.upc.trabajofinal2.service.CitaService;
import com.upc.trabajofinal2.service.DiagnosticoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {
    @Autowired
    private DiagnosticoService diagnosticoService;

    @Autowired
    private CitaService citaService;

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody DiagnosticoRegistroDTO diagnosticoDto) {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setDescripcion(diagnosticoDto.getDescripcion());
        diagnostico.setReceta(diagnosticoDto.getReceta());
        diagnostico.setTratamiento(diagnosticoDto.getTratamiento());

        Cita cita = citaService.listarId(diagnosticoDto.getCitaId());
        if (cita == null) {
            return ResponseEntity.badRequest().build(); // o lanzar una excepción personalizada
        }
        diagnostico.setCita(cita);

        diagnosticoService.insertar(diagnostico);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/modificar")
    public ResponseEntity<Void> modificar(@RequestBody DiagnosticoRegistroDTO diagnosticoDto) {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setId(diagnosticoDto.getId());
        diagnostico.setDescripcion(diagnosticoDto.getDescripcion());
        diagnostico.setReceta(diagnosticoDto.getReceta());
        diagnostico.setTratamiento(diagnosticoDto.getTratamiento());

        Cita cita = citaService.listarId(diagnosticoDto.getCitaId());
        if (cita == null) {
            return ResponseEntity.badRequest().build(); // o lanzar una excepción personalizada
        }
        diagnostico.setCita(cita);

        diagnosticoService.insertar(diagnostico);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        diagnosticoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<DiagnosticoResponseDTO> listarId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Diagnostico diagnostico = diagnosticoService.listarId(id);
        DiagnosticoResponseDTO diagnosticoDto = modelMapper.map(diagnostico, DiagnosticoResponseDTO.class);
        diagnosticoDto.setFechaCita(diagnostico.getCita().getFecha());
        diagnosticoDto.setNombrePaciente(diagnostico.getCita().getPaciente().getNombre());
        diagnosticoDto.setDniPaciente(diagnostico.getCita().getPaciente().getDni());
        diagnosticoDto.setNombreMedico(diagnostico.getCita().getMedico().getNombre());
        return ResponseEntity.ok(diagnosticoDto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DiagnosticoResponseDTO>> listar() {
        List<Diagnostico> diagnosticos = diagnosticoService.listar();
        List<DiagnosticoResponseDTO> diagnosticosDto = diagnosticos.stream()
                .map(diagnostico -> {
                    ModelMapper modelMapper = new ModelMapper();
                    DiagnosticoResponseDTO diagnosticoDto = modelMapper.map(diagnostico, DiagnosticoResponseDTO.class);
                    diagnosticoDto.setFechaCita(diagnostico.getCita().getFecha());
                    diagnosticoDto.setNombrePaciente(diagnostico.getCita().getPaciente().getNombre());
                    diagnosticoDto.setDniPaciente(diagnostico.getCita().getPaciente().getDni());
                    diagnosticoDto.setNombreMedico(diagnostico.getCita().getMedico().getNombre());
                    return diagnosticoDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(diagnosticosDto);
    }

    @GetMapping("/listarPorPaciente/{pacienteId}")
    public ResponseEntity<List<DiagnosticoResponseDTO>> listarPorPaciente(@PathVariable("pacienteId") Long pacienteId) {
        List<Diagnostico> diagnosticos = diagnosticoService.findDiagnosticosByPaciente(pacienteId);
        List<DiagnosticoResponseDTO> diagnosticosDto = diagnosticos.stream()
                .map(diagnostico -> {
                    ModelMapper modelMapper = new ModelMapper();
                    DiagnosticoResponseDTO diagnosticoDto = modelMapper.map(diagnostico, DiagnosticoResponseDTO.class);
                    diagnosticoDto.setFechaCita(diagnostico.getCita().getFecha());
                    diagnosticoDto.setNombrePaciente(diagnostico.getCita().getPaciente().getNombre());
                    diagnosticoDto.setDniPaciente(diagnostico.getCita().getPaciente().getDni());
                    diagnosticoDto.setNombreMedico(diagnostico.getCita().getMedico().getNombre());
                    return diagnosticoDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(diagnosticosDto);
    }
    @GetMapping("/listarPorMedico/{medicoId}")
    public ResponseEntity<List<Diagnostico>> listarPorMedico(@PathVariable Long medicoId) {
        List<Diagnostico> diagnosticos = diagnosticoService.findByMedicoId(medicoId);
        return ResponseEntity.ok(diagnosticos);
    }
}
