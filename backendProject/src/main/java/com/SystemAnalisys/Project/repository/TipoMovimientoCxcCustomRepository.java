package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.TipoMovimientoCxcResponse;
import java.util.List;

public interface TipoMovimientoCxcCustomRepository {
    List<TipoMovimientoCxcResponse> findAllTipoMovimientoCxc();
}