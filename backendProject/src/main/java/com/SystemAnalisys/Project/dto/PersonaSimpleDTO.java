package com.SystemAnalisys.Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaSimpleDTO {
    private String nombreCompleto; // Mapea a 'nombreCompleto' del query
    private Integer idPersona;    // Mapea a 'idpersona' del query
}