package com.SystemAnalisys.Project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role_opcion", schema = "proyectoanalisis")
public class RoleOpcion {

    @EmbeddedId
    private RoleOpcionId id;

    public RoleOpcion() {}

    public RoleOpcion(RoleOpcionId id, Boolean alta, Boolean baja, Boolean cambio, Boolean imprimir, Boolean exportar, LocalDateTime fechaCreacion, String usuarioCreacion, LocalDateTime fechaModificacion, String usuarioModificacion) {
        this.id = id;
        this.alta = alta;
        this.baja = baja;
        this.cambio = cambio;
        this.imprimir = imprimir;
        this.exportar = exportar;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    @Column(name = "alta", nullable = false)
    private Boolean alta;

    @Column(name = "baja", nullable = false)
    private Boolean baja;

    @Column(name = "cambio", nullable = false)
    private Boolean cambio;

    @Column(name = "imprimir", nullable = false)
    private Boolean imprimir;

    @Column(name = "exportar", nullable = false)
    private Boolean exportar;

    @Column(name = "fechacreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuarioModificacion;
}
