package com.SystemAnalisys.Project.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EstadoDeCuentaDTO {
    private Long idMovimientoCuenta;
    private Long idSaldoCuenta;
    private Long idTipoMovimientoCXC;
    private LocalDateTime fechaMovimiento;
    private BigDecimal valorMovimiento;
    private BigDecimal valorMovimientoPagado;
    private Boolean generadoAutomaticamente;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String usuarioCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioModificacion;

    // Constructor necesario para @ConstructorResult
    public EstadoDeCuentaDTO(
        Long idMovimientoCuenta,
        Long idSaldoCuenta,
        Long idTipoMovimientoCXC,
        LocalDateTime fechaMovimiento,
        BigDecimal valorMovimiento,
        BigDecimal valorMovimientoPagado,
        Boolean generadoAutomaticamente,
        String descripcion,
        LocalDateTime fechaCreacion,
        String usuarioCreacion,
        LocalDateTime fechaModificacion,
        String usuarioModificacion
    ) {
        this.idMovimientoCuenta = idMovimientoCuenta;
        this.idSaldoCuenta = idSaldoCuenta;
        this.idTipoMovimientoCXC = idTipoMovimientoCXC;
        this.fechaMovimiento = fechaMovimiento;
        this.valorMovimiento = valorMovimiento;
        this.valorMovimientoPagado = valorMovimientoPagado;
        this.generadoAutomaticamente = generadoAutomaticamente;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    // Getters and Setters

    public Long getIdMovimientoCuenta() {
        return idMovimientoCuenta;
    }
    public void setIdMovimientoCuenta(Long idMovimientoCuenta) {
        this.idMovimientoCuenta = idMovimientoCuenta;
    }
    public Long getIdSaldoCuenta() {
        return idSaldoCuenta;
    }
    public void setIdSaldoCuenta(Long idSaldoCuenta) {
        this.idSaldoCuenta = idSaldoCuenta;
    }
    public Long getIdTipoMovimientoCXC() {
        return idTipoMovimientoCXC;
    }
    public void setIdTipoMovimientoCXC(Long idTipoMovimientoCXC) {
        this.idTipoMovimientoCXC = idTipoMovimientoCXC;
    }
    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
    public BigDecimal getValorMovimiento() {
        return valorMovimiento;
    }
    public void setValorMovimiento(BigDecimal valorMovimiento) {
        this.valorMovimiento = valorMovimiento;
    }
    public BigDecimal getValorMovimientoPagado() {
        return valorMovimientoPagado;
    }
    public void setValorMovimientoPagado(BigDecimal valorMovimientoPagado) {
        this.valorMovimientoPagado = valorMovimientoPagado;
    }
    public Boolean getGeneradoAutomaticamente() {
        return generadoAutomaticamente;
    }
    public void setGeneradoAutomaticamente(Boolean generadoAutomaticamente) {
        this.generadoAutomaticamente = generadoAutomaticamente;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
