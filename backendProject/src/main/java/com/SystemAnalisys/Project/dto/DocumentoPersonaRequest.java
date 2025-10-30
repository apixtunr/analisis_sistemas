package com.SystemAnalisys.Project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DocumentoPersonaRequest {
    private Integer idtipodocumento;
    private Integer idpersona;
    private String nodocumento;
    private Date fechacreacion;
    private String usuariocreacion;

}
