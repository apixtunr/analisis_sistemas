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
@Table(name = "empresa", schema = "proyectoanalisis")
public class Empresa {
    public Empresa() {}

    public Empresa(Integer idEmpresa, String nombre, String direccion, String nit, Integer passwordCantidadMayusculas, 
                   Integer passwordCantidadMinusculas,Integer passwordCantidadCaracteresEspeciales, 
                   Integer passwordCantidadCaducidadDias, Integer passwordLargo,Integer passwordIntentosAntesDeBloquear, 
                   Integer passwordCantidadNumeros, Integer passwordCantidadPreguntasValidar, 
                   LocalDateTime fechaCreacion, String usuarioCreacion, LocalDateTime fechaModificacion, String usuarioModificacion
) {
    this.idEmpresa = idEmpresa;
    this.nombre = nombre;
    this.direccion = direccion;
    this.nit = nit;
    this.passwordCantidadMayusculas = passwordCantidadMayusculas;
    this.passwordCantidadMinusculas = passwordCantidadMinusculas;
    this.passwordCantidadCaracteresEspeciales = passwordCantidadCaracteresEspeciales;
    this.passwordCantidadCaducidadDias = passwordCantidadCaducidadDias;
    this.passwordLargo = passwordLargo;
    this.passwordIntentosAntesDeBloquear = passwordIntentosAntesDeBloquear;
    this.passwordCantidadNumeros = passwordCantidadNumeros;
    this.passwordCantidadPreguntasValidar = passwordCantidadPreguntasValidar;
    this.fechaCreacion = fechaCreacion;
    this.usuarioCreacion = usuarioCreacion;
    this.fechaModificacion = fechaModificacion;
    this.usuarioModificacion = usuarioModificacion;
    }

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idempresa")
private Integer idEmpresa;

@Column(name = "nombre", length = 100, nullable = false)
private String nombre;

@Column(name = "direccion", length = 200, nullable = false)
private String direccion;

@Column(name = "nit", length = 20, nullable = false)
private String nit;

@Column(name = "passwordcantidadmayusculas")
private Integer passwordCantidadMayusculas;

@Column(name = "passwordcantidadminusculas")
private Integer passwordCantidadMinusculas;

@Column(name = "passwordcantidadcaracteresespeciales")
private Integer passwordCantidadCaracteresEspeciales;

@Column(name = "passwordcantidadcaducidaddias")
private Integer passwordCantidadCaducidadDias;

@Column(name = "passwordlargo")
private Integer passwordLargo;

@Column(name = "passwordintentosantesdebloquear")
private Integer passwordIntentosAntesDeBloquear;

@Column(name = "passwordcantidadnumeros")
private Integer passwordCantidadNumeros;

@Column(name = "passwordcantidadpreguntasvalidar")
private Integer passwordCantidadPreguntasValidar;

@Column(name = "fechacreacion", nullable = false)
private LocalDateTime fechaCreacion;

@Column(name = "usuariocreacion", length = 100, nullable = false)
private String usuarioCreacion;

@Column(name = "fechamodificacion", nullable = true)
private LocalDateTime fechaModificacion;

@Column(name = "usuariomodificacion", length = 100, nullable = true)
private String usuarioModificacion;

}
