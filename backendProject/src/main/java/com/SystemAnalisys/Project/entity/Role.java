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
@Table(name = "role", schema = "proyectoanalisis")
public class Role {

    public Role() {}

    public Role(Integer idRole, String nombre, LocalDateTime fechacreacion, String usuariocreacion, LocalDateTime fechamodificacion, String usuariomodificacion) {
        this.idRole = idRole;
        this.nombre = nombre;
        this.fechacreacion = fechacreacion;
        this.usuariocreacion = usuariocreacion;
        this.fechamodificacion = fechamodificacion;
        this.usuariomodificacion = usuariomodificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrole")
    private Integer idRole;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "fechacreacion")
    private LocalDateTime fechacreacion;

    @Column(name = "usuariocreacion", length = 50, nullable = false)
    private String usuariocreacion;

    @Column(name = "fechamodificacion")
    private LocalDateTime fechamodificacion;
    
    @Column(name = "usuariomodificacion", length = 50, nullable = false)
    private String usuariomodificacion;
}
