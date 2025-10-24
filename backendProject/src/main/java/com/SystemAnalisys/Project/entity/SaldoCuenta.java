package com.SystemAnalisys.Project.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// Relaciones a otras entidades omitidas intencionalmente
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "saldo_cuenta", schema = "proyectoanalisis")
public class SaldoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsaldocuenta")
    private Integer idsaldocuenta;

    @Column(name = "idpersona", nullable = false)
    private Integer idpersona;

    @Column(name = "idstatuscuenta", nullable = false)
    private Long idstatuscuenta;

    @Column(name = "idtiposaldocuenta", nullable = false)
    private Integer idtiposaldocuenta;

    @Column(name = "saldoanterior")
    private java.math.BigDecimal saldoanterior;

    @Column(name = "debitos")
    private java.math.BigDecimal debitos;

    @Column(name = "creditos")
    private java.math.BigDecimal creditos;

    @Column(name = "fechacreacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuariocreacion;

    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuariomodificacion;

    // Relaciones opcionales para facilitar joins desde JPA
        // Notas: No se incluyen referencias directas a las entidades Persona/StatusCuenta/TipoSaldoCuenta
        // para evitar dependencias si esas entidades no están definidas en el proyecto.
}
