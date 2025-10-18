package com.SystemAnalisys.Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroMovimientoRequest {
    private Integer idPersona;
    private Integer idSldCuenta;
    private Integer idTM; // idTipoMovimiento
    private LocalDateTime fecha;
    private BigDecimal monto;
    private String descripcion;
    private String user;
}