package com.upc.trabajofinal2.initializer;

import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.findByRol("Paciente") == null) {
            Rol rolPaciente = new Rol();
            rolPaciente.setRol("Paciente");
            rolRepository.save(rolPaciente);
        }

        if (rolRepository.findByRol("Medico") == null) {
            Rol rolMedico = new Rol();
            rolMedico.setRol("Medico");
            rolRepository.save(rolMedico);
        }

        if (rolRepository.findByRol("Admin") == null) {
            Rol rolAdmin = new Rol();
            rolAdmin.setRol("Admin");
            rolRepository.save(rolAdmin);
        }
    }
}
