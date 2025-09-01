import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { FormsModule } from '@angular/forms'; // Importa FormsModule
import { Genero } from '../../entity/genero';
import { GeneroService } from '../../service/genero.service';

@Component({
  selector: 'app-crud-genero',
  standalone: false, // Cambia a true si lo quieres standalone o false si lo quieres en un módulo
  templateUrl: './crud-genero.component.html',
  styleUrl: './crud-genero.component.css'
})
export class CrudGeneroComponent implements OnInit {
  loading = true;
  error = '';
  generos: Genero[] = []; // lista

  genero: Genero = {
    // objeto para crear/editar
    idgenero: 0,
    nombre: '',
    fechaCreacion: new Date(),
    usuarioCreacion: '',
    fechaModificacion: undefined, // Puede ser undefined para que sea opcional
    usuarioModificacion: undefined // Puede ser undefined para que sea opcional
  };

  constructor(private generoService: GeneroService) {}

  ngOnInit(): void {
    this.loadGeneros();
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
    // Asegurarse de que las fechas sean objetos Date para el servicio, o convertirlas a string si el backend espera string
    const generoToSave = { ...this.genero };

    // Si tu backend espera un formato de fecha específico (ej: ISO string), conviértelo aquí
    // Por ejemplo: generoToSave.fechaCreacion = this.genero.fechaCreacion?.toISOString();

    if (this.genero.idgenero === 0) {
      // Crear nuevo género
      this.generoService.createGenero(generoToSave).subscribe({
        next: () => {
          alert('Género creado exitosamente.');
          this.onReset();
          this.loadGeneros();
        },
        error: (err) => {
          this.error = 'Error al crear el género: ' + err.message;
          console.error('Error al crear el género:', err);
        }
      });
    } else {
      // Actualizar género existente
      this.generoService.updateGenero(this.genero.idgenero, generoToSave).subscribe({
        next: () => {
          alert('Género actualizado exitosamente.');
          this.onReset();
          this.loadGeneros();
        },
        error: (err) => {
          this.error = 'Error al actualizar el género: ' + err.message;
          console.error('Error al actualizar el género:', err);
        }
      });
    }
  }

  onEdit(gen: Genero): void {
    // Copia profunda para evitar mutaciones directas y para manejar las fechas correctamente
    this.genero = {
      ...gen,
      fechaCreacion: gen.fechaCreacion ? new Date(gen.fechaCreacion) : new Date(),
      fechaModificacion: gen.fechaModificacion ? new Date(gen.fechaModificacion) : undefined
    };

    // Ajusta los valores de las fechas para el input[type="date"]
    // Los inputs de tipo 'date' esperan un string en formato 'YYYY-MM-DD'
    this.genero.fechaCreacion = this.formatDateForInput(this.genero.fechaCreacion);
    if (this.genero.fechaModificacion) {
      this.genero.fechaModificacion = this.formatDateForInput(this.genero.fechaModificacion);
    }
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
    this.genero = {
      idgenero: 0,
      nombre: '',
      fechaCreacion: new Date(),
      usuarioCreacion: '',
      fechaModificacion: undefined,
      usuarioModificacion: undefined
    };
    this.error = ''; // Limpiar errores al resetear
  }

  // Helper para formatear fechas para input[type="date"]
  formatDateForInput(date: Date | undefined): any { // Cambiado a any para flexibilidad temporal
    if (!date) return '';
    const d = new Date(date);
    const year = d.getFullYear();
    const month = ('0' + (d.getMonth() + 1)).slice(-2);
    const day = ('0' + d.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }
}