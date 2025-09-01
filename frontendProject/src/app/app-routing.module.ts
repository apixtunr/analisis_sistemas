import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';
import { CrudempresasComponent } from './component/crudempresas/crudempresas.component';
import { CrudmenuComponent } from './component/crudmenu/crudmenu.component';
import { CrudopcionesComponent } from './component/crudopciones/crudopciones.component';
import { MenuComponent } from './component/menu/menu.component';
import { CrudmoduloComponent } from './component/crudmodulo/crudmodulo.component';
import { CrudroleComponent } from './component/crudrole/crudrole.component';
import { CrudusuariosComponent } from './component/crudusuarios/crudusuarios.component';

const routes: Routes = [

  { path: 'crudempresas', component: CrudempresasComponent },
  { path: 'crudmenu', component: CrudmenuComponent },
  { path: 'crudmodulo', component: CrudmoduloComponent },
  { path: 'crudopciones', component: CrudopcionesComponent },
  { path: 'crudrole', component: CrudroleComponent },
  { path: 'listusuarios', component: ListusuariosComponent },
  { path: 'loginusuarios', component: LoginusuariosComponent },
  { path: 'menu', component: MenuComponent },
  { path: 'crudusuarios', component: CrudusuariosComponent },
  { path: '', redirectTo: 'loginusuarios', pathMatch: 'full' },
  { path: '**', redirectTo: 'loginusuarios', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
