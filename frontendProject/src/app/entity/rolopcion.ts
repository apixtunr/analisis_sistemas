export interface RolOpcion {
  idRole: number;
  idOpcion: number;
  alta: boolean;
  baja: boolean;
  cambio: boolean;
  imprimir: boolean;
  exportar: boolean;
  fechaCreacion: Date;
  usuarioCreacion: string;
  fechaModificacion?: Date;
  usuarioModificacion?: string;
}