package com.SystemAnalisys.Project.dto;

public class UsuarioDTO {
    private String idUsuario;
    private String nombre;
    private String apellido;
    private Integer idSucursal;

    public UsuarioDTO() {}

    public UsuarioDTO(String idUsuario, String nombre, String apellido, Integer idSucursal) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idSucursal = idSucursal;
    }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
}
