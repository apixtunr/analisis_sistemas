package com.SystemAnalisys.Project.dto;

import java.time.LocalDateTime;

public class RoleOpcionDTO {
    private Integer idRole;
    private Integer idOpcion;
    private Boolean alta;
    private Boolean baja;
    private Boolean cambio;
    private Boolean imprimir;
    private Boolean exportar;
    private LocalDateTime fechaCreacion;
    private String usuarioCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioModificacion;

    public RoleOpcionDTO() {}

    // Constructor con todos los campos
    public RoleOpcionDTO(Integer idRole, Integer idOpcion, Boolean alta, Boolean baja, Boolean cambio, Boolean imprimir, Boolean exportar,
                         LocalDateTime fechaCreacion, String usuarioCreacion, LocalDateTime fechaModificacion, String usuarioModificacion) {
        this.idRole = idRole;
        this.idOpcion = idOpcion;
        this.alta = alta;
        this.baja = baja;
        this.cambio = cambio;
        this.imprimir = imprimir;
        this.exportar = exportar;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    // Getters y setters
    public Integer getIdRole() { return idRole; }
    public void setIdRole(Integer idRole) { this.idRole = idRole; }
    public Integer getIdOpcion() { return idOpcion; }
    public void setIdOpcion(Integer idOpcion) { this.idOpcion = idOpcion; }
    public Boolean getAlta() { return alta; }
    public void setAlta(Boolean alta) { this.alta = alta; }
    public Boolean getBaja() { return baja; }
    public void setBaja(Boolean baja) { this.baja = baja; }
    public Boolean getCambio() { return cambio; }
    public void setCambio(Boolean cambio) { this.cambio = cambio; }
    public Boolean getImprimir() { return imprimir; }
    public void setImprimir(Boolean imprimir) { this.imprimir = imprimir; }
    public Boolean getExportar() { return exportar; }
    public void setExportar(Boolean exportar) { this.exportar = exportar; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getUsuarioCreacion() { return usuarioCreacion; }
    public void setUsuarioCreacion(String usuarioCreacion) { this.usuarioCreacion = usuarioCreacion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
    public String getUsuarioModificacion() { return usuarioModificacion; }
    public void setUsuarioModificacion(String usuarioModificacion) { this.usuarioModificacion = usuarioModificacion; }
}