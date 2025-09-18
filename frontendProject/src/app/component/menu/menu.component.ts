import { Component } from '@angular/core';
import { EstructuraMenuService } from '../../service/menu.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  estructuraMenu: any[] = [];

  // Variables para controlar el estado de los menús
  moduloAbierto: number | null = null;
  menuAbierto: number | null = null;

  constructor(
    private estructuraMenuService: EstructuraMenuService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.estructuraMenuService.getEstructuraMenu().subscribe(data => {
      this.estructuraMenu = data;
    });
  }

  toggleModulo(idModulo: number) {
    this.moduloAbierto = this.moduloAbierto === idModulo ? null : idModulo;
    this.menuAbierto = null; // opcional: cierra menús al cerrar módulo
  }
  // Alterna el estado del menú
  toggleMenu(idMenu: number) {
    this.menuAbierto = this.menuAbierto === idMenu ? null : idMenu;
  }

  logout(): void {
  localStorage.removeItem("usuario");
  this.router.navigate(['/loginusuarios']);
}
}
