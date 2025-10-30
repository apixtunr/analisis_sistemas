package com.SystemAnalisys.Project.controller;


import com.SystemAnalisys.Project.dto.TipoMovimientoCxcResponse;
import com.SystemAnalisys.Project.service.TipoMovimientoCxcService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-movimiento-cxc") // Ejemplo de endpoint
public class TipoMovimientoCxcController {

    private final TipoMovimientoCxcService tipoMovimientoCxcService;

    public TipoMovimientoCxcController(TipoMovimientoCxcService tipoMovimientoCxcService) {
        this.tipoMovimientoCxcService = tipoMovimientoCxcService;
    }

    @GetMapping
    public ResponseEntity<List<TipoMovimientoCxcResponse>> getAllTipoMovimientoCxc() {
        List<TipoMovimientoCxcResponse> movimientos = tipoMovimientoCxcService.getAllTipoMovimientoCxc();
        return ResponseEntity.ok(movimientos);
    }
}