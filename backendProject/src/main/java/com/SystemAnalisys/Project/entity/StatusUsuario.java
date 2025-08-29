package com.SystemAnalisys.Project.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "status_usuario", schema = "proyectoanalisis")
public class StatusUsuario {
    public StatusUsuario() {}

    public StatusUsuario(Integer idstatususuario, String nombre, Date fechacreacion, String usuariocreacion,
                  Date fechamodificacion, String usuariomodificacion) {
        this.idstatususuario = idstatususuario;
        this.nombre = nombre;
        this.fechaCreacion = fechacreacion;
        this.usuarioCreacion = usuariocreacion; 
        this.fechaModificacion = fechamodificacion;
        this.usuarioModificacion = usuariomodificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstatususuario")
    private Integer idstatususuario;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "fechacreacion", nullable = false)
    private Date fechaCreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = true)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion", nullable = true)
    private Date fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100, nullable = true)
    private String usuarioModificacion;
}
