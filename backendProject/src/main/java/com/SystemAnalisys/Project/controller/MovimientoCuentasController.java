package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.dto.EstadoDeCuentaDTO;
import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;
import com.SystemAnalisys.Project.service.MovimientoCuentasService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos-cuenta")
public class MovimientoCuentasController {

    private final MovimientoCuentasService movimientoCuentasService;

    public MovimientoCuentasController(MovimientoCuentasService movimientoCuentasService) {
        this.movimientoCuentasService = movimientoCuentasService;
    }

    // Endpoint para registrar movimiento (existente)
    @PostMapping
    public ResponseEntity<RegistroMovimientoResponse> registrarMovimiento(
            @Validated @RequestBody RegistroMovimientoRequest request) {
        RegistroMovimientoResponse response = movimientoCuentasService.registrarMovimiento(request);
        if (response.getExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Nuevo endpoint para consultar movimientos filtrados
    @GetMapping("/estado-cuenta")
    public ResponseEntity<List<EstadoDeCuentaDTO>> obtenerEstadoCuenta(
            @RequestParam Integer idCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin
    ) {
        List<EstadoDeCuentaDTO> movimientos = movimientoCuentasService
                .obtenerMovimientosPorCuentaYFechas(idCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(movimientos);
    }
}
