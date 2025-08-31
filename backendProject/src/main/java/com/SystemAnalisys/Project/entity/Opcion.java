package com.SystemAnalisys.Project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "opcion", schema = "proyectoanalisis")
public class Opcion {

    public Opcion() {}

    public Opcion(Integer idOpcion, Integer idMenu, String nombre, String descripcion, String url, LocalDateTime fechaCreacion, String usuarioCreacion, LocalDateTime fechaModificacion, String usuarioModificacion) {
        this.idOpcion = idOpcion;
        this.idMenu = idMenu;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idopcion")
    private Integer idOpcion;

    @Column(name = "idmenu", nullable = false)
    private Integer idMenu;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "fechacreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "usuariocreacion", length = 100, nullable = false)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuarioModificacion;

    @Column(name = "ordenmenu", nullable = false)
    private Integer ordenmenu;

    @Column(name = "pagina", nullable = false)
    private String pagina;

    public Integer getOrdenmenu() {
        return ordenmenu;
    }

    public void setOrdenmenu(Integer ordenmenu) {
        this.ordenmenu = ordenmenu;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }
}
