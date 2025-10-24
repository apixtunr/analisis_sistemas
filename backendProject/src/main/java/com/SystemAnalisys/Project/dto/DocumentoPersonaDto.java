package com.SystemAnalisys.Project.dto;

import lombok.Data;
import java.util.Date;

@Data
public class DocumentoPersonaDto {
    private Integer idtipodocumento;
    private Integer idpersona;
    private String nodocumento;
    private String nombreTipoDocumento;
    private Date fechacreacion;
    private String usuariocreacion;
    private Date fechamodificacion;
    private String usuariomodificacion;
}