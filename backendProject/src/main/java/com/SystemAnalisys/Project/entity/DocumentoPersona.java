package com.SystemAnalisys.Project.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@IdClass(DocumentoPersonaId.class)
@Table(name = "documento_persona", schema = "proyectoanalisis")
public class DocumentoPersona {

    @Id
    @Column(name = "idtipodocumento")
    private Integer idtipodocumento;

    @Id
    @Column(name = "idpersona")
    private Integer idpersona;

    @Column(name = "nodocumento", length = 50)
    private String nodocumento;

    @Column(name = "fechacreacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuariocreacion;

    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuariomodificacion;
}
