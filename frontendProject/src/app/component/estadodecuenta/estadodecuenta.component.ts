// src/app/estadodecuenta/estadodecuenta.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MovimientoCxcService } from '../../service/movimiento-cxc.service';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { PersonaDto } from '../../entity/persona.dto';
import { CuentaDto } from '../../entity/cuenta.dto';
// Ya no usaremos EstadoCuentaDto directamente para los movimientos si el backend devuelve un Map
// import { EstadoCuentaDto } from '../../entity/estado-cuenta.dto';
import { TipoMovimientoCxcDto } from '../../entity/tipo-movimiento-cxc.dto';

@Component({
  selector: 'app-estadodecuenta',
  templateUrl: './estadodecuenta.component.html',
  standalone: false
})
export class EstadodecuentaComponent implements OnInit {

  filtroForm!: FormGroup;
  personas: PersonaDto[] = [];
  cuentas: CuentaDto[] = [];
  // Aquí los movimientos serán de tipo 'any' o puedes definir una interfaz para la respuesta del backend
  movimientos: any[] = []; // Cambiamos EstadoCuentaDto[] a any[] para manejar el formato Map
  tipos: TipoMovimientoCxcDto[] = [];
  nombrePersona: string = '';
  nombreCuenta: string = '';
  saldoFinal: number = 0;
  totalCargos: number = 0;
  totalAbonos: number = 0;

  today: Date = new Date();

  constructor(
    private fb: FormBuilder,
    private service: MovimientoCxcService
  ) {}

  ngOnInit(): void {
    this.filtroForm = this.fb.group({
      idPersona: [null, Validators.required],
      idCuenta: [{ value: null, disabled: true }, Validators.required],
      fechaInicio: [null, Validators.required],
      fechaFin: [null, Validators.required]
    });

    this.service.getPersonas().subscribe(data => this.personas = data);
    this.cargarTiposMovimiento(); // Todavía útil para mostrar el nombre del tipo de movimiento
  }

  cargarTiposMovimiento(): void {
    this.service.getTiposMovimientoCxc().subscribe({
      next: (data) => this.tipos = data,
      error: () => console.error('Error cargando tipos de movimiento')
    });
  }

  cargarCuentas() {
    const idPersona = this.filtroForm.get('idPersona')?.value;
    this.filtroForm.get('idCuenta')?.reset();
    this.filtroForm.get('idCuenta')?.enable();

    this.service.getCuentasPorPersona(idPersona).subscribe(data => {
      this.cuentas = data;
    });
  }

  buscar() {
    if (this.filtroForm.invalid) return;

    const { idPersona, idCuenta, fechaInicio, fechaFin } = this.filtroForm.value;
    const fechaInicioIso = fechaInicio + 'T00:00:00';
    const fechaFinIso = fechaFin + 'T23:59:59';

    this.nombrePersona = this.personas.find(p => p.idPersona == idPersona)?.nombreCompleto ?? '';
    this.nombreCuenta = this.cuentas.find(c => c.idSaldoCuenta == idCuenta)?.nombreTipoSaldo ?? '';

    // Llamada al nuevo método del servicio
    this.service.getMovimientosConSaldos(idCuenta, fechaInicioIso, fechaFinIso)
      .subscribe(response => {
        // La respuesta del backend ya incluye los movimientos, totalAbonos, totalCargos y saldoFinal
        this.movimientos = response.movimientos;
        this.totalAbonos = response.totalAbonos;
        this.totalCargos = response.totalCargos;
        this.saldoFinal = response.saldoFinal; // El backend ya lo devuelve negativo si es débito
        // Ya no necesitamos this.calcularSaldoTotales(); porque el backend nos da los totales y saldos por movimiento
      }, error => {
        console.error('Error al obtener estado de cuenta con saldos', error);
        // Manejo de errores, por ejemplo, mostrar un mensaje al usuario
        this.movimientos = [];
        this.totalAbonos = 0;
        this.totalCargos = 0;
        this.saldoFinal = 0;
      });
  }

  limpiar() {
    this.filtroForm.reset();
    this.filtroForm.get('idCuenta')?.disable();
    this.movimientos = [];
    this.totalAbonos = 0;
    this.totalCargos = 0;
    this.saldoFinal = 0;
  }

  // Este método sigue siendo útil para mostrar el nombre del tipo de movimiento
  getTipoMovimientoNombre(idTipo: number): string {
    return this.tipos.find(t => t.idTipoMovimientoCxc === idTipo)?.nombre ?? '';
  }

  exportarPDF() {
    const doc = new jsPDF();
    const fechaEmision = new Date().toLocaleString();

    // Encabezado
    doc.setFontSize(12);
    doc.text(`Cliente: ${this.nombrePersona}`, 14, 10);
    doc.text(`Cuenta: ${this.nombreCuenta}`, 14, 17);
    doc.text(`Fecha emisión: ${fechaEmision}`, 14, 24);
    doc.text(
      `Período: ${this.filtroForm.value.fechaInicio} al ${this.filtroForm.value.fechaFin}`,
      14,
      31
    );

    // Tabla
    autoTable(doc, {
      startY: 38,
      head: [['Fecha', 'Descripción', 'Tipo', 'Valor', 'Saldo']],
      body: this.movimientos.map(m => [
        new Date(m.fechaMovimiento).toLocaleDateString('es-GT'),
        m.descripcion || '',
        this.getTipoMovimientoNombre(m.idTipoMovimientoCXC),
        (m.valorMovimiento ?? 0).toFixed(2),
        // El campo 'saldo' ya viene calculado desde el backend en cada movimiento
        (m.saldo ?? 0).toFixed(2)
      ])
    });

    // Pie de página
    const finalY = (doc as any).lastAutoTable.finalY + 10;
    doc.setFontSize(12);
    doc.text(`Total Cargos: ${this.totalCargos.toFixed(2)}`, 14, finalY);
    doc.text(`Total Abonos: ${this.totalAbonos.toFixed(2)}`, 14, finalY + 7);
    doc.text(`Saldo Final: ${this.saldoFinal.toFixed(2)}`, 14, finalY + 14);

    doc.save('estado_cuenta.pdf');
  }

  // ELIMINAMOS ESTE MÉTODO PORQUE EL CÁLCULO YA SE HACE EN EL BACKEND
  // calcularSaldoTotales(): void {
  //   let saldo = 0;
  //   this.totalAbonos = 0;
  //   this.totalCargos = 0;

  //   this.movimientos = this.movimientos.map(m => {
  //     // Si idTipoMovimientoCXC es 1, 2, 3, 4, 5, 6, 10, 27, 28 = DÉBITO (resta del saldo)
  //     if ([1, 2, 3, 4, 5, 6, 10, 27, 28].includes(m.idTipoMovimientoCXC)) {
  //       this.totalCargos += m.valorMovimiento;
  //       saldo -= m.valorMovimiento;
  //     }
  //     // Si idTipoMovimientoCXC es 7, 8, 9, 24, 26 = CRÉDITO (suma al saldo)
  //     else if ([7, 8, 9, 24, 26].includes(m.idTipoMovimientoCXC)) {
  //       this.totalAbonos += m.valorMovimiento;
  //       saldo += m.valorMovimiento;
  //     }

  //     return { ...m, saldo };
  //   });

  //   this.saldoFinal = saldo * -1;
  // }
}
