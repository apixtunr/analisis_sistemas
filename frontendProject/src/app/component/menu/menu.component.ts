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

  constructor(
    private estructuraMenuService: EstructuraMenuService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.estructuraMenuService.getEstructuraMenu().subscribe(data => {
      this.estructuraMenu = data;
    });
  }

  logout(): void {
    localStorage.removeItem("usuario");
    this.router.navigate(['/login']);
  }
}
