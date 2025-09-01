package com.SystemAnalisys.Project.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_acceso", schema = "proyectoanalisis")
public class TipoAcceso {

    public TipoAcceso() {}

    public TipoAcceso(Integer idTipoAcceso, String nombre, Date fechaCreacion, String usuarioCreacion,
                      Date fechaModificacion, String usuarioModificacion) {
        this.idTipoAcceso = idTipoAcceso;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    @Id
    @Column(name = "idtipoacceso")
    private Integer idTipoAcceso;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "usuariocreacion", length = 100)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuarioModificacion;
}