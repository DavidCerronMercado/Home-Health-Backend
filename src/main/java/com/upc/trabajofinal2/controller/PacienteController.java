package com.upc.trabajofinal2.controller;

import com.upc.trabajofinal2.dto.PacienteDTO;
import com.upc.trabajofinal2.dto.PacienteRegistroDTO;
import com.upc.trabajofinal2.dto.PacienteResponseDTO;
import com.upc.trabajofinal2.dto.UsuarioResponseDTO;
import com.upc.trabajofinal2.entity.Paciente;
import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.entity.Usuario;
import com.upc.trabajofinal2.exception.PacienteExistenteException;
import com.upc.trabajofinal2.service.PacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody PacienteRegistroDTO pacienteDto) {
        try {
            Paciente paciente = new Paciente();
            paciente.setNombre(pacienteDto.getNombre());
            paciente.setApellido(pacienteDto.getApellido());
            paciente.setDni(pacienteDto.getDni());
            paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());

            Usuario usuario = new Usuario();
            usuario.setUsername(pacienteDto.getUsername());
            usuario.setPassword(passwordEncoder.encode(pacienteDto.getPassword()));
            usuario.setEnabled(true);

            Rol rolPaciente = new Rol();
            rolPaciente.setRol("Paciente");
            usuario.setRoles(Collections.singleton(rolPaciente));

            paciente.setUsuario(usuario);
            pacienteService.insertar(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PacienteExistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/modificar")
    public ResponseEntity<String> modificar(@RequestBody PacienteRegistroDTO pacienteDto) {
        try {
            Paciente paciente = new Paciente();
            paciente.setId(pacienteDto.getId());
            paciente.setNombre(pacienteDto.getNombre());
            paciente.setApellido(pacienteDto.getApellido());
            paciente.setDni(pacienteDto.getDni());
            paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());

            Usuario usuario = new Usuario();
            usuario.setUsername(pacienteDto.getUsername());
            usuario.setPassword(passwordEncoder.encode(pacienteDto.getPassword()));
            usuario.setEnabled(true);

            paciente.setUsuario(usuario);
            pacienteService.modificar(paciente);
            return ResponseEntity.ok().build();
        } catch (PacienteExistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<PacienteResponseDTO> listarId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Paciente paciente = pacienteService.listarId(id);
        PacienteResponseDTO pacienteDto = modelMapper.map(paciente, PacienteResponseDTO.class);
        if (paciente.getUsuario() != null) {
            pacienteDto.setUsername(paciente.getUsuario().getUsername());
            pacienteDto.setPassword(paciente.getUsuario().getPassword());
        }
        return ResponseEntity.ok(pacienteDto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteResponseDTO>> listar() {
        List<Paciente> pacientes = pacienteService.listar();
        List<PacienteResponseDTO> pacientesDto = pacientes.stream()
                .map(paciente -> {
                    ModelMapper modelMapper = new ModelMapper();
                    PacienteResponseDTO pacienteDto = modelMapper.map(paciente, PacienteResponseDTO.class);
                    if (paciente.getUsuario() != null) {
                        pacienteDto.setUsername(paciente.getUsuario().getUsername());
                        pacienteDto.setPassword(paciente.getUsuario().getPassword());
                    }
                    return pacienteDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientesDto);
    }

    @GetMapping("/buscarApellido/{apellido}")
    public ResponseEntity<List<PacienteResponseDTO>> buscarPorApellido(@PathVariable("apellido") String apellido) {
        List<Paciente> pacientes = pacienteService.findByApellido(apellido);
        List<PacienteResponseDTO> pacientesDto = pacientes.stream()
                .map(paciente -> {
                    ModelMapper modelMapper = new ModelMapper();
                    PacienteResponseDTO pacienteDto = modelMapper.map(paciente, PacienteResponseDTO.class);
                    if (paciente.getUsuario() != null) {
                        pacienteDto.setUsername(paciente.getUsuario().getUsername());
                        pacienteDto.setPassword(paciente.getUsuario().getPassword());
                    }
                    return pacienteDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientesDto);
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<PacienteResponseDTO>> buscarPorNombre(@PathVariable("nombre") String nombre) {
        List<Paciente> pacientes = pacienteService.findByNombreContaining(nombre);
        List<PacienteResponseDTO> pacientesDto = pacientes.stream()
                .map(paciente -> {
                    ModelMapper modelMapper = new ModelMapper();
                    PacienteResponseDTO pacienteDto = modelMapper.map(paciente, PacienteResponseDTO.class);
                    if (paciente.getUsuario() != null) {
                        pacienteDto.setUsername(paciente.getUsuario().getUsername());
                        pacienteDto.setPassword(paciente.getUsuario().getPassword());
                    }
                    return pacienteDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientesDto);
    }

    @GetMapping("/recentDiagnosticos")
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientesConDiagnosticosRecientes(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<Paciente> pacientes = pacienteService.findPacientesWithRecentDiagnosticos(startDate, endDate);
        List<PacienteResponseDTO> pacientesDto = pacientes.stream()
                .map(paciente -> {
                    ModelMapper modelMapper = new ModelMapper();
                    PacienteResponseDTO pacienteDto = modelMapper.map(paciente, PacienteResponseDTO.class);
                    if (paciente.getUsuario() != null) {
                        pacienteDto.setUsername(paciente.getUsuario().getUsername());
                        pacienteDto.setPassword(paciente.getUsuario().getPassword());
                    }
                    return pacienteDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientesDto);
    }

    @GetMapping("/countByHospital/{hospital}")
    public ResponseEntity<Long> contarPacientesPorHospital(@PathVariable("hospital") String hospital) {
        Long count = pacienteService.countPacientesByHospital(hospital);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam boolean enabled) {
        pacienteService.cambiarEstadoUsuario(id, enabled);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/buscarPorUsername/{username}")
    public ResponseEntity<PacienteResponseDTO> buscarPorUsername(@PathVariable("username") String username) {
        ModelMapper modelMapper = new ModelMapper();
        Paciente paciente = pacienteService.findByUsername(username);
        PacienteResponseDTO pacienteDto = modelMapper.map(paciente, PacienteResponseDTO.class);
        if (paciente.getUsuario() != null) {
            pacienteDto.setUsername(paciente.getUsuario().getUsername());
            pacienteDto.setPassword(paciente.getUsuario().getPassword());
        }
        return ResponseEntity.ok(pacienteDto);
    }
}
