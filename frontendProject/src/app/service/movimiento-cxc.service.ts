import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'; // Importa HttpParams
import { Observable } from 'rxjs';
import { TipoMovimientoCxcDto } from '../entity/tipo-movimiento-cxc.dto';
import { PersonaDto } from '../entity/persona.dto';
import { CuentaDto } from '../entity/cuenta.dto';
import { RegistroMovimientoRequest } from '../entity/registro-movimiento.request';
import { RegistroMovimientoResponse } from '../entity/registro-movimiento.response';
import { EstadoCuentaDto } from '../entity/estado-cuenta.dto'; // Mantenemos si se sigue usando para el GET antiguo o como referencia

@Injectable({
  providedIn: 'root',
})
export class MovimientoCxcService {
  // Ajustamos la baseUrl para que solo apunte a 'http://localhost:8080'
  // y luego añadimos '/api/v1' o '/api' según el endpoint.
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getTiposMovimientoCxc(): Observable<TipoMovimientoCxcDto[]> {
    return this.http.get<TipoMovimientoCxcDto[]>(
      `${this.baseUrl}/v1/tipo-movimiento-cxc`
    );
  }

  getPersonas(): Observable<PersonaDto[]> {
    // Asumiendo que /api/personas/simple es el endpoint para obtener una lista simple de personas
    return this.http.get<PersonaDto[]>(`${this.baseUrl}/personas/simple`);
  }

  getCuentasPorPersona(idPersona: number): Observable<CuentaDto[]> {
    // Asumiendo que /api/personas/{id}/cuentas es el endpoint para obtener cuentas por persona
    return this.http.get<CuentaDto[]>(
      `${this.baseUrl}/personas/${idPersona}/cuentas`
    );
  }

  registrarMovimiento(
    request: RegistroMovimientoRequest
  ): Observable<RegistroMovimientoResponse> {
    // El endpoint de registro de movimientos está en /api/v1/movimientos-cuenta
    return this.http.post<RegistroMovimientoResponse>(
      `${this.baseUrl}/v1/movimientos-cuenta`,
      request
    );
  }

  // Si necesitas mantener el método getEstadoCuenta antiguo, aquí está con HttpParams
  // Si no se usa, puedes eliminarlo para mantener el código más limpio.
  getEstadoCuenta(
    idCuenta: number,
    fechaInicio: string,
    fechaFin: string
  ): Observable<EstadoCuentaDto[]> {
    let params = new HttpParams()
      .set('idCuenta', idCuenta.toString())
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);

    return this.http.get<EstadoCuentaDto[]>(
      `${this.baseUrl}/v1/movimientos-cuenta/estado-cuenta`,
      { params }
    );
  }

  /**
   * Nuevo método para obtener el estado de cuenta con los saldos calculados desde el backend.
   * La respuesta es un objeto que contiene la lista de movimientos y los totales.
   */
  getMovimientosConSaldos(idCuenta: number, fechaInicio: string, fechaFin: string): Observable<any> {
    let params = new HttpParams()
      .set('idCuenta', idCuenta.toString())
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);

    return this.http.get<any>(
      `${this.baseUrl}/v1/movimientos-cuenta/estado-cuenta-con-saldos`,
      { params }
    );
  }
}
