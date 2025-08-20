import { Component } from '@angular/core';
import { UsuarioService } from '../../../service/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-loginusuarios',
  standalone: false,
  templateUrl: './loginusuarios.component.html',
  styleUrl: './loginusuarios.component.css'
})
export class LoginusuariosComponent {

  usuario: any = {};

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  login() {
    let validarFormulario: any = document.getElementById("loginForm");
    if (validarFormulario.reportValidity()) {
      console.log(this.usuario);
      this.usuarioService.login(this.usuario).subscribe(
        (u: any) => {
          console.log(u);
          this.darBienvenida(u);
        },
        (error:Error) => {
          console.error(error.message);
        }
      );
    }
  }

  darBienvenida(usuario: any) {
    if (usuario) {
      let t = JSON.stringify(usuario);
      localStorage.setItem("usuario", t);
      this.usuario = {};

      if (usuario.idRole === 2) {
        this.router.navigate(['/listusuarios']);
      }
    } else {
      alert("Invalid credentials");
    }
  }
}
