package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.dto.SaldoCuentaRequest;
import com.SystemAnalisys.Project.entity.SaldoCuenta;
import com.SystemAnalisys.Project.service.SaldoCuentaCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saldo-cuentas")
public class SaldoCuentaController {

    private final SaldoCuentaCrudService service;
    public SaldoCuentaController(SaldoCuentaCrudService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SaldoCuenta> create(@RequestBody SaldoCuentaRequest req) {
        SaldoCuenta created = service.create(req);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaldoCuenta> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SaldoCuenta>> listAll() {
        List<SaldoCuenta> list = service.listAll();
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/persona/{idPersona}")
    public ResponseEntity<List<SaldoCuenta>> listByPersona(@PathVariable Integer idPersona) {
        List<SaldoCuenta> list = service.listByPersona(idPersona);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaldoCuenta> update(@PathVariable Integer id, @RequestBody SaldoCuentaRequest req) {
        SaldoCuenta updated = service.update(id, req);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/recalcular")
    public ResponseEntity<SaldoCuenta> recalculate(@PathVariable Integer id, @RequestParam(required = false) String usuario) {
        SaldoCuenta updated = service.recalculateSaldo(id, usuario);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/calcular")
    public ResponseEntity<?> calcularSinPersistir(@PathVariable Integer id) {
        java.math.BigDecimal saldo = service.calculateSaldoActual(id);
        if (saldo == null) return ResponseEntity.notFound().build();
        // Devolver solo el valor calculado en JSON: { "saldoActual": <valor> }
        return ResponseEntity.ok(java.util.Map.of("saldoActual", saldo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para consulta de saldos por nombre y apellido
    @GetMapping("/consultar-por-nombre")
    public ResponseEntity<List<SaldoCuenta>> consultarPorNombre(
            @RequestParam String nombre, 
            @RequestParam String apellido) {
        List<SaldoCuenta> cuentas = service.consultarPorNombreApellido(nombre, apellido);
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }
}
