import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../entity/usuario';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-crudusuarios',
  standalone: false,
  templateUrl: './crudusuarios.component.html',
  styleUrl: './crudusuarios.component.css',
})
export class CrudusuariosComponent implements OnInit {
  constructor(
    private usuarioService: UsuarioService,
    private fb: FormBuilder
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
    console.log('Base64 de foto:', this.usuarioForm.get('fotografia')?.value);
  } else {
    this.imagenPreview = null;
  }
}
  //Método para eliminar usuario
  onDelete(idUsuario: string) {
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
      password: '',
      idGenero: 0,
      ultimaFechaIngreso: '',
      intentosDeAcceso: 0,
      sesionActual: '',
      ultimaFechaCambioPassword: '',
      correoElectronico: '',
      requiereCambiarPassword: 0,
      fotografia: null,
      telefonoMovil: '',
      idSucursal: 0,
      pregunta: '',
      respuesta: '',
      idRole: 0,
      fechaCreacion: '',
      usuarioCreacion: '',
      fechaModificacion: '',
      usuarioModificacion: '',
      });
    };
  }
