import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';
import { CrudempresasComponent } from './component/crudempresas/crudempresas.component';
<<<<<<< HEAD
import { OpcionComponent } from './component/opcion/opcion.component';
import { CommonModule } from '@angular/common';
import { RoleComponent } from './component/role/role.component';
=======
import { CrudusuariosComponent } from './component/crudusuarios/crudusuarios.component';
import { CrudsucursalesComponent } from './component/crudsucursales/crudsucursales.component';
import { CrudroleComponent } from './component/crudrole/crudrole.component';
import { CrudmoduloComponent } from './component/crudmodulo/crudmodulo.component';
>>>>>>> 9384d2a161bbc177ffee80817843399dc0f9fbd9

@NgModule({
  declarations: [
    AppComponent,
    ListusuariosComponent,
    LoginusuariosComponent,
    CrudempresasComponent,
<<<<<<< HEAD
    OpcionComponent,
    RoleComponent
=======
    CrudusuariosComponent,
    CrudsucursalesComponent,
    CrudroleComponent,
    CrudmoduloComponent
>>>>>>> 9384d2a161bbc177ffee80817843399dc0f9fbd9
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
