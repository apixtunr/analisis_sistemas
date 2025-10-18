package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;

public interface MovimientoCuentasCustomRepository {
    RegistroMovimientoResponse ejecutarRegistroMovimientos(RegistroMovimientoRequest request);
}