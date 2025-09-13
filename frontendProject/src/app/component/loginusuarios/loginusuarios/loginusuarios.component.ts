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
      idUsuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    if (this.loginForm.valid) {
      const usuario = this.loginForm.value;
      this.usuarioService.login(usuario).subscribe(
        (resp: any) => {
          if (resp.success && resp.usuario) {
            localStorage.setItem("usuario", JSON.stringify(resp.usuario));
            // Redirección según rol
            if (resp.usuario.rol === 18) {
              this.router.navigate(['/menu']);
            } else if (resp.usuario.rol === 2) {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/home']);
            }
            alert("Bienvenido " + resp.usuario.nombre + " " + resp.usuario.apellido);
          } else {
            alert(resp.message || "Credenciales incorrectas");
          }
        },
        (error: Error) => {
          alert("Error de conexión o servidor");
          console.error(error.message);
        }
      );
    } else {
      this.loginForm.markAllAsTouched();
    }
  }



}
