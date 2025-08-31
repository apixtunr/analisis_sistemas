import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from '../../../service/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-loginusuarios',
  templateUrl: './loginusuarios.component.html',
  styleUrls: ['./loginusuarios.component.css'],
  standalone: false
})
export class LoginusuariosComponent {
  loginForm: FormGroup;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.loginForm = this.fb.group({
      correoElectronico: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  login() {
    if (this.loginForm.valid) {
      const usuario = this.loginForm.value;
      this.usuarioService.login(usuario).subscribe(
        (u: any) => {
          this.darBienvenida(u);
        },
        (error: Error) => {
          console.error(error.message);
        }
      );
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  darBienvenida(usuario: any) {
    if (usuario) {
      localStorage.setItem("usuario", JSON.stringify(usuario));
      if (usuario.idRole === 2) {
        this.router.navigate(['/menu']);
        alert("Bienvenido " + usuario.nombre + " " + usuario.apellido);
      }
    } else {
      alert("Invalid credentials");
    }
  }
}
