export interface TipoDocumento {
  idTipoDocumento?: number;
  nombre: string;
  fechaCreacion: string | null;
  usuarioCreacion: string | null;
  fechaModificacion: string | null;
  usuarioModificacion: string | null;
}