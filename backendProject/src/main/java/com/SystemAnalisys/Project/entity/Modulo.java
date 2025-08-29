package com.SystemAnalisys.Project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "modulo", schema = "proyectoanalisis")
public class Modulo {

    public Modulo() {}

    public Modulo(Integer idModulo, String nombre, Integer ordenmenu, LocalDateTime fechacreacion,
                  String usuariocreacion, LocalDateTime fechamodificacion, String usuariomodificacion) {
        this.idModulo = idModulo;
        this.nombre = nombre;
        this.ordenmenu = ordenmenu;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.fechamodificacion = fechamodificacion;
        this.usuariomodificacion = usuariomodificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmodulo")
    private Integer idModulo;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "ordenmenu", nullable = false)
    private Integer ordenmenu;

    @Column(name = "fechacreacion", nullable = false)
    private LocalDateTime fechacreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuariocreacion;

    @Column(name = "fechamodificacion", nullable = true)
    private LocalDateTime fechamodificacion;

    @Column(name = "usuariomodificacion", length = 100, nullable = true)
    private String usuariomodificacion;
}
