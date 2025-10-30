package com.SystemAnalisys.Project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SystemAnalisys.Project.dto.DocumentoPersonaDto;
import com.SystemAnalisys.Project.entity.Persona;
import com.SystemAnalisys.Project.service.PersonaService;


@RestController
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    // Obtiene la lista de todas las personas
    @GetMapping("api/list_persona")
    public List<Persona> getAllPersonas() {
        return personaService.getAllPersonas();
    }

    // Crea una nueva persona
    @PostMapping("api/create_persona")
    public Persona createPersona(@RequestBody Persona persona) {
        return personaService.save(persona);
    }

    // Actualiza una persona existente
    @PutMapping("api/update_persona/{id}")
    public Persona updatePersona(@PathVariable("id") Integer id, @RequestBody Persona updatedPersona) {
        Optional<Persona> personaOptional = personaService.findById(id);
        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            persona.setNombre(updatedPersona.getNombre());
            persona.setApellido(updatedPersona.getApellido());
            persona.setFechaNacimiento(updatedPersona.getFechaNacimiento());
            persona.setIdGenero(updatedPersona.getIdGenero());
            persona.setDireccion(updatedPersona.getDireccion());
            persona.setTelefono(updatedPersona.getTelefono());
            persona.setCorreoElectronico(updatedPersona.getCorreoElectronico());
            persona.setIdEstadoCivil(updatedPersona.getIdEstadoCivil());
            persona.setIdTipoDocumento(updatedPersona.getIdTipoDocumento());
            persona.setNumeroDocumento(updatedPersona.getNumeroDocumento());
            persona.setFechaCreacion(updatedPersona.getFechaCreacion());
            persona.setUsuarioCreacion(updatedPersona.getUsuarioCreacion());
            persona.setFechaModificacion(updatedPersona.getFechaModificacion());
            persona.setUsuarioModificacion(updatedPersona.getUsuarioModificacion());
            return personaService.save(persona);
        } else {
            return null;
        }
    }

    // Elimina una persona existente
    @DeleteMapping("api/delete_persona/{id}")
    public void deletePersona(@PathVariable("id") Integer id) {
        Optional<Persona> personaOptional = personaService.findById(id);
        personaOptional.ifPresent(personaService::delete);
    }


    /*Endpoint que muestra al usuario el nombre completo de la persona asociado al id */
    @GetMapping("/{id}")
    public ResponseEntity<String> obtenerNombre(@PathVariable Long id) {
        String nombre = personaService.obtenerNombreCompletoPorId(id);
        return ResponseEntity.ok(nombre);
    }

    /*Endpoint que obtiene los documentos asociados a una persona */
    @GetMapping("/{id}/documentos")
    public ResponseEntity<List<DocumentoPersonaDto>> obtenerDocumentosPersona(@PathVariable Long id) {
        List<DocumentoPersonaDto> documentos = personaService.obtenerDocumentosPorPersonaId(id);
        if (documentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documentos);
    }


}