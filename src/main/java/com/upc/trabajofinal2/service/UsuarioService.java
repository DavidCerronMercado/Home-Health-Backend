package com.upc.trabajofinal2.service;

import com.upc.trabajofinal2.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    void insertar(Usuario usuario);
    void eliminar(Long id);
    Usuario listarId(Long id);
    List<Usuario> listar();
    Usuario findByUsername(String username);
    List<Usuario> findByRole(String rol);

}
