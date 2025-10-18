package com.SystemAnalisys.Project.dto; // Paquete corregido

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroMovimientoResponse {
    private String mensaje;
    private Integer idSldCuentaAfectado; // Asumiendo que la función podría retornar el ID afectado
    private Boolean exito; // Indicador de éxito
}