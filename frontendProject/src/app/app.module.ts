import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListusuariosComponent } from './component/listusuarios/listusuarios.component';
import { LoginusuariosComponent } from './component/loginusuarios/loginusuarios/loginusuarios.component';
import { CrudempresasComponent } from './component/crudempresas/crudempresas.component';
import { OpcionComponent } from './component/opcion/opcion.component';
import { CommonModule } from '@angular/common';
import { RoleComponent } from './component/role/role.component';

@NgModule({
  declarations: [
    AppComponent,
    ListusuariosComponent,
    LoginusuariosComponent,
    CrudempresasComponent,
    OpcionComponent,
    RoleComponent
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
