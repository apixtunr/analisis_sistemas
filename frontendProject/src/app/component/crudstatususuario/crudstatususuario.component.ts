import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StatusUsuario } from '../../entity/statusUsuario';
import { StatusUsuarioService } from '../../service/statusUsuario.service';

@Component({
  selector: 'app-crudstatususuario',
  standalone: false,
  templateUrl: './crudstatususuario.component.html',
  styleUrl: './crudstatususuario.component.css'
})
export class CrudstatususuarioComponent implements OnInit {
  loading = true;
  error = '';
  statusUsuarios: StatusUsuario[] = []; // lista
  currentUser: string = ''; // Usuario actual del localStorage

  statusUsuario: any = {
    // objeto para crear/editar (usando any para flexibilidad con fechas)
    idstatususuario: 0,
    nombre: '',
    fechaCreacion: '',
    usuarioCreacion: '',
    fechaModificacion: ''
  };

  constructor(private statusUsuarioService: StatusUsuarioService) {}

  ngOnInit(): void {
    this.getCurrentUser();
    this.initializeStatusUsuario();
    this.loadStatusUsuarios();
  }

  /**
   * Obtiene el usuario actual del localStorage
   */
  getCurrentUser(): void {
    try {
      // Intentar obtener el objeto JSON del localStorage
      const userDataString = localStorage.getItem('usuario') || 
                            localStorage.getItem('user') || 
                            localStorage.getItem('currentUser') ||
                            localStorage.getItem('userData');

      if (userDataString) {
        // Parsear el JSON
        const userData = JSON.parse(userDataString);
        
        // Extraer el email/correo del objeto
        this.currentUser = userData.email || 
                          userData.correo || 
                          userData.mail || 
                          userData.usuario ||
                          userData.username ||
                          'Usuario Anónimo';
      } else {
        this.currentUser = 'Usuario Anónimo';
      }
    } catch (error) {
      console.error('Error al parsear datos del usuario desde localStorage:', error);
      this.currentUser = 'Usuario Anónimo';
    }
  }

  /**
   * Inicializa el objeto statusUsuario con valores por defecto
   */
  initializeStatusUsuario(): void {
    const today = this.getCurrentDateForInput();
    this.statusUsuario = {
      idstatususuario: 0, // Se mantiene en 0, el backend lo asignará automáticamente
      nombre: '',
      fechaCreacion: today, // String en formato YYYY-MM-DD para el input
      usuarioCreacion: this.currentUser,
      fechaModificacion: ''
    };
  }

  loadStatusUsuarios(): void {
    this.statusUsuarioService.getStatusUsuarios().subscribe({
      next: (data) => {
        this.statusUsuarios = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar status de usuarios: ' + err.message;
        this.loading = false;
        console.error('Error al cargar status de usuarios:', err);
      }
    });
  }

  onSubmit(): void {
    // Validación básica
    if (!this.statusUsuario.nombre || this.statusUsuario.nombre.trim() === '') {
      this.error = 'El nombre del status de usuario es requerido.';
      return;
    }

    if (this.statusUsuario.idstatususuario === 0) {
      // Crear nuevo status usuario
      const newStatusUsuario: any = {
        nombre: this.statusUsuario.nombre.trim(), // Limpiar espacios
        fechaCreacion: this.statusUsuario.fechaCreacion ? new Date(this.statusUsuario.fechaCreacion) : new Date(),
        usuarioCreacion: this.statusUsuario.usuarioCreacion || this.currentUser
        // NO incluir fechaModificacion para creación
        // NO incluir idstatususuario para creación (el backend lo asignará)
      };

      console.log('Enviando nuevo status usuario:', newStatusUsuario); // Para debug

      this.statusUsuarioService.createStatusUsuario(newStatusUsuario).subscribe({
        next: (response) => {
          console.log('Respuesta del servidor:', response);
          alert('Status de usuario creado exitosamente.');
          this.onReset();
          this.loadStatusUsuarios();
        },
        error: (err) => {
          console.error('Error completo:', err);
          this.error = 'Error al crear el status de usuario. Revisa la consola para más detalles.';
          
          // Mostrar más información del error
          if (err.error && err.error.message) {
            this.error += ' ' + err.error.message;
          } else if (err.message) {
            this.error += ' ' + err.message;
          }
        }
      });
    } else {
      // Actualizar status usuario existente
      const statusUsuarioToUpdate: any = {
        idstatususuario: this.statusUsuario.idstatususuario,
        nombre: this.statusUsuario.nombre.trim(),
        fechaCreacion: this.statusUsuario.fechaCreacion ? new Date(this.statusUsuario.fechaCreacion) : new Date(),
        usuarioCreacion: this.statusUsuario.usuarioCreacion,
        fechaModificacion: new Date()
      };

      console.log('Enviando status usuario para actualizar:', statusUsuarioToUpdate); // Para debug

      this.statusUsuarioService.updateStatusUsuario(this.statusUsuario.idstatususuario, statusUsuarioToUpdate).subscribe({
        next: (response) => {
          console.log('Respuesta del servidor:', response);
          alert('Status de usuario actualizado exitosamente.');
          this.onReset();
          this.loadStatusUsuarios();
        },
        error: (err) => {
          console.error('Error completo:', err);
          this.error = 'Error al actualizar el status de usuario. Revisa la consola para más detalles.';
          
          if (err.error && err.error.message) {
            this.error += ' ' + err.error.message;
          } else if (err.message) {
            this.error += ' ' + err.message;
          }
        }
      });
    }
  }

  onEdit(statusUsr: StatusUsuario): void {
    // Preparar el objeto para edición
    this.statusUsuario = {
      idstatususuario: statusUsr.idstatususuario,
      nombre: statusUsr.nombre,
      fechaCreacion: this.formatDateForInput(statusUsr.fechaCreacion),
      usuarioCreacion: statusUsr.usuarioCreacion || this.currentUser,
      fechaModificacion: statusUsr.fechaModificacion ? this.formatDateForInput(statusUsr.fechaModificacion) : ''
    };
  }

  onDelete(idstatususuario: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar este status de usuario?')) {
      this.statusUsuarioService.deleteStatusUsuario(idstatususuario).subscribe({
        next: () => {
          alert('Status de usuario eliminado exitosamente.');
          this.loadStatusUsuarios();
        },
        error: (err) => {
          this.error = 'Error al eliminar el status de usuario: ' + err.message;
          console.error('Error al eliminar el status de usuario:', err);
        }
      });
    }
  }

  onReset(): void {
    this.initializeStatusUsuario();
    this.error = ''; // Limpiar errores al resetear
  }

  /**
   * Helper para formatear fechas para input[type="date"]
   * Los inputs de tipo 'date' esperan un string en formato 'YYYY-MM-DD'
   */
  formatDateForInput(date: Date | string | undefined): string {
    if (!date) return '';
    
    let d: Date;
    if (typeof date === 'string') {
      d = new Date(date);
    } else {
      d = date;
    }
    
    if (isNaN(d.getTime())) return '';
    
    const year = d.getFullYear();
    const month = ('0' + (d.getMonth() + 1)).slice(-2);
    const day = ('0' + d.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  /**
   * Helper para obtener la fecha actual en formato YYYY-MM-DD
   */
  getCurrentDateForInput(): string {
    return this.formatDateForInput(new Date());
  }
}