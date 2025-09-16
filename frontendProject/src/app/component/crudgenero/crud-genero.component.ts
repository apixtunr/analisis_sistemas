import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { FormsModule } from '@angular/forms'; // Importa FormsModule
import { Genero } from '../../entity/genero';
import { GeneroService } from '../../service/genero.service';
import { PermisoService } from '../../service/permisoservice';
import { RolOpcion } from '../../entity/rolopcion';

@Component({
  selector: 'app-crud-genero',
  standalone: false, // Cambia a true si lo quieres standalone o false si lo quieres en un módulo
  templateUrl: './crud-genero.component.html',
  styleUrl: './crud-genero.component.css'
})
export class CrudGeneroComponent implements OnInit {
  permisosGenero: RolOpcion | undefined;
  isEditMode: boolean = false;
  loading = true;
  error = '';
  generos: Genero[] = []; // lista
  currentUser: string = ''; // Usuario actual del localStorage

  genero: any = {
    // objeto para crear/editar (usando any para flexibilidad con fechas)
    idgenero: 0,
    nombre: '',
    fechaCreacion: '',
    usuarioCreacion: '',
    fechaModificacion: '',
    usuarioModificacion: ''
  };
  
  constructor(private generoService: GeneroService, private permisoService: PermisoService) {}

  ngOnInit(): void {
    // Obtener permisos para géneros (idOpcion=3)
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    const idRole = usuario.rol;
    this.permisoService.getPermisos(3, idRole).subscribe(permiso => {
      this.permisosGenero = permiso;
    });
    this.getCurrentUser();
    this.initializeGenero();
    this.loadGeneros();
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
        // Ajusta estas propiedades según la estructura de tu JSON
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
   * Inicializa el objeto genero con valores por defecto
   */
  initializeGenero(): void {
    const today = this.getCurrentDateForInput();
    this.genero = {
      idgenero: 0, // Se mantiene en 0, el backend lo asignará automáticamente
      nombre: '',
      fechaCreacion: today, // String en formato YYYY-MM-DD para el input
      usuarioCreacion: this.currentUser,
      fechaModificacion: '',
      usuarioModificacion: ''
    };
  }

  loadGeneros(): void {
    this.generoService.getGeneros().subscribe({
      next: (data) => {
        this.generos = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar géneros: ' + err.message;
        this.loading = false;
        console.error('Error al cargar géneros:', err);
      }
    });
  }

  onSubmit(): void {
    // Validación básica
    if (!this.genero.nombre || this.genero.nombre.trim() === '') {
      this.error = 'El nombre del género es requerido.';
      return;
    }

    if (this.genero.idgenero === 0) {
      // Crear nuevo género
      const newGenero: any = {
        nombre: this.genero.nombre.trim(), // Limpiar espacios
        fechaCreacion: this.genero.fechaCreacion ? new Date(this.genero.fechaCreacion) : new Date(),
        usuarioCreacion: this.genero.usuarioCreacion || this.currentUser
        // NO incluir fechaModificacion ni usuarioModificacion para creación
        // NO incluir idgenero para creación (el backend lo asignará)
      };

      console.log('Enviando nuevo género:', newGenero); // Para debug

      this.generoService.createGenero(newGenero).subscribe({
        next: (response) => {
          console.log('Respuesta del servidor:', response);
          alert('Género creado exitosamente.');
          this.onReset();
          this.loadGeneros();
        },
        error: (err) => {
          console.error('Error completo:', err);
          this.error = 'Error al crear el género. Revisa la consola para más detalles.';
          
          // Mostrar más información del error
          if (err.error && err.error.message) {
            this.error += ' ' + err.error.message;
          } else if (err.message) {
            this.error += ' ' + err.message;
          }
        }
      });
    } else {
      // Actualizar género existente
      const generoToUpdate: any = {
        idgenero: this.genero.idgenero,
        nombre: this.genero.nombre.trim(),
        fechaCreacion: this.genero.fechaCreacion ? new Date(this.genero.fechaCreacion) : new Date(),
        usuarioCreacion: this.genero.usuarioCreacion,
        fechaModificacion: new Date(),
        usuarioModificacion: this.currentUser
      };

      console.log('Enviando género para actualizar:', generoToUpdate); // Para debug

      this.generoService.updateGenero(this.genero.idgenero, generoToUpdate).subscribe({
        next: (response) => {
          console.log('Respuesta del servidor:', response);
          alert('Género actualizado exitosamente.');
          this.onReset();
          this.loadGeneros();
        },
        error: (err) => {
          console.error('Error completo:', err);
          this.error = 'Error al actualizar el género. Revisa la consola para más detalles.';
          
          if (err.error && err.error.message) {
            this.error += ' ' + err.error.message;
          } else if (err.message) {
            this.error += ' ' + err.message;
          }
        }
      });
    }
  }

  onEdit(gen: Genero): void {
    // Preparar el objeto para edición
    this.genero = {
      idgenero: gen.idgenero,
      nombre: gen.nombre,
      fechaCreacion: this.formatDateForInput(gen.fechaCreacion),
      usuarioCreacion: gen.usuarioCreacion || this.currentUser,
      fechaModificacion: gen.fechaModificacion ? this.formatDateForInput(gen.fechaModificacion) : '',
      usuarioModificacion: gen.usuarioModificacion || ''
    };
  this.isEditMode = true;
  }

  onDelete(idgenero: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar este género?')) {
      this.generoService.deleteGenero(idgenero).subscribe({
        next: () => {
          alert('Género eliminado exitosamente.');
          this.loadGeneros();
        },
        error: (err) => {
          this.error = 'Error al eliminar el género: ' + err.message;
          console.error('Error al eliminar el género:', err);
        }
      });
    }
  }

  onReset(): void {
    this.initializeGenero();
    this.error = ''; // Limpiar errores al resetear
  this.isEditMode = false;
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