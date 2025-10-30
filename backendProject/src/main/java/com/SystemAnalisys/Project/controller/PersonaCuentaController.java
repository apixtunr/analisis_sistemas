package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.dto.PersonaDTO;
import com.SystemAnalisys.Project.dto.PersonaSimpleDTO;
import com.SystemAnalisys.Project.service.PersonaCuentaService; // Importa el servicio unificado
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/personas") // Mantener el mismo base path es buena práctica
public class PersonaCuentaController { // Nuevo nombre del controlador

    private final PersonaCuentaService personaCuentaService; // Inyecta el servicio unificado

    // Constructor que inyecta el servicio unificado
    public PersonaCuentaController(PersonaCuentaService personaCuentaService) {
        this.personaCuentaService = personaCuentaService;
    }

    /**
     * Endpoint REST para obtener todas las cuentas de saldo asociadas a una persona específica.
     * URL de ejemplo: GET /api/personas/1/cuentas
     *
     * @param idPersona El ID de la persona.
     * @return ResponseEntity con una lista de PersonaDTO.
     */
    @GetMapping("/{idPersona}/cuentas")
    public ResponseEntity<List<PersonaDTO>> getCuentasByPersonaId(@PathVariable Integer idPersona) {
        List<PersonaDTO> cuentas = personaCuentaService.getCuentasPorPersona(idPersona);
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    /**
     * Endpoint REST para obtener una lista simplificada de todas las personas (nombre completo e ID).
     * URL de ejemplo: GET /api/personas/simple
     *
     * @return ResponseEntity con una lista de PersonaSimpleDTO.
     */
    @GetMapping("/simple")
    public ResponseEntity<List<PersonaSimpleDTO>> getAllPersonasSimple() {
        List<PersonaSimpleDTO> personas = personaCuentaService.getAllPersonasSimple();
        if (personas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personas);
    }
}