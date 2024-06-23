package com.upc.trabajofinal2.serviceImpl;

import com.upc.trabajofinal2.entity.Paciente;
import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.entity.Usuario;
import com.upc.trabajofinal2.exception.PacienteExistenteException;
import com.upc.trabajofinal2.repository.PacienteRepository;
import com.upc.trabajofinal2.repository.RolRepository;
import com.upc.trabajofinal2.repository.UsuarioRepository;
import com.upc.trabajofinal2.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public void insertar(Paciente paciente) {
        if (pacienteRepository.findByDni(paciente.getDni()) != null) {
            throw new PacienteExistenteException("El paciente con DNI " + paciente.getDni() + " ya existe.");
        }
        Usuario usuario = paciente.getUsuario();
        Rol rolPaciente = rolRepository.findByRol("Paciente");
        if (rolPaciente == null) {
            rolPaciente = new Rol();
            rolPaciente.setRol("Paciente");
            rolRepository.save(rolPaciente);
        }
        usuario.setRoles(Collections.singleton(rolPaciente));
        usuarioRepository.save(usuario);
        pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (paciente != null) {
            Usuario usuario = paciente.getUsuario();
            if (usuario != null) {
                usuario.getRoles().clear();
                usuarioRepository.save(usuario);
                usuarioRepository.delete(usuario);
            }
            pacienteRepository.delete(paciente);
        }
    }

    @Override
    public Paciente listarId(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }
    @Override
    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente findByDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    @Override
    public List<Paciente> findByApellido(String apellido) {
        return pacienteRepository.findByApellido(apellido);
    }

    @Override
    public List<Paciente> findByNombreContaining(String nombre) {
        return pacienteRepository.findByNombreContaining(nombre);
    }

    @Override
    public List<Paciente> findPacientesWithRecentDiagnosticos(LocalDate startDate, LocalDate endDate) {
        return pacienteRepository.findPacientesWithRecentDiagnosticos(startDate, endDate);
    }

    @Override
    public Long countPacientesByHospital(String hospital) {
        return pacienteRepository.countPacientesByHospital(hospital);
    }

    @Override
    public void modificar(Paciente paciente) {
        Paciente pacienteExistente = pacienteRepository.findById(paciente.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        pacienteExistente.setNombre(paciente.getNombre());
        pacienteExistente.setApellido(paciente.getApellido());
        pacienteExistente.setDni(paciente.getDni());
        pacienteExistente.setFechaNacimiento(paciente.getFechaNacimiento());

        Usuario usuarioExistente = pacienteExistente.getUsuario();
        usuarioExistente.setUsername(paciente.getUsuario().getUsername());
        usuarioExistente.setPassword(paciente.getUsuario().getPassword());
        usuarioRepository.save(usuarioExistente);

        pacienteRepository.save(pacienteExistente);
    }

    @Override
    public boolean existeDni(String dni) {
        return pacienteRepository.findByDni(dni) != null;
    }

    @Override
    public void cambiarEstadoUsuario(Long id, boolean enabled) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Usuario usuario = paciente.getUsuario();
        if (usuario != null) {
            usuario.setEnabled(enabled);
            usuarioRepository.save(usuario);
        }
    }
    @Override
    public Paciente findByUsername(String username) {
        return pacienteRepository.findByUsuarioUsername(username);
    }
}
