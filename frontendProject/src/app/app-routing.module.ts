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
import { CrudGeneroComponent } from './component/crudgenero/crud-genero.component';
import { CrudstatususuarioComponent } from './component/crudstatususuario/crudstatususuario.component';

const routes: Routes = [

  { path: 'crudempresas', component: CrudempresasComponent },
  { path: 'crudmenu', component: CrudmenuComponent },
  { path: 'crudmodulo', component: CrudmoduloComponent },
  { path: 'crudopciones', component: CrudopcionesComponent },
  { path: 'crudrole', component: CrudroleComponent },
  { path: 'listusuarios', component: ListusuariosComponent },
  { path: 'loginusuarios', component: LoginusuariosComponent },
  { path: 'menu', component: MenuComponent },
  { path: '', redirectTo: 'loginusuarios', pathMatch: 'full' },
  { path: '**', redirectTo: 'loginusuarios', pathMatch: 'full' },
  { path: 'crud-genero', component: CrudGeneroComponent },
  { path: 'crud-statususuario', component: CrudstatususuarioComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
