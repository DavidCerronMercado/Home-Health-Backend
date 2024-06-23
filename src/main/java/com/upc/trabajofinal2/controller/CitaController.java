package com.upc.trabajofinal2.controller;

import com.upc.trabajofinal2.dto.CitaRegistroDTO;
import com.upc.trabajofinal2.dto.CitaResponseDTO;
import com.upc.trabajofinal2.entity.Cita;
import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Paciente;
import com.upc.trabajofinal2.service.CitaService;
import com.upc.trabajofinal2.service.MedicoService;
import com.upc.trabajofinal2.service.PacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/citas")
public class CitaController {
    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody CitaRegistroDTO citaRegistroDto) {
        Cita cita = new Cita();
        cita.setFecha(citaRegistroDto.getFecha());
        cita.setTurno(citaRegistroDto.getTurno());
        cita.setPrecio(citaRegistroDto.getPrecio());

        Paciente paciente = pacienteService.findByDni(citaRegistroDto.getDniPaciente());
        if (paciente == null) {
            return ResponseEntity.badRequest().build(); // o lanzar una excepción personalizada
        }
        cita.setPaciente(paciente);

        Medico medico = medicoService.listarId(citaRegistroDto.getIdMedico());
        if (medico == null) {
            return ResponseEntity.badRequest().build(); // o lanzar una excepción personalizada
        }
        cita.setMedico(medico);

        citaService.insertar(cita);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/modificar")
    public ResponseEntity<Void> modificar(@RequestBody CitaRegistroDTO citaRegistroDto) {
        Cita cita = new Cita();
        cita.setId(citaRegistroDto.getId());
        cita.setFecha(citaRegistroDto.getFecha());
        cita.setTurno(citaRegistroDto.getTurno());
        cita.setPrecio(citaRegistroDto.getPrecio());

        Paciente paciente = pacienteService.findByDni(citaRegistroDto.getDniPaciente());
        if (paciente == null) {
            return ResponseEntity.badRequest().build(); // o lanzar una excepción personalizada
        }
        cita.setPaciente(paciente);

        Medico medico = medicoService.listarId(citaRegistroDto.getIdMedico());
        if (medico == null) {
            return ResponseEntity.badRequest().build(); // o lanzar una excepción personalizada
        }
        cita.setMedico(medico);

        citaService.insertar(cita);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<CitaResponseDTO> listarId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Cita cita = citaService.listarId(id);
        CitaResponseDTO citaDto = modelMapper.map(cita, CitaResponseDTO.class);
        citaDto.setNombrePaciente(cita.getPaciente().getNombre());
        citaDto.setDniPaciente(cita.getPaciente().getDni());
        citaDto.setNombreMedico(cita.getMedico().getNombre());
        citaDto.setEspecialidadMedico(cita.getMedico().getEspecialidad());
        citaDto.setHospitalMedico(cita.getMedico().getHospital());
        citaDto.setPrecio(cita.getPrecio());
        return ResponseEntity.ok(citaDto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CitaResponseDTO>> listar() {
        List<Cita> citas = citaService.listar();
        List<CitaResponseDTO> citasDto = citas.stream()
                .map(cita -> {
                    ModelMapper modelMapper = new ModelMapper();
                    CitaResponseDTO citaDto = modelMapper.map(cita, CitaResponseDTO.class);
                    citaDto.setNombrePaciente(cita.getPaciente().getNombre());
                    citaDto.setDniPaciente(cita.getPaciente().getDni());
                    citaDto.setNombreMedico(cita.getMedico().getNombre());
                    citaDto.setEspecialidadMedico(cita.getMedico().getEspecialidad());
                    citaDto.setHospitalMedico(cita.getMedico().getHospital());
                    citaDto.setPrecio(cita.getPrecio());
                    return citaDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(citasDto);
    }



    @GetMapping("/listarPorPaciente/{pacienteId}")
    public ResponseEntity<List<CitaResponseDTO>> listarPorPaciente(@PathVariable("pacienteId") Long pacienteId) {
        List<Cita> citas = citaService.findCitasByPaciente(pacienteId);
        List<CitaResponseDTO> citasDto = citas.stream()
                .map(cita -> {
                    ModelMapper modelMapper = new ModelMapper();
                    CitaResponseDTO citaDto = modelMapper.map(cita, CitaResponseDTO.class);
                    citaDto.setNombrePaciente(cita.getPaciente().getNombre());
                    citaDto.setDniPaciente(cita.getPaciente().getDni());
                    citaDto.setNombreMedico(cita.getMedico().getNombre());
                    citaDto.setEspecialidadMedico(cita.getMedico().getEspecialidad());
                    citaDto.setHospitalMedico(cita.getMedico().getHospital());
                    citaDto.setPrecio(cita.getPrecio());
                    return citaDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(citasDto);
    }

    @GetMapping("/buscarPorFechas")
    public ResponseEntity<List<CitaResponseDTO>> buscarPorFechas(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<Cita> citas = citaService.findCitasByFechaBetween(startDate, endDate);
        List<CitaResponseDTO> citasDto = citas.stream()
                .map(cita -> {
                    ModelMapper modelMapper = new ModelMapper();
                    CitaResponseDTO citaDto = modelMapper.map(cita, CitaResponseDTO.class);
                    citaDto.setNombrePaciente(cita.getPaciente().getNombre());
                    citaDto.setDniPaciente(cita.getPaciente().getDni());
                    citaDto.setNombreMedico(cita.getMedico().getNombre());
                    citaDto.setEspecialidadMedico(cita.getMedico().getEspecialidad());
                    citaDto.setHospitalMedico(cita.getMedico().getHospital());
                    citaDto.setPrecio(cita.getPrecio());
                    return citaDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(citasDto);
    }
    @GetMapping("/verificarDisponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(@RequestParam("medicoId") Long medicoId, @RequestParam("fecha") LocalDate fecha, @RequestParam("turno") String turno) {
        Boolean disponible = citaService.isHorarioDisponible(medicoId, fecha, turno);
        return ResponseEntity.ok(disponible);
    }

    @GetMapping("/contarCitasPorMedico")
    public ResponseEntity<Long> contarCitasPorMedico(@RequestParam("medicoId") Long medicoId, @RequestParam("fecha") LocalDate fecha) {
        Long count = citaService.countCitasByMedicoAndFecha(medicoId, fecha);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/listarPorMedicoo/{medicoId}")
    public ResponseEntity<List<CitaResponseDTO>> listarPorMedicoo(@PathVariable("medicoId") Long medicoId) {
        List<Cita> citas = citaService.findCitasByMedicoo(medicoId);
        List<CitaResponseDTO> citasDto = citas.stream()
                .map(cita -> {
                    ModelMapper modelMapper = new ModelMapper();
                    CitaResponseDTO citaDto = modelMapper.map(cita, CitaResponseDTO.class);
                    citaDto.setNombrePaciente(cita.getPaciente().getNombre());
                    citaDto.setDniPaciente(cita.getPaciente().getDni());
                    citaDto.setNombreMedico(cita.getMedico().getNombre());
                    citaDto.setEspecialidadMedico(cita.getMedico().getEspecialidad());
                    citaDto.setHospitalMedico(cita.getMedico().getHospital());
                    citaDto.setPrecio(cita.getPrecio());
                    return citaDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(citasDto);
    }
}

