package com.SystemAnalisys.Project.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "persona", schema = "proyectoanalisis")
public class Persona {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersona")
    private Integer idPersona;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "idgenero", nullable = false)
    private Integer idGenero;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correoelectronico", length = 100)
    private String correoElectronico;

    @Column(name = "idestadocivil", nullable = false)
    private Integer idEstadoCivil;

    // Campos de documento
    @Column(name = "idtipodocumento")
    private Integer idTipoDocumento;

    @Column(name = "numerodocumento", length = 50)
    private String numeroDocumento;

    // Relaciones con otras entidades
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idgenero", insertable = false, updatable = false)
    private Genero genero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idestadocivil", insertable = false, updatable = false)
    private EstadoCivil estadoCivil;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtipodocumento", insertable = false, updatable = false)
    private TipoDocumento tipoDocumento;

    // Campos de auditoría
    @Column(name = "fechacreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuarioModificacion;
}