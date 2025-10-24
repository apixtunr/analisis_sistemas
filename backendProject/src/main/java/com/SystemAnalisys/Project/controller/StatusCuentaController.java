package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.entity.StatusCuenta;
import com.SystemAnalisys.Project.repository.StatusCuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-cuenta")
public class StatusCuentaController {

    @Autowired
    private StatusCuentaRepository statusCuentaRepository;

    @GetMapping
    public ResponseEntity<List<StatusCuenta>> listarTodos() {
        List<StatusCuenta> statusList = statusCuentaRepository.findAll();
        return ResponseEntity.ok(statusList);
    }
}