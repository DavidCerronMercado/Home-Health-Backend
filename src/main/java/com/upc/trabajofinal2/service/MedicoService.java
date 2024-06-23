package com.upc.trabajofinal2.service;

import com.upc.trabajofinal2.entity.Medico;

import java.util.List;

public interface MedicoService {
    void insertar(Medico medico);
    void eliminar(Long id);
    Medico listarId(Long id);
    List<Medico> listar();
    List<Medico> findByApellido(String apellido);
    List<Medico> findByNombreContaining(String nombre);
    void modificar(Medico medico);
    void cambiarEstadoUsuario(Long id, boolean enabled);
    Medico findByUsername(String username);
}
