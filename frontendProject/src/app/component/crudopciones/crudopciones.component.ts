import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OpcionService } from '../../service/opcion.service';
import { Opcion } from '../../entity/opcion';

@Component({
  selector: 'app-crudopciones',
  standalone: false,
  templateUrl: './crudopciones.component.html',
  styleUrl: './crudopciones.component.css'
})
export class CrudopcionesComponent implements OnInit {
  opciones: Opcion[] = [];
  opcionForm!: FormGroup;
  editando: boolean = false;
  idEditando: number | null = null;
  error = '';

  constructor(
    private opcionService: OpcionService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.opcionForm = this.fb.group({
      idMenu: ['', Validators.required],
      nombre: ['', Validators.required],
      ordenmenu: ['', Validators.required],
      pagina: ['', Validators.required],
      descripcion: [''],
      url: [''],
      fechacreacion: [''],
      usuariocreacion: [''],
      fechamodificacion: [''],
      usuariomodificacion: ['']
    });
    this.cargarOpciones();
  }

  cargarOpciones() {
    this.opcionService.getOpciones().subscribe({
      next: (data) => this.opciones = data,
      error: () => this.error = 'Error al cargar opciones'
    });
  }

  onSubmit() {
    if (this.opcionForm.invalid) {
      this.opcionForm.markAllAsTouched();
      return;
    }
    const opcion: Opcion = {
      ...this.opcionForm.value,
      fechacreacion: new Date().toISOString(),
      usuariocreacion: 'ADMIN',
      fechamodificacion: '',
      usuariomodificacion: ''
    };
    this.opcionService.createOpcion(opcion).subscribe({
      next: () => {
        this.cargarOpciones();
        this.onReset();
        alert('Opción creada correctamente');
      },
      error: () => this.error = 'Error al crear opción'
    });
  }

  onEdit(opcion: Opcion) {
    this.editando = true;
    this.idEditando = opcion.idOpcion;
    this.opcionForm.patchValue(opcion);
  }

  onUpdate() {
    if (this.opcionForm.invalid || this.idEditando == null) return;
    const opcion: Opcion = {
      ...this.opcionForm.value,
      fechamodificacion: new Date().toISOString(),
      usuariomodificacion: 'ADMIN'
    };
    this.opcionService.updateOpcion(this.idEditando, opcion).subscribe({
      next: () => {
        this.cargarOpciones();
        this.onReset();
        alert('Opción actualizada correctamente');
      },
      error: () => this.error = 'Error al actualizar opción'
    });
  }

  onDelete(id: number) {
    if (!confirm('¿Seguro que desea eliminar esta opción?')) return;
    this.opcionService.deleteOpcion(id).subscribe({
      next: () => {
        this.cargarOpciones();
        alert('Opción eliminada correctamente');
      },
      error: () => this.error = 'Error al eliminar opción'
    });
  }

  onReset() {
    this.opcionForm.reset();
    this.editando = false;
    this.idEditando = null;
  }
}
