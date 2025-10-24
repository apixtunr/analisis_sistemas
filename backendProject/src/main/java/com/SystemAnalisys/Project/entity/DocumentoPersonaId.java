package com.SystemAnalisys.Project.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class DocumentoPersonaId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idtipodocumento;
    private Integer idpersona;
}
