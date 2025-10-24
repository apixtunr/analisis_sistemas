package com.SystemAnalisys.Project.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SystemAnalisys.Project.entity.StatusCuenta;
import com.SystemAnalisys.Project.service.StatusCuentaService;
import java.util.List;


@RestController
@RequestMapping("/api/statuscuenta")
public class StatusCuentaController {

    private final StatusCuentaService service;

    public StatusCuentaController(StatusCuentaService service) {
        this.service = service;
    }

    @GetMapping
    public List<StatusCuenta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public StatusCuenta obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
    }

    @PostMapping("/crear")
    public StatusCuenta crear(@RequestBody StatusCuenta statusCuenta) {
        return service.crear(statusCuenta);
    }

    @PutMapping("/{id}")
    public StatusCuenta actualizar(@PathVariable Long id, @RequestBody StatusCuenta statusCuenta) {
        return service.actualizar(id, statusCuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

}

