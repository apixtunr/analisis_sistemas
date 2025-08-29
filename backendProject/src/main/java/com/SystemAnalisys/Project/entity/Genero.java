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
@Table(name = "genero", schema = "proyectoanalisis") 
public class Genero {
    public Genero() {}

    public Genero(Integer idgenero, String nombre, Date fechaCreacion, String usuarioCreacion,
                  Date fechaModificacion, String usuarioModificacion) {
        this.idgenero = idgenero;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgenero")
    private Integer idgenero;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "fechacreacion", nullable = false)
    private Date fechaCreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion", nullable = true)
    private Date fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100, nullable = true)
    private String usuarioModificacion;
}
