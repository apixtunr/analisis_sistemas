import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PersonaService } from '../../service/persona.service';
import { GeneroService } from '../../service/genero.service';
import { EstadoCivilService } from '../../service/estadocivil.service';
import { TipoDocumentoService } from '../../service/tipodocumento.service';
import { PermisoService } from '../../service/permisoservice';
import { Persona } from '../../entity/persona';
import { EstadoCivil } from '../../entity/estadoCivil';
import { TipoDocumento } from '../../entity/tipoDocumento';
import { Genero } from '../../entity/genero';
import { RolOpcion } from '../../entity/rolopcion';

@Component({
  selector: 'app-gestionpersonas',
  standalone: false,
  templateUrl: './gestionpersonas.component.html',
  styleUrl: './gestionpersonas.component.css'
})
export class GestionpersonasComponent implements OnInit {
  permisosUsuario: RolOpcion | undefined;
  isEditMode = false;

  constructor(
    private personaService: PersonaService,
    private generoService: GeneroService,
    private estadoCivilService: EstadoCivilService,
    private tipoDocumentoService: TipoDocumentoService,
    private fb: FormBuilder,
    private permisoService: PermisoService
  ) {}

  loading = true;
  error = '';

  personaForm!: FormGroup;
  personas: Persona[] = [];
  estadosCiviles: EstadoCivil[] = [];
  tiposDocumento: TipoDocumento[] = [];
  //documentosPersona: DocumentoPersona[] = [];
  generos: Genero[] = [];

  // Método para inicializar el componente
  ngOnInit(): void {
    this.personaForm = this.fb.group({
      idPersona: [null],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      fechaNacimiento: ['', Validators.required],
      idGenero: [null, Validators.required],
      direccion: [''],
      telefono: [''],
      correoElectronico: ['', [Validators.email]],
      idEstadoCivil: [null, Validators.required],
      idTipoDocumento: [null, Validators.required],
      numeroDocumento: ['', Validators.required],
      // Campos de auditoría (solo lectura)
      fechaCreacion: [{value: '', disabled: true}],
      usuarioCreacion: [{value: '', disabled: true}],
      fechaModificacion: [{value: '', disabled: true}],
      usuarioModificacion: [{value: '', disabled: true}]
    });

    // Obtener permisos para gestión de personas (asumiendo idOpcion=10 para personas)
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    const idRole = usuario.rol;
    this.permisoService.getPermisos(128, idRole).subscribe((permiso) => {
      this.permisosUsuario = permiso;
    });

    // Cargar personas
    this.personaService.getPersonas().subscribe({
      next: (data) => {
        this.personas = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar personas';
        this.loading = false;
      }
    });

    // Cargar géneros
    this.generoService.getGeneros().subscribe({
      next: (data) => {
        this.generos = data;
      },
      error: (err) => {
        console.error('Error al cargar géneros:', err);
      }
    });

    // Cargar estados civiles
    this.estadoCivilService.getEstadosCiviles().subscribe({
      next: (data) => {
        this.estadosCiviles = data;
      },
      error: (err) => {
        console.error('Error al obtener estados civiles:', err);
      }
    });

    // Cargar tipos de documento
    this.tipoDocumentoService.getTiposDocumento().subscribe({
      next: (data) => {
        this.tiposDocumento = data;
      },
      error: (err) => {
        console.error('Error al obtener tipos de documento:', err);
      }
    });
  }

  // Método para obtener el nombre del estado civil por ID
  getEstadoCivilNombre(idEstadoCivil: number): string {
    const estadoCivil = this.estadosCiviles.find(e => e.idEstadoCivil === idEstadoCivil);
    return estadoCivil ? estadoCivil.nombre : 'No especificado';
  }

  // Método para obtener el nombre del género por ID
  getGeneroNombre(idGenero: number): string {
    const genero = this.generos.find(g => g.idgenero === idGenero);
    return genero ? genero.nombre : 'No especificado';
  }

  // Método para obtener el nombre del tipo de documento por ID
  getTipoDocumentoNombre(idTipoDocumento?: number): string {
    if (!idTipoDocumento) return ''; //Agregar mensaje vacio si no hay tipo de documento
    const tipoDoc = this.tiposDocumento.find(t => t.idTipoDocumento === idTipoDocumento);
    return tipoDoc ? tipoDoc.nombre : ''; //Agregar mensaje vacio si no se encuentra el tipo de documento
  }

  // Método para crear persona
  onSubmit() {
    if (this.personaForm.invalid) {
      this.personaForm.markAllAsTouched();
      console.log('No se pudo crear la persona: formulario inválido');
      return;
    }

    const usuarioLocal = JSON.parse(localStorage.getItem('usuario') || '{}');
    const usuarioCreacion = usuarioLocal.nombre || 'system';

    const persona: Persona = {
      ...this.personaForm.value,
      fechaNacimiento: this.toDateOnly(this.personaForm.value.fechaNacimiento),
      fechaCreacion: new Date().toISOString(),
      usuarioCreacion: usuarioCreacion,
      fechaModificacion: null,
      usuarioModificacion: null
    };

    this.personaService.createPersona(persona).subscribe({
      next: () => {
        alert('Persona creada correctamente.');
        console.log('Persona creada correctamente');
        this.onReset();
        // Recargar la lista
        this.personaService.getPersonas().subscribe(data => {
          this.personas = data;
        });
      },
      error: (error) => {
        console.error('Error al crear persona:', error);
        alert('Error al crear la persona. Revise los datos e intente nuevamente.');
      }
    });
  }

  // Editar persona (trae los datos al formulario)
  onEdit(persona: Persona) {
    this.personaForm.patchValue(persona);
    this.isEditMode = true;
    console.log('Editando persona:', persona);
    console.log('Valores del formulario:', this.personaForm.value);
  }

  // Método para eliminar persona
  onDelete(idPersona: number) {
    const confirmado = confirm('¿Estás seguro de eliminar esta persona?');
    if (!confirmado) return;

    this.personaService.deletePersona(idPersona).subscribe({
      next: () => {
        alert('Persona eliminada correctamente.');
        console.log('Persona eliminada correctamente');
        // Recargar la lista
        this.personaService.getPersonas().subscribe(data => {
          this.personas = data;
        });
      },
      error: (error) => {
        console.error('Error al eliminar persona:', error);
        alert('Error al eliminar la persona.');
      }
    });
  }

  // Método para actualizar persona
  onUpdate() {
    if (this.personaForm.invalid) {
      this.personaForm.markAllAsTouched();
      return;
    }

    const changes = this.personaForm.value;

    // Auditoría con el usuario logueado
    const usuarioLocal = JSON.parse(localStorage.getItem('usuario') || '{}');
    const usuarioModificacion = usuarioLocal.nombre || 'system';

    if (!changes.idPersona) {
      console.error('No se puede actualizar: ID de persona no encontrado');
      return;
    }

    // Asegurar que el ID es un número
    const personaId = Number(changes.idPersona);
    if (isNaN(personaId)) {
      console.error('ID de persona inválido:', changes.idPersona);
      return;
    }

    // Buscar la persona actual en la lista cargada
    const existing = this.personas.find(p => p.idPersona === personaId);

    // Normalizar tipos
    const normalizedChanges = {
      ...changes,
      idEstadoCivil: Number(changes.idEstadoCivil),
      idGenero: Number(changes.idGenero),
      idTipoDocumento: changes.idTipoDocumento ? Number(changes.idTipoDocumento) : null
    };

    // Función para mandar el update con merge
    const doUpdate = (currentPersona: Persona) => {
      const updatedPersona: Persona = {
        ...currentPersona,
        ...normalizedChanges,
        idPersona: personaId, // Asegurar que el ID sea correcto
        fechaNacimiento: this.toDateOnly(normalizedChanges.fechaNacimiento),
        fechaModificacion: new Date().toISOString(),
        usuarioModificacion: usuarioModificacion
      };

      this.personaService.updatePersona(personaId, updatedPersona).subscribe({
        next: () => {
          alert('Persona actualizada correctamente.');
          console.log('Persona actualizada correctamente');
          this.onReset();
          // Recargar la lista
          this.personaService.getPersonas().subscribe(data => {
            this.personas = data;
          });
        },
        error: (error: any) => {
          console.error('Error al actualizar persona:', error);
          alert('Error al actualizar la persona.');
        }
      });
    };

    if (existing) {
      doUpdate(existing);
    } else {
      console.error('Persona no encontrada en la lista local');
      alert('Error: Persona no encontrada');
    }
  }

  // Método para resetear el formulario
  onReset() {
    this.personaForm.reset({
      idPersona: null,
      nombre: '',
      apellido: '',
      fechaNacimiento: '',
      idGenero: null,
      direccion: '',
      telefono: '',
      correoElectronico: '',
      idEstadoCivil: null,
      idTipoDocumento: null,
      numeroDocumento: '',
      // Limpiar campos de auditoría
      fechaCreacion: '',
      usuarioCreacion: '',
      fechaModificacion: '',
      usuarioModificacion: ''
    });
    this.isEditMode = false;
  }

  // Normaliza a 'YYYY-MM-DD' sin hora ni zona
  private toDateOnly(value: any): string {
    if (!value) return '';
    // Si ya viene como 'YYYY-MM-DD', úsalo tal cual
    if (typeof value === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(value)) return value;

    const d = new Date(value); // puede venir como Date (MatDatepicker) o string con hora
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
