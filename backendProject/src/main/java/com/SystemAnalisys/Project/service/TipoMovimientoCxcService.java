package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.dto.TipoMovimientoCxcResponse;
import com.SystemAnalisys.Project.repository.TipoMovimientoCxcCustomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoMovimientoCxcService {

    private final TipoMovimientoCxcCustomRepository tipoMovimientoCxcCustomRepository;

    public TipoMovimientoCxcService(TipoMovimientoCxcCustomRepository tipoMovimientoCxcCustomRepository) {
        this.tipoMovimientoCxcCustomRepository = tipoMovimientoCxcCustomRepository;
    }

    public List<TipoMovimientoCxcResponse> getAllTipoMovimientoCxc() {
        return tipoMovimientoCxcCustomRepository.findAllTipoMovimientoCxc();
    }
}