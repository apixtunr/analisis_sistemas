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
@Table(name = "sucursal", schema = "proyectoanalisis")
public class Sucursal {
    public Sucursal () {}

    public Sucursal(
    Integer idSucursal,
    String nombre,
    String direccion,
    Integer idEmpresa,
    Date fechaCreacion,
    String usuarioCreacion,
    Date fechaModificacion,
    String usuarioModificacion
) {
    this.idSucursal = idSucursal;
    this.nombre = nombre;
    this.direccion = direccion;
    this.idEmpresa = idEmpresa;
    this.fechaCreacion = fechaCreacion;
    this.usuarioCreacion = usuarioCreacion;
    this.fechaModificacion = fechaModificacion;
    this.usuarioModificacion = usuarioModificacion;
}

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) // hace que se autogenere en la DB
@Column(name = "idsucursal")
private Integer idSucursal;

@Column(name = "nombre")
private String nombre;

@Column(name = "direccion")
private String direccion;

@Column(name = "idempresa")
private Integer idEmpresa;

@Column(name = "fechacreacion")
private Date fechaCreacion;

@Column(name = "usuariocreacion")
private String usuarioCreacion;

@Column(name = "fechamodificacion")
private Date fechaModificacion;

@Column(name = "usuariomodificacion")
private String usuarioModificacion;

}
