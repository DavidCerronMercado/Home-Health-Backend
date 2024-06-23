package com.upc.trabajofinal2.controller;

import com.upc.trabajofinal2.dto.RolDTO;
import com.upc.trabajofinal2.dto.UsuarioResponseDTO;
import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.entity.Usuario;
import com.upc.trabajofinal2.service.RolService;
import com.upc.trabajofinal2.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody RolDTO rolDto) {
        ModelMapper modelMapper = new ModelMapper();
        Rol rol = modelMapper.map(rolDto, Rol.class);
        rolService.insertar(rol);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/modificar")
    public ResponseEntity<Void> modificar(@RequestBody RolDTO rolDto) {
        ModelMapper modelMapper = new ModelMapper();
        Rol rol = modelMapper.map(rolDto, Rol.class);
        rolService.insertar(rol);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<RolDTO> listarId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Rol rol = rolService.listarId(id);
        RolDTO rolDto = modelMapper.map(rol, RolDTO.class);
        return ResponseEntity.ok(rolDto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RolDTO>> listar() {
        List<Rol> roles = rolService.listar();
        List<RolDTO> rolesDto = roles.stream()
                .map(rol -> new ModelMapper().map(rol, RolDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(rolesDto);
    }

    @GetMapping("/listarUsuariosPorRol/{rol}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosPorRol(@PathVariable("rol") String rol) {
        List<Usuario> usuarios = usuarioService.findByRole(rol);
        List<UsuarioResponseDTO> usuariosDto = usuarios.stream()
                .map(usuario -> new ModelMapper().map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDto);
    }
}
