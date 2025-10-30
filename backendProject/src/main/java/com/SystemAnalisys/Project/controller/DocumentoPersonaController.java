package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.dto.DocumentoPersonaRequest;
import com.SystemAnalisys.Project.dto.DocumentoPersonaDto;
import com.SystemAnalisys.Project.entity.DocumentoPersona;
import com.SystemAnalisys.Project.service.DocumentoPersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos-persona")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class DocumentoPersonaController {

    private final DocumentoPersonaService service;

    public DocumentoPersonaController(DocumentoPersonaService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody DocumentoPersonaRequest req) {
        var created = service.create(req);
        if (created == null) return ResponseEntity.status(409).body("Documento ya existe");
        return ResponseEntity.ok(created);
    }

    @GetMapping("/persona/{idPersona}")
    public ResponseEntity<List<DocumentoPersonaDto>> listByPersona(@PathVariable Integer idPersona) {
        var list = service.listByPersonaConTipo(idPersona);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{idTipo}/{idPersona}")
    public ResponseEntity<DocumentoPersona> getById(@PathVariable Integer idTipo, @PathVariable Integer idPersona) {
        return service.getById(idTipo, idPersona)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{idTipo}/{idPersona}")
    public ResponseEntity<?> update(@PathVariable Integer idTipo, @PathVariable Integer idPersona, @RequestBody DocumentoPersonaRequest req) {
        var updated = service.update(idTipo, idPersona, req);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{idTipo}/{idPersona}")
    public ResponseEntity<Void> delete(@PathVariable Integer idTipo, @PathVariable Integer idPersona) {
        service.delete(idTipo, idPersona);
        return ResponseEntity.noContent().build();
    }
}
