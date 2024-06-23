package com.upc.trabajofinal2.service;

import com.upc.trabajofinal2.entity.Rol;

import java.util.List;

public interface RolService {
    void insertar(Rol rol);
    void eliminar(Long id);
    Rol listarId(Long id);
    List<Rol> listar();
}
