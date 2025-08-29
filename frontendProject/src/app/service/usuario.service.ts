import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../entity/usuario';
import { RolOpcion } from '../entity/rolopcion';
import { Role } from '../entity/role';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
	private apiUrl = 'http://localhost:8080';

	constructor(private http: HttpClient) {}

	getUsuarios(): Observable<Usuario[]> {
		return this.http.get<Usuario[]>(this.apiUrl + '/api/list_usuarios');
	}

  actualizarRolUsuario(idUsuario: string, idRole: number): Observable<void> {
  const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  return this.http.put<void>(
    `${this.apiUrl}/usuario/${idUsuario}/rol`,
    JSON.stringify(idRole),
    httpOptions
  );
}
  getRoles(): Observable<Role[]> {
  return this.http.get<Role[]>(this.apiUrl + '/role/list');
}

getUsuariosPorRol(idRole: number): Observable<Usuario[]> {
  return this.http.get<Usuario[]>(`${this.apiUrl}/api/usuarios/${idRole}`);
}

	login(usuario: Usuario){
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<Usuario>(this.apiUrl + "/user/login", usuario, httpOptions);
  }
}



