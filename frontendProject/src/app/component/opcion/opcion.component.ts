import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';
import { UsuarioService } from '../../service/usuario.service';
import { RolOpcionService } from '../../service/rolopcion.service';
import { Usuario } from '../../entity/usuario';
import { Role } from '../../entity/role';
import { RolOpcion } from '../../entity/rolopcion';
import { Opcion } from '../../entity/opcion';

@Component({
	selector: 'app-opcion',
	templateUrl: './opcion.component.html',
	styleUrls: ['./opcion.component.css'],
	standalone: false
})
export class OpcionComponent implements OnInit {
	usuarios: Usuario[] = [];
	roles: Role[] = [];
	rolUsuario: Role | null = null;
	permisosForm: FormGroup;
	usuarioControl: FormControl;
	mensaje: string = '';
	cargandoPermisos: boolean = false;

	constructor(
		private fb: FormBuilder,
		private usuarioService: UsuarioService,
		private rolOpcionService: RolOpcionService
	) {
		this.usuarioControl = this.fb.control(null);
		this.permisosForm = this.fb.group({
			permisos: this.fb.array([])
		});
	}

	ngOnInit(): void {
		this.usuarioService.getUsuarios().subscribe(data => {
			this.usuarios = data;
		});
		this.usuarioService.getRoles().subscribe(data => {
			this.roles = data;
		});
		this.usuarioControl.valueChanges.subscribe((usuario: Usuario) => {
			this.onUsuarioChange(usuario);
		});
	}

	get permisosArray(): FormArray {
		return this.permisosForm.get('permisos') as FormArray;
	}

	onUsuarioChange(usuario: Usuario | null) {
		this.cargandoPermisos = true;
		this.rolUsuario = null;
		this.permisosArray.clear();

		if (!usuario) {
			this.cargandoPermisos = false;
			return;
		}

		this.rolUsuario = this.roles.find(r => r.idRole === usuario.idRole) || null;

		if (this.rolUsuario) {
			this.rolOpcionService.getPermisosPorRol(this.rolUsuario.idRole).subscribe({
				next: data => {
					this.permisosArray.clear();
					if (data && data.length > 0) {
						data.forEach(permiso => {
							this.permisosArray.push(this.fb.group({
								idRole: [permiso.idRole],
								idOpcion: [permiso.idOpcion],
								alta: [permiso.alta ?? false],
								baja: [permiso.baja ?? false],
								cambio: [permiso.cambio ?? false],
								imprimir: [permiso.imprimir ?? false],
								exportar: [permiso.exportar ?? false]
							}));
						});
					}
					this.cargandoPermisos = false;
				},
				error: () => {
					this.cargandoPermisos = false;
					this.mensaje = 'Error al cargar permisos.';
				}
			});
		} else {
			this.cargandoPermisos = false;
		}
	}

	guardarPermisos(): void {
		const permisos = this.permisosForm.value.permisos;
		if (!permisos || permisos.length === 0) return;

		let exitos = 0;
		let errores = 0;

		permisos.forEach((permiso: any) => {
			this.rolOpcionService.guardarRoleOpcion(permiso).subscribe({
				next: () => {
					exitos++;
					if (exitos + errores === permisos.length) {
						this.mensaje = 'Permisos guardados correctamente.';
					}
				},
				error: () => {
					errores++;
					this.mensaje = 'Error al guardar permisos.';
				}
			});
		});
	}

	trackByIndex(index: number, item: any) {
		return index;
	}
}
