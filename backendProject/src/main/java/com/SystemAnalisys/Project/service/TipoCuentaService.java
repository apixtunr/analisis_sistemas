package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.entity.TipoCuenta;
import com.SystemAnalisys.Project.repository.TipoCuentaRepository;

@Service
public class TipoCuentaService {

    private final TipoCuentaRepository tipoCuentaRepository;

    public TipoCuentaService(TipoCuentaRepository tipoCuentaRepository) {
        this.tipoCuentaRepository = tipoCuentaRepository;
    }

    // Obtener todos los tipos de cuenta
    public List<TipoCuenta> getAllTipoCuenta() {
        return tipoCuentaRepository.findAll();
    }

    // Buscar por ID
    public Optional<TipoCuenta> findById(Integer idTipoCuenta) {
        return tipoCuentaRepository.findById(idTipoCuenta);
    }

    // Guardar o actualizar
    public TipoCuenta save(TipoCuenta tipoCuenta) {
        return tipoCuentaRepository.save(tipoCuenta);
    }

    // Eliminar
    public void delete(TipoCuenta tipoCuenta) {
        tipoCuentaRepository.delete(tipoCuenta);
    }
}