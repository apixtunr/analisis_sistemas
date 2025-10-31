package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.dto.EstadoDeCuentaDTO;
import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;
import com.SystemAnalisys.Project.repository.MovimientoCuentasCustomRepository;

import java.time.LocalDateTime;
import java.util.List;

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

     public List<EstadoDeCuentaDTO> obtenerMovimientosPorCuentaYFechas(
            Integer idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoCuentasCustomRepository.obtenerMovimientosPorCuentaYFechas(
                idCuenta, fechaInicio, fechaFin
        );
    }
}