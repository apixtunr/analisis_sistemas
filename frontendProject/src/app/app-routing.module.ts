import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';
import { CrudempresasComponent } from './component/crudempresas/crudempresas.component';
import { CrudmenuComponent } from './component/crudmenu/crudmenu.component';
import { CrudopcionesComponent } from './component/crudopciones/crudopciones.component';
import { MenuComponent } from './component/menu/menu.component';
import { CrudmoduloComponent } from './component/crudmodulo/crudmodulo.component';
import { CrudroleComponent } from './component/crudrole/crudrole.component';
import { CrudusuariosComponent } from './component/crudusuarios/crudusuarios.component';
import { AsignacionrolopcionComponent } from './component/asignacionrolopcion/asignacionrolopcion.component';
import { CrudGeneroComponent } from './component/crudgenero/crud-genero.component';
import { CrudstatususuarioComponent } from './component/crudstatususuario/crudstatususuario.component';
import { CrudsucursalesComponent } from './component/crudsucursales/crudsucursales.component';

const routes: Routes = [

  { path: 'crudempresas', component: CrudempresasComponent, canActivate: [AuthGuard] },
  { path: 'crudmenu', component: CrudmenuComponent, canActivate: [AuthGuard] },
  { path: 'crudmodulo', component: CrudmoduloComponent, canActivate: [AuthGuard] },
  { path: 'crudopciones', component: CrudopcionesComponent, canActivate: [AuthGuard] },
  { path: 'crudrole', component: CrudroleComponent, canActivate: [AuthGuard] },
  { path: 'listusuarios', component: ListusuariosComponent, canActivate: [AuthGuard] },
  { path: 'loginusuarios', component: LoginusuariosComponent },
  { path: 'menu', component: MenuComponent, canActivate: [AuthGuard] },
  { path: 'crud-genero', component: CrudGeneroComponent, canActivate: [AuthGuard] },
  { path: 'crudstatususuario', component: CrudstatususuarioComponent, canActivate: [AuthGuard] },
  { path: 'crudusuarios', component: CrudusuariosComponent, canActivate: [AuthGuard] },
  { path: 'asignacionrolopcion', component: AsignacionrolopcionComponent, canActivate: [AuthGuard] },
  { path: 'crudsucursales', component: CrudsucursalesComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'loginusuarios', pathMatch: 'full' },
  { path: '**', redirectTo: 'menu', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
