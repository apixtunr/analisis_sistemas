import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MovimientoCxcService } from '../../service/movimiento-cxc.service';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { PersonaDto } from '../../entity/persona.dto';
import { CuentaDto } from '../../entity/cuenta.dto';
import { EstadoCuentaDto } from '../../entity/estado-cuenta.dto';
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
  movimientos: EstadoCuentaDto[] = [];
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
    this.cargarTiposMovimiento();
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

    this.service.getEstadoCuenta(idCuenta, fechaInicioIso, fechaFinIso)
      .subscribe(data => {
        this.movimientos = data;
        this.calcularSaldoTotales();
      });
  }

  limpiar() {
    this.filtroForm.reset();
    this.filtroForm.get('idCuenta')?.disable();
    this.movimientos = [];
  }

  // MÉTODO NUEVO AGREGADO PARA RESOLVER EL ERROR
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

  calcularSaldoTotales(): void {
  let saldo = 0;
  this.totalAbonos = 0;
  this.totalCargos = 0;

  this.movimientos = this.movimientos.map(m => {
    // Si idTipoMovimientoCXC es 1, 2, 3, 4, 5, 6, 10, 27, 28 = DÉBITO (resta del saldo)
    if ([1, 2, 3, 4, 5, 6, 10, 27, 28].includes(m.idTipoMovimientoCXC)) {
      this.totalCargos += m.valorMovimiento;
      saldo -= m.valorMovimiento;
    }
    // Si idTipoMovimientoCXC es 7, 8, 9, 24, 26 = CRÉDITO (suma al saldo)
    else if ([7, 8, 9, 24, 26].includes(m.idTipoMovimientoCXC)) {
      this.totalAbonos += m.valorMovimiento;
      saldo += m.valorMovimiento;
    }

    return { ...m, saldo };
  });

  this.saldoFinal = saldo;
}
}
