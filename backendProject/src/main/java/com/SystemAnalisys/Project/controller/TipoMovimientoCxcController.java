package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.dto.TipoMovimientoCxcResponse;
import com.SystemAnalisys.Project.entity.TipoMovimientosCxc;
import com.SystemAnalisys.Project.service.TipoMovimientoCxcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-movimiento-cxc") // Ejemplo de endpoint
public class TipoMovimientoCxcController {

    private final TipoMovimientoCxcService tipoMovimientoCxcService;

    public TipoMovimientoCxcController(TipoMovimientoCxcService tipoMovimientoCxcService) {
        this.tipoMovimientoCxcService = tipoMovimientoCxcService;
    }

    @GetMapping
    public ResponseEntity<List<TipoMovimientoCxcResponse>> getAllTipoMovimientoCxc() {
        List<TipoMovimientoCxcResponse> movimientos = tipoMovimientoCxcService.getAllTipoMovimientoCxc();
        return ResponseEntity.ok(movimientos);
    }

    @Autowired
    private TipoMovimientoCxcService service;

    @GetMapping("/listar")
    public List<TipoMovimientosCxc> listar() {
        return service.listar();
    }
    
    @GetMapping("/{id}")
    public TipoMovimientosCxc obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Tipo de movimiento no encontrado"));
    }

    @PostMapping("/crear")
    public TipoMovimientosCxc crear(@RequestBody TipoMovimientosCxc tipoMovimiento) {
        return service.crear(tipoMovimiento);
    }

    @PutMapping("/actualizar/{id}")
    public TipoMovimientosCxc actualizar(@PathVariable Long id, @RequestBody TipoMovimientosCxc tipoMovimiento) {
        return service.actualizar(id, tipoMovimiento);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

}