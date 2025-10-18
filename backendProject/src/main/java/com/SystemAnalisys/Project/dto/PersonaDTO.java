package com.SystemAnalisys.Project.dto; // Nuevo paquete

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO { // Nuevo nombre
    private String nombreTipoSaldo;
    private Integer idSaldoCuenta;
}