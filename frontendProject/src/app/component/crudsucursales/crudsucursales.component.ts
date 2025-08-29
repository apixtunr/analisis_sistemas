import { Sucursal } from './../../entity/sucursal';
import { Component, OnInit } from '@angular/core';
import { SucursalService } from '../../service/sucursal.service';

@Component({
  selector: 'app-crudsucursales',
  standalone: false,
  templateUrl: './crudsucursales.component.html',
  styleUrl: './crudsucursales.component.css',
})
export class CrudsucursalesComponent implements OnInit {
  constructor(private sucursalService: SucursalService) {}
  loading = true;
  error = '';
  sucursales: Sucursal[] = []; // lista de sucursales
  sucursal: Sucursal = {
    // objeto para crear/editar
    idSucursal: 0,
    nombre: '',
    direccion: '',
    idEmpresa: 0,
    fechaCreacion: new Date(),
    usuarioCreacion: '',
    fechaModificacion: new Date(),
    usuarioModificacion: '',
  };

  //Método para inicializar el componente
  ngOnInit(): void {
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
    this.sucursalService.createSucursal(this.sucursal).subscribe({
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
    this.sucursal = { ...sucursal }; // copia al form
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
    this.sucursal = {
      idSucursal: 0,
      nombre: '',
      direccion: '',
      idEmpresa: 0,
      fechaCreacion: new Date(),
      usuarioCreacion: '',
      fechaModificacion: new Date(),
      usuarioModificacion: '',
    };
  }
}
