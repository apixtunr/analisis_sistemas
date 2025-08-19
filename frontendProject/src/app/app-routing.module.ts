import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';

const routes: Routes = [
  { path: 'listusuarios', component: ListusuariosComponent },
  { path: '', redirectTo: 'listusuarios', pathMatch: 'full' },
  { path: '**', redirectTo: 'listusuarios', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
