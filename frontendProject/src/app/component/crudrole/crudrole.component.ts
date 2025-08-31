import { Component, OnInit } from '@angular/core';
import { Role } from '../../entity/role';
import { RoleService } from '../../service/role.service';

@Component({
  selector: 'app-crudrole',
  standalone: false,
  templateUrl: './crudrole.component.html',
  styleUrls: ['./crudrole.component.css'],
})
export class CrudroleComponent implements OnInit {
  constructor(private roleService: RoleService) {}

  loading = true;
  error = '';
  roles: Role[] = []; // lista de roles
  role: Role = {
    idRole: null,
    nombre: '',
    fechacreacion: null,
    usuariocreacion: '',
    fechamodificacion: null,
    usuariomodificacion: '',
  };

  ngOnInit(): void {
    this.roleService.getRoles().subscribe({
      next: (data) => {
        this.roles = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar roles';
        this.loading = false;
      },
    });
  }

  private getUsuarioLogueado(): string {
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    return usuario?.nombre || 'system'; // fallback en caso de que no exista
  }

  onSubmit() {
    const payload: Role = {
      ...this.role,
      fechacreacion: this.role.fechacreacion ? new Date(this.role.fechacreacion) : null,
      fechamodificacion: this.role.fechamodificacion ? new Date(this.role.fechamodificacion) : null,
    };

    // Crear
    if (payload.idRole == null) {
      payload.idRole = null;
      payload.fechacreacion = new Date();
      payload.usuariocreacion = this.getUsuarioLogueado(); // ✅ se toma de localStorage
      payload.fechamodificacion = null;
      this.roleService.createRole(payload).subscribe({
        next: () => {
          alert('Rol creado correctamente.');
          this.ngOnInit();
          this.onReset();
        },
        error: () => {
          this.error = 'Error al crear rol';
        },
      });
    }
    // Actualizar
    else {
      payload.fechamodificacion = new Date();
      payload.usuariomodificacion = this.getUsuarioLogueado(); // ✅ se toma de localStorage
      this.roleService.updateRole(payload).subscribe({
        next: () => {
          alert('Rol actualizado correctamente.');
          this.ngOnInit();
          this.onReset();
        },
        error: () => {
          this.error = 'Error al actualizar rol';
        },
      });
    }
  }

  onEdit(role: Role) {
    this.role = { ...role };
  }

  onDelete(idRole: number) {
    if (!confirm('¿Seguro que deseas eliminar este rol?')) return;
    this.roleService.deleteRole(idRole).subscribe({
      next: () => {
        alert('Rol eliminado correctamente.');
        this.ngOnInit();
      },
      error: () => {
        this.error = 'Error al eliminar rol';
      },
    });
  }

  onReset() {
    this.role = {
      idRole: null,
      nombre: '',
      fechacreacion: null,
      usuariocreacion: '',
      fechamodificacion: null,
      usuariomodificacion: '',
    };
  }
}
