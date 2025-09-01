package com.SystemAnalisys.Project.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "bitacora_acceso", schema = "proyectoanalisis")
public class BitacoraAcceso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbitacoraacceso")
    private Integer idBitacoraAcceso;
    
    @Column(name = "idusuario", length = 100, nullable = false)
    private String idUsuario;
    

    @ManyToOne(optional = false)
    @JoinColumn(name = "idtipoacceso", nullable = false)
    private TipoAcceso tipoAcceso;

    
    @Column(name = "fechaacceso", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAcceso;
    
    @Column(name = "httpuseragent", length = 200)
    private String httpUserAgent;
    
    @Column(name = "direccionip", length = 50)
    private String direccionIp;
    
    @Column(name = "accion", length = 100)
    private String accion;
    
    @Column(name = "sistemaoperativo", length = 50)
    private String sistemaOperativo;
    
    @Column(name = "dispositivo", length = 50)
    private String dispositivo;
    
    @Column(name = "browser", length = 50)
    private String browser;
    
    @Column(name = "sesion", length = 100)
    private String sesion;
    

    }