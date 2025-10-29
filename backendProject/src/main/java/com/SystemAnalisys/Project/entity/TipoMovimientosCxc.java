package com.SystemAnalisys.Project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "tipo_movimiento_cxc", schema = "proyectoanalisis")
public class TipoMovimientosCxc {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipomovimientocxc")
    private Long id;

    private String nombre;

    @Column(name = "operacioncuentacorriente")
    private Integer operacionCuentaCorriente; // 1 = Cargo, 2 = Abono

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "usuariocreacion")
    private String usuarioCreacion;

    @Column(name = "fechamodificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuariomodificacion")
    private String usuarioModificacion;

    // --- Getters y Setters ---
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public Integer getOperacionCuentaCorriente() { 
        return operacionCuentaCorriente; 
    }

    public void setOperacionCuentaCorriente(Integer operacionCuentaCorriente) { 
        this.operacionCuentaCorriente = operacionCuentaCorriente; 
    }

    public LocalDateTime getFechaCreacion() { 
        return fechaCreacion; 
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) { 
        this.fechaCreacion = fechaCreacion; 
    }

    public String getUsuarioCreacion() { 
        return usuarioCreacion; 
    }

    public void setUsuarioCreacion(String usuarioCreacion) { 
        this.usuarioCreacion = usuarioCreacion; 
    }

    public LocalDateTime getFechaModificacion() { 
        return fechaModificacion; 
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) { 
        this.fechaModificacion = fechaModificacion; 
    }

    public String getUsuarioModificacion() { 
        return usuarioModificacion; 
    }

    public void setUsuarioModificacion(String usuarioModificacion) { 
        this.usuarioModificacion = usuarioModificacion; 
    }

}
