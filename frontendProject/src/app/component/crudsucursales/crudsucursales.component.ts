import { Sucursal } from './../../entity/sucursal';
import { Component, OnInit } from '@angular/core';
import { SucursalService } from '../../service/sucursal.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-crudsucursales',
  standalone: false,
  templateUrl: './crudsucursales.component.html',
  styleUrl: './crudsucursales.component.css',
})
export class CrudsucursalesComponent implements OnInit {
  constructor(
    private sucursalService: SucursalService,
    private fb: FormBuilder
  ) {}

  loading = true;
  error = '';

  sucursalForm!: FormGroup;
  sucursales: any[] = []; // lista de sucursales

  //Método para inicializar el componente
  ngOnInit(): void {
    this.sucursalForm = this.fb.group({
      idSucursal: [0],
      nombre: ['', Validators.required],
      direccion: ['', Validators.required],
      idEmpresa: [0, Validators.required],
      fechaCreacion: [new Date(), Validators.required],
      usuarioCreacion: ['', Validators.required],
      fechaModificacion: [new Date(), Validators.required],
      usuarioModificacion: ['', Validators.required],
    });

    this.sucursalService.getSucursales().subscribe({
      next: (data) => {
        this.sucursales = data; // aquí cargás la lista
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar sucursales';
        this.loading = false;
      },
    });
  }

  //Método para crear sucursal
  onSubmit() {
    if (this.sucursalForm.invalid) {
      this.sucursalForm.markAllAsTouched();
      return;
    }

    const sucursal: Sucursal = {
      ...this.sucursalForm.value,
      //Valores por default
      fechaCreacion: new Date(),
      usuarioCreacion: 'ADMIN',
      fechaModificacion: new Date(),
      usuarioModificacion: 'ADMIN',
    };

    this.sucursalService.createSucursal(sucursal).subscribe({
      next: () => {
        alert('Sucursal creada correctamente.');
        this.ngOnInit(); // recargar la lista
      },
      error: () => {
        this.error = 'Error al crear sucursal';
      },
    });
  }

  //Editar sucursal (trae los datos al formulario)
  onEdit(sucursal: Sucursal) {
    this.sucursalForm.patchValue(sucursal); // copia al form
  }

  //Método para eliminar sucursal
  onDelete(idSucursal: number) {
    this.sucursalService.deleteSucursal(idSucursal).subscribe({
      next: () => {
        alert('Sucursal eliminada correctamente.');
        this.ngOnInit(); // recargar la lista
      },
      error: () => {
        this.error = 'Error al eliminar sucursal';
      },
    });
  }

  //Método para resetear el formulario
  onReset() {
    this.sucursalForm.reset({
      idSucursal: 0,
      nombre: '',
      direccion: '',
      idEmpresa: 0,
      fechaCreacion: new Date(),
      usuarioCreacion: '',
      fechaModificacion: new Date(),
      usuarioModificacion: '',
    });
  }

  onUpdate() {
    if (this.sucursalForm.invalid) return;

    const sucursal: Sucursal = {
      ...this.sucursalForm.value,
      //Valores por default
      fechaCreacion: new Date(),
      usuarioCreacion: 'ADMIN',
      fechaModificacion: new Date(),
      usuarioModificacion: 'ADMIN',
    };

    if (!sucursal.idSucursal) {
      alert('No se puede actualizar una sucursal sin ID');
      return;
    }

    this.sucursalService.updateSucursal(sucursal).subscribe({
      next: () => {
        alert('Sucursal actualizada correctamente.');
        this.ngOnInit(); // recargar la lista
        this.onReset();
      },
      error: () => {
        this.error = 'Error al actualizar sucursal';
      },
    });
  }
}
