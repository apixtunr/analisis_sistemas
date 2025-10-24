package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.StatusCuenta;
import com.SystemAnalisys.Project.repository.StatusCuentaRepository;

@Service
public class StatusCuentaService {

 private final StatusCuentaRepository repository;

    public StatusCuentaService(StatusCuentaRepository repository) {
        this.repository = repository;
    }

    public List<StatusCuenta> listar() {
        return repository.findAll();
    }

    public Optional<StatusCuenta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public StatusCuenta crear(StatusCuenta statusCuenta) {
        return repository.save(statusCuenta);
    }

    public StatusCuenta actualizar(Long id, StatusCuenta nuevo) {
        return repository.findById(id).map(existente -> {
            existente.setNombre(nuevo.getNombre());
            existente.setFechamodificacion(nuevo.getFechamodificacion());
            existente.setUsuariomodificacion(nuevo.getUsuariomodificacion());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Estado no encontrado"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

}
