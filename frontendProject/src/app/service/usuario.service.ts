import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../entity/usuario';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
	private apiUrl = 'http://localhost:8080';

	constructor(private http: HttpClient) {}

	getUsuarios(): Observable<Usuario[]> {
		return this.http.get<Usuario[]>(this.apiUrl + '/api/list_usuarios');
	}
}



