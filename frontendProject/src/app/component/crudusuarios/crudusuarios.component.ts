import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../entity/usuario';

@Component({
  selector: 'app-crudusuarios',
  standalone: false,
  templateUrl: './crudusuarios.component.html',
  styleUrl: './crudusuarios.component.css',
})
export class CrudusuariosComponent implements OnInit {
  constructor(private usuarioService: UsuarioService) {}
  loading = true;
  error = '';
  usuarios: Usuario[] = []; // lista de usuarios

  usuario: Usuario = {
    // objeto para crear/editar
    idUsuario: '',
    nombre: '',
    apellido: '',
    fechaNacimiento: '',
    idStatusUsuario: 0,
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
  };

  //Método para inicializar el componente
  ngOnInit(): void {
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
    this.usuarioService.createUsuario(this.usuario).subscribe({
      next: () => {
        alert('Usuario creado correctamente.');
        this.ngOnInit(); // recargar la lista
      },
      error: () => {
        this.error = 'Error al crear usuario';
      },
    });
  }

  //Editar usuario (trae los datos al formulario)
  onEdit(usuario: Usuario) {
    this.usuario = { ...usuario }; // copia al form
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

  //Método para resetear el formulario
  onReset() {
    this.usuario = {
      idUsuario: '',
      nombre: '',
      apellido: '',
      fechaNacimiento: '',
      idStatusUsuario: 0,
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
    };
  }
}
