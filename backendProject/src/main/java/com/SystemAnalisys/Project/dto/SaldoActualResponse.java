package com.SystemAnalisys.Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoActualResponse {
    private Integer idsaldocuenta;
    private BigDecimal saldoAnterior;
    private BigDecimal debitos;
    private BigDecimal creditos;
    private BigDecimal saldoActual;
}
