package com.upc.trabajofinal2.serviceImpl;

import com.upc.trabajofinal2.entity.Medico;
import com.upc.trabajofinal2.entity.Rol;
import com.upc.trabajofinal2.entity.Usuario;
import com.upc.trabajofinal2.exception.MedicoExistenteException;
import com.upc.trabajofinal2.repository.MedicoRepository;
import com.upc.trabajofinal2.repository.RolRepository;
import com.upc.trabajofinal2.repository.UsuarioRepository;
import com.upc.trabajofinal2.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void insertar(Medico medico) {
        if (medicoRepository.findByUsuarioUsername(medico.getUsuario().getUsername()) != null) {
            throw new MedicoExistenteException("El médico con username " + medico.getUsuario().getUsername() + " ya existe.");
        }
        Usuario usuario = medico.getUsuario();
        Rol rolMedico = rolRepository.findByRol("Medico");
        if (rolMedico == null) {
            rolMedico = new Rol();
            rolMedico.setRol("Medico");
            rolRepository.save(rolMedico);
        }
        usuario.setRoles(Collections.singleton(rolMedico));
        usuarioRepository.save(usuario);
        medicoRepository.save(medico);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Medico medico = medicoRepository.findById(id).orElse(null);
        if (medico != null) {
            Usuario usuario = medico.getUsuario();
            if (usuario != null) {
                usuario.getRoles().clear();
                usuarioRepository.save(usuario);
                usuarioRepository.delete(usuario);
            }
            medicoRepository.delete(medico);
        }
    }

    @Override
    public Medico listarId(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Medico> listar() {
        return medicoRepository.findAll();
    }

    @Override
    public List<Medico> findByApellido(String apellido) {
        return medicoRepository.findByApellido(apellido);
    }

    @Override
    public List<Medico> findByNombreContaining(String nombre) {
        return medicoRepository.findByNombreContaining(nombre);
    }

    @Override
    public void modificar(Medico medico) {
        Medico medicoExistente = medicoRepository.findById(medico.getId())
                .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        medicoExistente.setNombre(medico.getNombre());
        medicoExistente.setApellido(medico.getApellido());

        Usuario usuarioExistente = medicoExistente.getUsuario();
        usuarioExistente.setUsername(medico.getUsuario().getUsername());
        usuarioExistente.setPassword(medico.getUsuario().getPassword());
        usuarioRepository.save(usuarioExistente);

        medicoRepository.save(medicoExistente);
    }

    @Override
    public void cambiarEstadoUsuario(Long id, boolean enabled) {
        Medico medico = medicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        Usuario usuario = medico.getUsuario();
        if (usuario != null) {
            usuario.setEnabled(enabled);
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public Medico findByUsername(String username) {
        return medicoRepository.findByUsuarioUsername(username);
    }
}
