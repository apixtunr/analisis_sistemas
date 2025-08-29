import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../entity/usuario';
import { Role } from '../../entity/role';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css'],
  standalone: false
})
export class RoleComponent implements OnInit {
  usuarios: Usuario[] = [];
  roles: Role[] = [];
  mensaje: string = '';
  form: FormGroup;

  constructor(private usuarioService: UsuarioService) {
    this.form = new FormGroup({
      usuario: new FormControl('', Validators.required),
      rol: new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
    this.usuarioService.getUsuarios().subscribe(data => {
      this.usuarios = data;
    });
    this.usuarioService.getRoles().subscribe(data => {
      this.roles = data;
    });
  }

  asignarRol() {
    if (this.form.valid) {
      const usuarioSeleccionado = this.form.value.usuario;
      const rolSeleccionado = this.form.value.rol;
      this.usuarioService.actualizarRolUsuario(usuarioSeleccionado, rolSeleccionado)
        .subscribe({
          next: () => this.mensaje = 'Rol asignado correctamente.',
          error: () => this.mensaje = 'Error al asignar el rol.'
        });
    } else {
      this.mensaje = 'Selecciona usuario y rol.';
    }
  }
}
