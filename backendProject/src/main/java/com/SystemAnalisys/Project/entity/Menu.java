package com.SystemAnalisys.Project.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "menu", schema = "proyectoanalisis")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idmenu;

    private Integer idmodulo;
    private String nombre;
    private Integer ordenmenu;
    private Date fechacreacion;
    private String usuariocreacion;
    private Date fechamodificacion;
    private String usuariomodificacion;

    // Getters y setters
    public Integer getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Integer idmenu) {
        this.idmenu = idmenu;
    }

    public Integer getIdmodulo() {
        return idmodulo;
    }

    public void setIdmodulo(Integer idmodulo) {
        this.idmodulo = idmodulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrdenmenu() {
        return ordenmenu;
    }

    public void setOrdenmenu(Integer ordenmenu) {
        this.ordenmenu = ordenmenu;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getUsuariocreacion() {
        return usuariocreacion;
    }

    public void setUsuariocreacion(String usuariocreacion) {
        this.usuariocreacion = usuariocreacion;
    }

    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public String getUsuariomodificacion() {
        return usuariomodificacion;
    }

    public void setUsuariomodificacion(String usuariomodificacion) {
        this.usuariomodificacion = usuariomodificacion;
    }
    
}
