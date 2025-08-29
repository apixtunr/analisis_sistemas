import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';
import { CrudempresasComponent } from './component/crudempresas/crudempresas.component';
import { RoleComponent } from './component/role/role.component';
import { OpcionComponent } from './component/opcion/opcion.component';
import { CrudusuariosComponent } from './component/crudusuarios/crudusuarios.component';
import { CrudsucursalesComponent } from './component/crudsucursales/crudsucursales.component';

const routes: Routes = [
  { path: 'listusuarios', component: ListusuariosComponent },
  { path: 'loginusuarios', component: LoginusuariosComponent },
  { path: 'crudempresas', component: CrudempresasComponent },
  { path: 'role', component: RoleComponent },
  { path: 'opcion', component: OpcionComponent },
  { path: 'crudusuarios', component: CrudusuariosComponent },
  { path: 'crudsucursales', component: CrudsucursalesComponent },
  { path: '', redirectTo: 'loginusuarios', pathMatch: 'full' },
  { path: '**', redirectTo: 'loginusuarios', pathMatch: 'full' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
