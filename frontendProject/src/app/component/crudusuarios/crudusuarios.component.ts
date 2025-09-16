import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../entity/usuario';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PermisoService } from '../../service/permisoservice';
import { RolOpcion } from '../../entity/rolopcion';

@Component({
  selector: 'app-crudusuarios',
  standalone: false,
  templateUrl: './crudusuarios.component.html',
  styleUrl: './crudusuarios.component.css',
})
export class CrudusuariosComponent implements OnInit {
  permisosUsuario: RolOpcion | undefined;
  constructor(
    private usuarioService: UsuarioService,
    private fb: FormBuilder
  , private permisoService: PermisoService
  ) {}

  loading = true;
  error = '';
  imagenPreview: string | null = null;

  usuarioForm!: FormGroup;
  usuarios: any[] = []; // lista de usuarios

  //Método para inicializar el componente
  ngOnInit(): void {
    this.usuarioForm = this.fb.group({
      idUsuario: [''],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      fechaNacimiento: ['', Validators.required],
      password: ['', Validators.required],
      idGenero: [0],
      correoElectronico: ['', [Validators.required, Validators.email]],
      fotografia: [''],
      telefonoMovil: [''],
      idSucursal: [0],
      pregunta: [''],
      respuesta: [''],
    });

    this.usuarioService.getUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data; // aquí cargás la lista
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar usuarios';
        this.loading = false;
      },
    });
    // Obtener permisos para usuarios (idOpcion=9)
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    const idRole = usuario.rol;
    this.permisoService.getPermisos(9, idRole).subscribe(permiso => {
      this.permisosUsuario = permiso;
    });
  }

  //Método para crear usuario
  onSubmit() {
     if (this.usuarioForm.invalid) {
      this.usuarioForm.markAllAsTouched();
      return;
    }

    const usuario: Usuario = {
      ...this.usuarioForm.value,

      // valores default
      idStatusUsuario: 1,
      idRole: 2,
      fechaCreacion: new Date().toISOString(),
      ultimaFechaIngreso: new Date().toISOString(),
      intentosDeAcceso: 0,
      sesionActual: '',
      ultimaFechaCambioPassword: new Date().toISOString(),
      requiereCambiarPassword: 1,
      usuarioCreacion: 'ADMIN',
      fechaModificacion: '',
      usuarioModificacion: ''
    };

    this.usuarioService.createUsuario(usuario).subscribe({
      next: () => {
        alert('Usuario creado correctamente.');
        this.ngOnInit(); // recargar lista
        this.onReset();
      },
      error: () => {
        this.error = 'El usuario ya está en uso';
      }
    });
  }

  //Editar usuario (trae los datos al formulario)
  onEdit(usuario: Usuario) {
  // Carga todos los campos normales en el formulario
  const { fotografia, ...usuarioData } = usuario;
  this.usuarioForm.patchValue(usuarioData);

  // Si quieres mostrar la foto en el formulario
  if (fotografia) {
    this.usuarioForm.get('fotografia')?.setValue(fotografia);
  } else {
    this.imagenPreview = null;
  }
}
  //Método para eliminar usuario
  onDelete(idUsuario: string) {
    const confirmado = confirm('¿Estás seguro de eliminar este usuario?');
    if (!confirmado) return;

    this.usuarioService.deleteUsuario(idUsuario).subscribe({
      next: () => {
        alert('Usuario eliminado correctamente.');
        this.ngOnInit(); // recargar la lista
      },
      error: () => {
        this.error = 'Error al eliminar usuario';
      },
    });
  }

  //Método para actualizar usuario
  onUpdate() {
    if (this.usuarioForm.invalid) return;

    const usuario: Usuario = {
      ...this.usuarioForm.value,

      // valores default
      idStatusUsuario: 1,
      idRole: 2,
      fechaCreacion: new Date().toISOString(),
      ultimaFechaIngreso: new Date().toISOString(),
      intentosDeAcceso: 0,
      sesionActual: '',
      ultimaFechaCambioPassword: new Date().toISOString(),
      requiereCambiarPassword: 1,
      usuarioCreacion: 'ADMIN',
      fechaModificacion: '',
      usuarioModificacion: 'ADMIN' // temporal
    };

    if (!usuario.idUsuario) {
      alert('No se puede actualizar un usuario sin ID');
      return;
    }

    this.usuarioService.updateUsuario(usuario.idUsuario, usuario).subscribe({
      next: () => {
        alert('Usuario actualizado correctamente');
        this.ngOnInit();
        this.onReset();
      },
      error: () => {
        this.error = 'Error al actualizar usuario';
      }
    });
  }

  //Método para resetear el formulario
  onReset() {
    this.usuarioForm.reset({
      idUsuario: '',
      nombre: '',
      apellido: '',
      fechaNacimiento: '',
      password: '',
      idGenero: 0,
      correoElectronico: '',
      fotografia: '',
      telefonoMovil: '',
      idSucursal: 0,
      pregunta: '',
      respuesta: ''
      });
    };
  }
