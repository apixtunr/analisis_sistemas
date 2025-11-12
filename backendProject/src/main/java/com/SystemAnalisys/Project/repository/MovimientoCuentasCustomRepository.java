package com.SystemAnalisys.Project.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.SystemAnalisys.Project.dto.EstadoDeCuentaDTO;
import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;

public interface MovimientoCuentasCustomRepository {
    RegistroMovimientoResponse ejecutarRegistroMovimientos(RegistroMovimientoRequest request);

    List<EstadoDeCuentaDTO> obtenerMovimientosPorCuentaYFechas(
        Integer idCuenta,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
    );

    Map<String, Object> obtenerEstadoCuentaConSaldos(
        Integer idCuenta,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
    );
}