package com.SystemAnalisys.Project.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SaldoCuentaRequest {
    private Integer idpersona;
    private Long idstatuscuenta;
    private Integer idtiposaldocuenta;
    private BigDecimal saldoanterior;
    private BigDecimal debitos;
    private BigDecimal creditos;
    private Date fechacreacion;
    private String usuariocreacion;
}
