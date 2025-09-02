import { Sucursal } from './../../entity/sucursal';
import { Component, OnInit } from '@angular/core';
import { SucursalService } from '../../service/sucursal.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpresaService } from '../../service/empresa.service';
import { Empresa } from '../../entity/empresa';

@Component({
  selector: 'app-crudsucursales',
  standalone: false,
  templateUrl: './crudsucursales.component.html',
  styleUrl: './crudsucursales.component.css',
})
export class CrudsucursalesComponent implements OnInit {
  constructor(
    private sucursalService: SucursalService,
    private fb: FormBuilder,
    private empresaService: EmpresaService
  ) {}

  loading = true;
  error = '';

  sucursalForm!: FormGroup;
  sucursales: any[] = []; // lista de sucursales
  empresas: any[] = []; // lista de empresas

  //Método para inicializar el componente
  ngOnInit(): void {
    this.sucursalForm = this.fb.group({
      nombre: ['', Validators.required],
      direccion: ['', Validators.required],
      idEmpresa: [0, Validators.required]
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

    this.empresaService.getEmpresas().subscribe({
      next: (data) => this.empresas = data,
      error: (err) => console.error('Error cargando empresas', err)
    });
  }

  //Método para crear sucursal
  onSubmit() {
    if (this.sucursalForm.invalid) {
      this.sucursalForm.markAllAsTouched();
      return;
    }

    const usuarioLocal = JSON.parse(localStorage.getItem('usuario') || '{}');
    const usuarioCreacion = usuarioLocal.id || ''; // Esto jala el idUsuario

    const sucursal: Sucursal = {
      ...this.sucursalForm.value,

      //Valores por default
      fechaCreacion: new Date(),
      usuarioCreacion: usuarioCreacion,
      fechaModificacion: null,
      usuarioModificacion: null
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
    const confirmado = confirm('¿Estás seguro de eliminar esta sucursal?');
    if (!confirmado) return;

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
