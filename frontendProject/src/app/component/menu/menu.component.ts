import { Component } from '@angular/core';
import { EstructuraMenuService } from '../../service/menu.service';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  estructuraMenu: any[] = [];

  constructor(private estructuraMenuService: EstructuraMenuService) {}

  ngOnInit(): void {
    this.estructuraMenuService.getEstructuraMenu().subscribe(data => {
      this.estructuraMenu = data;
    });
  }
}
