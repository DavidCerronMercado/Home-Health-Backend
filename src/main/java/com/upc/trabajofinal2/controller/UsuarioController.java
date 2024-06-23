package com.upc.trabajofinal2.controller;

import com.upc.trabajofinal2.dto.UsuarioDTO;
import com.upc.trabajofinal2.dto.UsuarioResponseDTO;
import com.upc.trabajofinal2.entity.Usuario;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody UsuarioDTO usuarioDto) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuarioService.insertar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/modificar")
    public ResponseEntity<Void> modificar(@RequestBody UsuarioDTO usuarioDto) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuarioService.insertar(usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<UsuarioDTO> listarId(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = usuarioService.listarId(id);
        UsuarioDTO usuarioDto = modelMapper.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<Usuario> usuarios = usuarioService.listar();
        List<UsuarioDTO> usuariosDto = usuarios.stream()
                .map(usuario -> new ModelMapper().map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDto);
    }

    @GetMapping("/buscarUsername/{username}")
    public ResponseEntity<UsuarioDTO> buscarPorUsername(@PathVariable("username") String username) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO usuarioDto = modelMapper.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDto);
    }
}
