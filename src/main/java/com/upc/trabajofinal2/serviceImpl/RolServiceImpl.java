package com.upc.trabajofinal2.serviceImpl;

import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.repository.RolRepository;
import com.upc.trabajofinal2.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public void insertar(Rol rol) {
        rolRepository.save(rol);
    }

    @Override
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Rol listarId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rol> listar() {
        return rolRepository.findAll();
    }
}
