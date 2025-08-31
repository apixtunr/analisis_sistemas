import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modulo } from '../entity/modulo';

@Injectable({ providedIn: 'root' })
export class ModuloService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Obtener todos los módulos
  getModulos(): Observable<Modulo[]> {
    return this.http.get<Modulo[]>(this.apiUrl + '/api/list_modulo');
  }

}
