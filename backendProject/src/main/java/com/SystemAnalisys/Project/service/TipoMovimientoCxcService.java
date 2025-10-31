package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.dto.TipoMovimientoCxcResponse;
import com.SystemAnalisys.Project.entity.TipoMovimientosCxc;
import com.SystemAnalisys.Project.repository.TipoMovimientosCxcRepository;
import com.SystemAnalisys.Project.repository.TipoMovimientoCxcCustomRepository;


@Service
public class TipoMovimientoCxcService {

    private final TipoMovimientosCxcRepository repository;
    private final TipoMovimientoCxcCustomRepository tipoMovimientoCxcCustomRepository;

    public TipoMovimientoCxcService(TipoMovimientosCxcRepository repository, TipoMovimientoCxcCustomRepository tipoMovimientoCxcCustomRepository) {
        this.repository = repository;
        this.tipoMovimientoCxcCustomRepository = tipoMovimientoCxcCustomRepository;
    }

    // Listar todos los tipos de movimiento
    public List<TipoMovimientosCxc> listar() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<TipoMovimientosCxc> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Crear nuevo registro
    public TipoMovimientosCxc crear(TipoMovimientosCxc tipoMovimiento) {
        return repository.save(tipoMovimiento);
    }

    // Actualizar existente
    public TipoMovimientosCxc actualizar(Long id, TipoMovimientosCxc nuevo) {
        return repository.findById(id).map(existente -> {
            existente.setNombre(nuevo.getNombre());
            existente.setOperacionCuentaCorriente(nuevo.getOperacionCuentaCorriente());
            existente.setFechaModificacion(nuevo.getFechaModificacion());
            existente.setUsuarioModificacion(nuevo.getUsuarioModificacion());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Tipo de movimiento no encontrado"));
    }

    public List<TipoMovimientoCxcResponse> getAllTipoMovimientoCxc() {
        return tipoMovimientoCxcCustomRepository.findAllTipoMovimientoCxc();
    }

    // Eliminar por ID
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}