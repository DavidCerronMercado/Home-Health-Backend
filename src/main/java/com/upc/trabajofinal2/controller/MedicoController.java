package com.upc.trabajofinal2.controller;

import com.upc.trabajofinal2.dto.MedicoRegistroDTO;
import com.upc.trabajofinal2.dto.MedicoResponseDTO;
import com.upc.trabajofinal2.dto.PacienteResponseDTO;
import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Paciente;
import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.entity.Usuario;
import com.upc.trabajofinal2.exception.MedicoExistenteException;
import com.upc.trabajofinal2.exception.PacienteExistenteException;
import com.upc.trabajofinal2.service.MedicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody MedicoRegistroDTO medicoDto) {
        try {
            Medico medico = new Medico();
            medico.setNombre(medicoDto.getNombre());
            medico.setApellido(medicoDto.getApellido());
            medico.setEspecialidad(medicoDto.getEspecialidad());
            medico.setHospital(medicoDto.getHospital());

            Usuario usuario = new Usuario();
            usuario.setUsername(medicoDto.getUsername());
            usuario.setPassword(passwordEncoder.encode(medicoDto.getPassword()));
            usuario.setEnabled(true);

            Rol rolMedico = new Rol();
            rolMedico.setRol("Medico");
            usuario.setRoles(Collections.singleton(rolMedico));

            medico.setUsuario(usuario);
            medicoService.insertar(medico);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (MedicoExistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificar(@RequestBody MedicoRegistroDTO medicoDto) {
        try {
            Medico medico = new Medico();
            medico.setId(medicoDto.getId());
            medico.setNombre(medicoDto.getNombre());
            medico.setApellido(medicoDto.getApellido());
            medico.setEspecialidad(medicoDto.getEspecialidad());
            medico.setHospital(medicoDto.getHospital());


            Usuario usuario = new Usuario();
            usuario.setUsername(medicoDto.getUsername());
            usuario.setPassword(passwordEncoder.encode(medicoDto.getPassword()));
            usuario.setEnabled(true);

            medico.setUsuario(usuario);
            medicoService.modificar(medico);
            return ResponseEntity.ok().build();
        } catch (MedicoExistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        medicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<MedicoResponseDTO> listarId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Medico medico = medicoService.listarId(id);
        MedicoResponseDTO medicoDto = modelMapper.map(medico, MedicoResponseDTO.class);
        if (medico.getUsuario() != null) {
            medicoDto.setUsername(medico.getUsuario().getUsername());
            medicoDto.setPassword(medico.getUsuario().getPassword());
        }
        return ResponseEntity.ok(medicoDto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MedicoResponseDTO>> listar() {
        List<Medico> medicos = medicoService.listar();
        List<MedicoResponseDTO> medicosDto = medicos.stream()
                .map(medico -> {
                    ModelMapper modelMapper = new ModelMapper();
                    MedicoResponseDTO medicoDto = modelMapper.map(medico, MedicoResponseDTO.class);
                    if (medico.getUsuario() != null) {
                        medicoDto.setUsername(medico.getUsuario().getUsername());
                        medicoDto.setPassword(medico.getUsuario().getPassword());
                    }
                    return medicoDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(medicosDto);
    }

    @GetMapping("/buscarApellido/{apellido}")
    public ResponseEntity<List<MedicoResponseDTO>> buscarPorApellido(@PathVariable("apellido") String apellido) {
        List<Medico> medicos = medicoService.findByApellido(apellido);
        List<MedicoResponseDTO> medicosDto = medicos.stream()
                .map(medico -> {
                    ModelMapper modelMapper = new ModelMapper();
                    MedicoResponseDTO medicoDto = modelMapper.map(medico, MedicoResponseDTO.class);
                    if (medico.getUsuario() != null) {
                        medicoDto.setUsername(medico.getUsuario().getUsername());
                        medicoDto.setPassword(medico.getUsuario().getPassword());
                    }
                    return medicoDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(medicosDto);
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<MedicoResponseDTO>> buscarPorNombre(@PathVariable("nombre") String nombre) {
        List<Medico> medicos = medicoService.findByNombreContaining(nombre);
        List<MedicoResponseDTO> medicosDto = medicos.stream()
                .map(medico -> {
                    ModelMapper modelMapper = new ModelMapper();
                    MedicoResponseDTO medicoDto = modelMapper.map(medico, MedicoResponseDTO.class);
                    if (medico.getUsuario() != null) {
                        medicoDto.setUsername(medico.getUsuario().getUsername());
                        medicoDto.setPassword(medico.getUsuario().getPassword());
                    }
                    return medicoDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(medicosDto);
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam boolean enabled) {
        medicoService.cambiarEstadoUsuario(id, enabled);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscarPorUsername/{username}")
    public ResponseEntity<MedicoResponseDTO> buscarPorUsername(@PathVariable("username") String username) {
        ModelMapper modelMapper = new ModelMapper();
        Medico medico = medicoService.findByUsername(username);
        MedicoResponseDTO medicoDto = modelMapper.map(medico, MedicoResponseDTO.class);
        if (medico.getUsuario() != null) {
            medicoDto.setUsername(medico.getUsuario().getUsername());
            medicoDto.setPassword(medico.getUsuario().getPassword());
        }
        return ResponseEntity.ok(medicoDto);
    }
}
