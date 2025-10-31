package com.SystemAnalisys.Project.entity;

import com.SystemAnalisys.Project.dto.EstadoDeCuentaDTO;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@SqlResultSetMapping(
    name = "EstadoDeCuentaMapping",
    classes = @ConstructorResult(
        targetClass = EstadoDeCuentaDTO.class,
        columns = {
            @ColumnResult(name = "idMovimientoCuenta", type = Long.class),
            @ColumnResult(name = "idSaldoCuenta", type = Long.class),
            @ColumnResult(name = "idTipoMovimientoCXC", type = Long.class),
            @ColumnResult(name = "fechaMovimiento", type = LocalDateTime.class),
            @ColumnResult(name = "valorMovimiento", type = BigDecimal.class),
            @ColumnResult(name = "valorMovimientoPagado", type = BigDecimal.class),
            @ColumnResult(name = "generadoAutomaticamente", type = Boolean.class),
            @ColumnResult(name = "descripcion", type = String.class),
            @ColumnResult(name = "fechaCreacion", type = LocalDateTime.class),
            @ColumnResult(name = "usuarioCreacion", type = String.class),
            @ColumnResult(name = "fechaModificacion", type = LocalDateTime.class),
            @ColumnResult(name = "usuarioModificacion", type = String.class)
        }
    )
)
@Entity
@Table(name = "movimiento_cuenta", schema = "proyectoanalisis")
public class MovimientoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmovimientocuenta;

    private Long idsaldocuenta;

    private Long idtipomovimientocxc;

    private LocalDateTime fechamovimiento;

    private BigDecimal valormovimiento;

    private BigDecimal valormovimientopagado;

    private Boolean generadoautomaticamente;

    private String descripcion;

    private LocalDateTime fechacreacion;

    private String usuariocreacion;

    private LocalDateTime fechamodificacion;

    private String usuariomodificacion;

    // Getters y Setters

    public Long getIdmovimientocuenta() { return idmovimientocuenta; }
    public void setIdmovimientocuenta(Long idmovimientocuenta) { this.idmovimientocuenta = idmovimientocuenta; }

    public Long getIdsaldocuenta() { return idsaldocuenta; }
    public void setIdsaldocuenta(Long idsaldocuenta) { this.idsaldocuenta = idsaldocuenta; }

    public Long getIdtipomovimientocxc() { return idtipomovimientocxc; }
    public void setIdtipomovimientocxc(Long idtipomovimientocxc) { this.idtipomovimientocxc = idtipomovimientocxc; }

    public LocalDateTime getFechamovimiento() { return fechamovimiento; }
    public void setFechamovimiento(LocalDateTime fechamovimiento) { this.fechamovimiento = fechamovimiento; }

    public BigDecimal getValormovimiento() { return valormovimiento; }
    public void setValormovimiento(BigDecimal valormovimiento) { this.valormovimiento = valormovimiento; }

    public BigDecimal getValormovimientopagado() { return valormovimientopagado; }
    public void setValormovimientopagado(BigDecimal valormovimientopagado) { this.valormovimientopagado = valormovimientopagado; }

    public Boolean getGeneradoautomaticamente() { return generadoautomaticamente; }
    public void setGeneradoautomaticamente(Boolean generadoautomaticamente) { this.generadoautomaticamente = generadoautomaticamente; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechacreacion() { return fechacreacion; }
    public void setFechacreacion(LocalDateTime fechacreacion) { this.fechacreacion = fechacreacion; }

    public String getUsuariocreacion() { return usuariocreacion; }
    public void setUsuariocreacion(String usuariocreacion) { this.usuariocreacion = usuariocreacion; }

    public LocalDateTime getFechamodificacion() { return fechamodificacion; }
    public void setFechamodificacion(LocalDateTime fechamodificacion) { this.fechamodificacion = fechamodificacion; }

    public String getUsuariomodificacion() { return usuariomodificacion; }
    public void setUsuariomodificacion(String usuariomodificacion) { this.usuariomodificacion = usuariomodificacion; }
}
