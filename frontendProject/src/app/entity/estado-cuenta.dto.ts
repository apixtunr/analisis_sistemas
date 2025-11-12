export interface EstadoCuentaDto {
  idMovimientoCuenta: number;
  idSaldoCuenta: number;
  idTipoMovimientoCXC: number;
  fechaMovimiento: string;
  valorMovimiento: number;
  valorMovimientoPagado: number;
  generadoAutomaticamente: boolean;
  descripcion: string;
  fechaCreacion: string;
  usuarioCreacion: string;
  fechaModificacion: string;
  usuarioModificacion: string;


  // ✅ Agregadas para el estado de cuenta
  saldo?: number;
}
