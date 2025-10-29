package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;
import com.SystemAnalisys.Project.service.MovimientoCuentasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/movimientos-cuenta")
public class MovimientoCuentasController {

    private final MovimientoCuentasService movimientoCuentasService;

    public MovimientoCuentasController(MovimientoCuentasService movimientoCuentasService) {
        this.movimientoCuentasService = movimientoCuentasService;
    }

    @PostMapping
    public ResponseEntity<RegistroMovimientoResponse> registrarMovimiento(@Validated @RequestBody RegistroMovimientoRequest request) {
        RegistroMovimientoResponse response = movimientoCuentasService.registrarMovimiento(request);
        if (response.getExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}