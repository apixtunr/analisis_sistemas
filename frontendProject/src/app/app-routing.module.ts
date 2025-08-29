import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';
import { CrudempresasComponent } from './component/crudempresas/crudempresas.component';
<<<<<<< HEAD
import { RoleComponent } from './component/role/role.component';
import { OpcionComponent } from './component/opcion/opcion.component';
=======
import { CrudusuariosComponent } from './component/crudusuarios/crudusuarios.component';
import { CrudsucursalesComponent } from './component/crudsucursales/crudsucursales.component';
>>>>>>> 9384d2a161bbc177ffee80817843399dc0f9fbd9

const routes: Routes = [
  { path: 'listusuarios', component: ListusuariosComponent },
  { path: 'loginusuarios', component: LoginusuariosComponent },
  { path: 'crudempresas', component: CrudempresasComponent },
<<<<<<< HEAD
  { path: 'role', component: RoleComponent },
  { path: 'opcion', component: OpcionComponent },
=======
  { path: 'crudusuarios', component: CrudusuariosComponent },
  { path: 'crudsucursales', component: CrudsucursalesComponent },
>>>>>>> 9384d2a161bbc177ffee80817843399dc0f9fbd9
  { path: '', redirectTo: 'loginusuarios', pathMatch: 'full' },
  { path: '**', redirectTo: 'loginusuarios', pathMatch: 'full' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
