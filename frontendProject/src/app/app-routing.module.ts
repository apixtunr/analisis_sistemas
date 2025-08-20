import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';

const routes: Routes = [
  { path: 'listusuarios', component: ListusuariosComponent },
  { path: 'loginusuarios', component: LoginusuariosComponent },
  { path: '', redirectTo: 'loginusuarios', pathMatch: 'full' },
  { path: '**', redirectTo: 'loginusuarios', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
