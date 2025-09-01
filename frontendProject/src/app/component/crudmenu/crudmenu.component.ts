import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CrudmenuService } from '../../service/crudmenu.service';
import { ModuloService } from '../../service/modulo.service';
import { Menu } from '../../entity/menu';
import { Modulo } from '../../entity/modulo';

@Component({
  selector: 'app-crudmenu',
  standalone: false,
  templateUrl: './crudmenu.component.html',
  styleUrl: './crudmenu.component.css'
})
export class CrudmenuComponent implements OnInit {
  menuForm!: FormGroup;
  menus: Menu[] = [];
  modulos: Modulo[] = [];
  editando: boolean = false;
  idEditando: number | null = null;
  error = '';

  constructor(
    private fb: FormBuilder,
    private crudmenuService: CrudmenuService,
    private moduloService: ModuloService
  ) {}

  ngOnInit(): void {
    this.menuForm = this.fb.group({
      idmodulo: ['', Validators.required],
      nombre: ['', Validators.required],
      ordenmenu: ['', Validators.required],
      fechacreacion: [''],
      usuariocreacion: [''],
      fechamodificacion: [''],
      usuariomodificacion: ['']
    });
    this.cargarMenus();
    this.cargarModulos();
  }

  cargarMenus() {
    this.crudmenuService.getMenus().subscribe({
      next: (data) => this.menus = data,
      error: () => this.error = 'Error al cargar menús'
    });
  }

  cargarModulos() {
    this.moduloService.getModulos().subscribe({
      next: (data) => this.modulos = data,
      error: () => this.error = 'Error al cargar módulos'
    });
  }

  onSubmit() {
    if (this.menuForm.invalid) {
      this.menuForm.markAllAsTouched();
      return;
    }
    // Obtener usuario del localStorage
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    const nombreUsuario = usuario?.nombre || 'system';

    const menu: Menu = {
      ...this.menuForm.value,
      fechacreacion: new Date(), // <-- Esto envía un objeto Date, no string
      usuariocreacion: nombreUsuario,
      fechamodificacion: null,
      usuariomodificacion: null
    };
    this.crudmenuService.createMenu(menu).subscribe({
      next: () => {
        this.cargarMenus();
        this.onReset();
        alert('Menú creado correctamente');
      },
      error: () => this.error = 'Error al crear menú'
    });
  }

  onEdit(menu: Menu) {
    this.editando = true;
    this.idEditando = (menu as any).idmenu;
    this.menuForm.patchValue(menu);
  }

  onUpdate() {
    if (this.menuForm.invalid || this.idEditando == null) return;
    // Obtener usuario del localStorage
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    const nombreUsuario = usuario?.nombre || 'system';

    const menu: Menu = {
      ...this.menuForm.value,
      fechamodificacion: new Date().toISOString(),
      usuariomodificacion: nombreUsuario
    };
    this.crudmenuService.updateMenu(this.idEditando, menu).subscribe({
      next: () => {
        this.cargarMenus();
        this.onReset();
        alert('Menú actualizado correctamente');
      },
      error: () => this.error = 'Error al actualizar menú'
    });
  }

  onDelete(id: number) {
    if (!confirm('¿Seguro que desea eliminar este menú?')) return;
    this.crudmenuService.deleteMenu(id).subscribe({
      next: () => {
        this.cargarMenus();
        alert('Menú eliminado correctamente');
      },
      error: () => this.error = 'Error al eliminar menú'
    });
  }

  onReset() {
    this.menuForm.reset();
    this.editando = false;
    this.idEditando = null;
  }
  getNombreModulo(idmodulo: number): string {
    const modulo = this.modulos.find(m => m.idModulo == idmodulo);
    return modulo ? modulo.nombre : '';
  }
}
