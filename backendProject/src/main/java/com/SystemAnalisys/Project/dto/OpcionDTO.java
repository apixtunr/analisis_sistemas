package com.SystemAnalisys.Project.dto;

public class OpcionDTO {
    private Integer idOpcion;
    private String nombre;
    private String url;
    private String descripcion;

    public OpcionDTO(Integer idOpcion, String nombre, String url, String descripcion) {
        this.idOpcion = idOpcion;
        this.nombre = nombre;
        this.url = url;
        this.descripcion = descripcion;
    }

    public Integer getIdOpcion() { return idOpcion; }
    public String getNombre() { return nombre; }
    public String getUrl() { return url; }
    public String getDescripcion() { return descripcion; }
}
