package com.SystemAnalisys.Project.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tipo_saldo_cuenta", schema = "proyectoanalisis")
public class TipoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipocuenta")
    private Integer idTipoCuenta;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    // Auditoría
    @Column(name = "fechacreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuarioModificacion;
}
