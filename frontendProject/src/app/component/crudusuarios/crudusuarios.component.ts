import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { GeneroService } from '../../service/genero.service';
import { SucursalService } from '../../service/sucursal.service';
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
    private generoService: GeneroService,
    private sucursalService: SucursalService,
    private fb: FormBuilder
  ) {}

  loading = true;
  error = '';
  imagenPreview: string | null = null;
  isEditMode = false; //Bandera para el modo edición

  usuarioForm!: FormGroup;
  usuarios: any[] = []; // lista de usuarios
  generos: any[] = []; // lista de géneros
  sucursales: any[] = []; // lista de sucursales

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

    // Cargar usuarios
    this.usuarioService.getUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar usuarios';
        this.loading = false;
      },
    });

    // Cargar géneros
    this.generoService.getGeneros().subscribe({
      next: (data) => {
        this.generos = data;
      },
      error: (err) => {
        console.error('Error al cargar géneros:', err);
      },
    });

    // Cargar sucursales
    this.sucursalService.getSucursales().subscribe({
      next: (data) => {
        this.sucursales = data;
      },
      error: (err) => {
        console.error('Error al cargar sucursales:', err);
      },
    });
  }

  // Método para obtener el nombre del género por ID
  getGeneroNombre(idGenero: number): string {
    const genero = this.generos.find((g) => g.idgenero === idGenero);
    return genero ? genero.nombre : 'No especificado';
  }

  // Método para obtener el nombre de la sucursal por ID
  getSucursalNombre(idSucursal: number): string {
    const sucursal = this.sucursales.find((s) => s.idSucursal === idSucursal);
    return sucursal ? sucursal.nombre : 'No especificado';
  }

  //Método para crear usuario
  onSubmit() {
    if (this.usuarioForm.invalid) {
      this.usuarioForm.markAllAsTouched();
      console.log('No se pudo crear el usuario: formulario inválido');
      return;
    }

    const usuarioLocal = JSON.parse(localStorage.getItem('usuario') || '{}');
    const usuarioCreacion = usuarioLocal.id || ''; // Esto jala el idUsuario

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
      usuarioCreacion: usuarioCreacion,
      fechaModificacion: '',
      usuarioModificacion: '',
    };

    this.usuarioService.createUsuario(usuario).subscribe({
      next: () => {
        console.log('Usuario creado correctamente');
        alert('Usuario creado correctamente.');
        this.ngOnInit(); // recargar lista
        this.onReset();
      },
      error: (error) => {
        const requisitos = error?.error;
        if (
          typeof requisitos === 'string' &&
          requisitos.includes('La contraseña no cumple con los requisitos')
        ) {
          alert(requisitos);
          this.error = requisitos;
        } else {
          this.error = 'El usuario ya está en uso';
          alert(this.error);
        }
      },
    });
  }

  //Editar usuario (trae los datos al formulario)
  onEdit(usuario: Usuario) {
    // Carga todos los campos normales en el formulario
    const { fotografia, ...usuarioData } = usuario;
    this.usuarioForm.patchValue(usuarioData);
      this.isEditMode = true; // Activar modo edición

    // Si quieres mostrar la foto en el formulario
    if (fotografia) {
      this.usuarioForm.get('fotografia')?.setValue(fotografia);
    } else {
      this.imagenPreview = null;
    }

    console.log('Editando usuario:', usuario);
    console.log('Valores del formulario:', this.usuarioForm.value);
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
      error: (error) => {
        let mensajeError = 'Error al eliminar usuario';
        const errorMsg = error?.error?.message || '';
        if (
          errorMsg.toLowerCase().includes('violates foreign key constraint') ||
          errorMsg.toLowerCase().includes('is still referenced')
        ) {
          mensajeError =
            'No se puede eliminar el usuario porque está relacionado con la bitácora.';
        }
        this.error = mensajeError;
        alert(this.error);
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
      usuarioModificacion: 'ADMIN', // temporal
    };

    if (!usuario.idUsuario) {
      alert('No se puede actualizar un usuario sin ID');
      return;
    }

    console.log('Usuario a actualizar:', usuario);

    this.usuarioService.updateUsuario(usuario.idUsuario, usuario).subscribe({
      next: () => {
        alert('Usuario actualizado correctamente');
        this.ngOnInit();
        this.onReset();
      },
      error: () => {
        this.error = 'Error al actualizar usuario';
      },
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
      respuesta: '',
    });
    this.isEditMode = false; // volvemos al modo agregar
  }
}
