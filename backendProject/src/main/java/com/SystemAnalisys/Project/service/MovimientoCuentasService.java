package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;
import com.SystemAnalisys.Project.repository.MovimientoCuentasCustomRepository;
import org.springframework.stereotype.Service;

@Service
public class MovimientoCuentasService {

    private final MovimientoCuentasCustomRepository movimientoCuentasCustomRepository;

    public MovimientoCuentasService(MovimientoCuentasCustomRepository movimientoCuentasCustomRepository) {
        this.movimientoCuentasCustomRepository = movimientoCuentasCustomRepository;
    }

    public RegistroMovimientoResponse registrarMovimiento(RegistroMovimientoRequest request) {
        return movimientoCuentasCustomRepository.ejecutarRegistroMovimientos(request);
    }
}