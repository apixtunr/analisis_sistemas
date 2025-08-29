package com.SystemAnalisys.Project.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.SystemAnalisys.Project.entity.StatusUsuario;
import com.SystemAnalisys.Project.repository.StatusUsuarioRepository;

@Service
public class StatusUsuarioService {
    private final StatusUsuarioRepository statusUsuarioRepository;

    public StatusUsuarioService(StatusUsuarioRepository statusUsuarioRepository) {
        this.statusUsuarioRepository = statusUsuarioRepository;
    }

    public List<StatusUsuario> getAllStatusUsuarios() {
        return (List<StatusUsuario>) statusUsuarioRepository.findAll();
    }

    public Optional<StatusUsuario> findByIdStatusUsuario(Integer idStatusUsuario) {
        return statusUsuarioRepository.findByIdstatususuario(idStatusUsuario);
    }

    public StatusUsuario save(StatusUsuario statusUsuario) {
        return statusUsuarioRepository.save(statusUsuario);
    }

    public void delete(StatusUsuario statusUsuario) {
        statusUsuarioRepository.delete(statusUsuario);
    }

    public Integer getMaxId() {
        return statusUsuarioRepository.findMaxId();
    }
}