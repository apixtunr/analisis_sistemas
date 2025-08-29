import { Component, OnInit } from '@angular/core';
import { Empresa } from '../../entity/empresa';
import { EmpresaService } from '../../service/empresa.service';

@Component({
    selector: 'app-crudempresas',
    templateUrl: './crudempresas.component.html',
    styleUrls: ['./crudempresas.component.css'],
    standalone: false
})

export class CrudempresasComponent implements OnInit {
  loading = true;
  error = '';
  empresas: Empresa[] = [];   // lista

  empresa: Empresa = {        // objeto para crear/editar
    idEmpresa: 0,
    nombre: '',
    direccion: '',
    nit: '',
    fechaCreacion: '',
    fechaModificacion: '',
    usuarioCreacion: '',
    usuarioModificacion: '',
    passwordCantidadCaducidadDias: 0,
    passwordCantidadCaracteresEspeciales: 0,
    passwordCantidadMayusculas: 0,
    passwordCantidadMinusculas: 0,
    passwordCantidadNumeros: 0,
    passwordCantidadPreguntasValidar: 0,
    passwordIntentosAntesDeBloquear: 0,
    passwordLargo: 0
  };

  constructor(private empresaService: EmpresaService) {}

  ngOnInit(): void {
    this.empresaService.getEmpresas().subscribe({
      next: (data) => {
        this.empresas = data; // aquí cargás la lista
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar empresas';
        this.loading = false;
      }
    });
  }

  // Crear nueva empresa
  onSubmit() {
    // crear el Json con fechas formateadas para el backend
  const payload = {
    ...this.empresa,
    fechaCreacion: this.formatDateTime(this.empresa.fechaCreacion),
    fechaModificacion: this.formatDateTime(this.empresa.fechaModificacion)
  };

  // enviar al backend
  this.empresaService.createEmpresa(payload).subscribe({
    next: () => {
      alert('Empresa guardada.');
      this.ngOnInit(); // refresca la lista
    },
    error: (err) => {
      this.error = 'Error al guardar empresa.';
    }
  });
  }

  // Editar empresa (trae los datos al formulario)
  onEdit(emp: Empresa) {
    this.empresa = { ...emp }; // copia al form
  }

  //Método para eliminar empresa
  onDelete(nit: string) {
    this.empresaService.deleteEmpresa(nit).subscribe({
      next: () => {
        alert('Empresa eliminada.');
        this.ngOnInit();
      },
      error: () => {
        this.error = 'Error al eliminar empresa.';
      }
    });
  }

  // Resetear formulario
  onReset() {
    this.empresa = {
      idEmpresa: 0,
      nombre: '',
      direccion: '',
      nit: '',
      fechaCreacion: '',
      fechaModificacion: '',
      usuarioCreacion: '',
      usuarioModificacion: '',
      passwordCantidadCaducidadDias: 0,
      passwordCantidadCaracteresEspeciales: 0,
      passwordCantidadMayusculas: 0,
      passwordCantidadMinusculas: 0,
      passwordCantidadNumeros: 0,
      passwordCantidadPreguntasValidar: 0,
      passwordIntentosAntesDeBloquear: 0,
      passwordLargo: 0
    };
  }

  // Formatear fecha para enviar al backend
formatDateTime(date: string): string {
  if (!date) return '';
  // Para LocalDateTime de Spring
  if (!date.includes('T')) {
    return date + 'T00:00:00';
  }
  return date;
}

 }
