import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';
import { CrudempresasComponent } from './component/crudempresas/crudempresas.component';
import { CommonModule } from '@angular/common';
import { CrudroleComponent } from './component/crudrole/crudrole.component';
import { CrudmoduloComponent } from './component/crudmodulo/crudmodulo.component';
import { CrudopcionesComponent } from './component/crudopciones/crudopciones.component';
import { CrudmenuComponent } from './component/crudmenu/crudmenu.component';
import { MenuComponent } from './component/menu/menu.component';
import { CrudusuariosComponent } from './component/crudusuarios/crudusuarios.component';
import { AsignacionrolopcionComponent } from './component/asignacionrolopcion/asignacionrolopcion.component';

@NgModule({
  declarations: [
    AppComponent,
    ListusuariosComponent,
    LoginusuariosComponent,
    CrudempresasComponent,
    CrudroleComponent,
    CrudmoduloComponent,
    CrudmenuComponent,
    CrudopcionesComponent,
    MenuComponent,
    CrudusuariosComponent,
    AsignacionrolopcionComponent
  ],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,

  ],
  providers: [provideHttpClient(withInterceptorsFromDi())]
})
export class AppModule { }
