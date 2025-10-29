package com.SystemAnalisys.Project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status_cuenta", schema = "proyectoanalisis")
public class StatusCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstatuscuenta")
    private Long idstatuscuenta;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fechacreacion", nullable = false)
    private LocalDateTime fechacreacion;

    @Column(name = "usuariocreacion", nullable = false)
    private String usuariocreacion;

    @Column(name = "fechamodificacion")
    private LocalDateTime fechamodificacion;

    @Column(name = "usuariomodificacion")
    private String usuariomodificacion;

}