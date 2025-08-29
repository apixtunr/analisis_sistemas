package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.dto.UsuarioDTO;
import com.SystemAnalisys.Project.entity.Usuario;
import com.SystemAnalisys.Project.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String par_id) {
        return usuarioRepository.findById(par_id);
    }

    public Usuario save(Usuario par_usuario) {
        return usuarioRepository.save(par_usuario);
    }

    public void delete(Usuario par_usuario) {
        usuarioRepository.delete(par_usuario);
    }

    public Usuario login(Usuario usuario) {
        Optional<Usuario> listUsuario = usuarioRepository
                .findByCorreoElectronicoAndPassword(usuario.getCorreoElectronico(), usuario.getPassword());
        Usuario usuarioReturn = null;

        if (listUsuario.isPresent()) {
            usuarioReturn = listUsuario.get();
        }
        return usuarioReturn;
    }

    @Transactional
    public void actualizarRolUsuario(String idUsuario, Integer idRole) {
        usuarioRepository.actualizarRolUsuario(idUsuario, idRole);
    }

    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> lista = new java.util.ArrayList<>();
        for (Usuario u : usuarios) {
            lista.add(new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido()));
        }
        return lista;
    }

    /*Devuelve una lista de Usuarios que pertenecen a un rol específico*/
public List<UsuarioDTO> getUsuariosPorRol(Integer idRole) {
    List<Usuario> usuarios = usuarioRepository.findAll();
    List<UsuarioDTO> lista = new java.util.ArrayList<>();
    for (Usuario u : usuarios) {
        if (u.getIdRole() != null && u.getIdRole().equals(idRole)) {
            lista.add(new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido()));
        }
    }
    return lista;
}
}
