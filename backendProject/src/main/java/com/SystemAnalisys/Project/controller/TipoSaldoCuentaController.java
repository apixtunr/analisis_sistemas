package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.entity.TipoSaldoCuenta;
import com.SystemAnalisys.Project.repository.TipoSaldoCuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-saldo-cuenta")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class TipoSaldoCuentaController {

    @Autowired
    private TipoSaldoCuentaRepository tipoSaldoCuentaRepository;

    @GetMapping
    public ResponseEntity<List<TipoSaldoCuenta>> listarTodos() {
        List<TipoSaldoCuenta> tipos = tipoSaldoCuentaRepository.findAll();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSaldoCuenta> obtenerPorId(@PathVariable Integer id) {
        return tipoSaldoCuentaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoSaldoCuenta> crear(@RequestBody TipoSaldoCuenta tipoSaldoCuenta) {
        // Establecer fecha de creación si no está presente
        if (tipoSaldoCuenta.getFechacreacion() == null) {
            tipoSaldoCuenta.setFechacreacion(java.time.LocalDateTime.now());
        }
        if (tipoSaldoCuenta.getUsuariocreacion() == null) {
            tipoSaldoCuenta.setUsuariocreacion("system");
        }
        
        TipoSaldoCuenta saved = tipoSaldoCuentaRepository.save(tipoSaldoCuenta);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSaldoCuenta> actualizar(@PathVariable Integer id, @RequestBody TipoSaldoCuenta tipoSaldoCuenta) {
        return tipoSaldoCuentaRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(tipoSaldoCuenta.getNombre());
                    existing.setFechamodificacion(java.time.LocalDateTime.now());
                    existing.setUsuariomodificacion(tipoSaldoCuenta.getUsuariomodificacion() != null ? 
                        tipoSaldoCuenta.getUsuariomodificacion() : "system");
                    return ResponseEntity.ok(tipoSaldoCuentaRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return tipoSaldoCuentaRepository.findById(id)
                .map(tipo -> {
                    tipoSaldoCuentaRepository.delete(tipo);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}