package com.SystemAnalisys.Project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SystemAnalisys.Project.service.CierreMesService;

@RestController
@RequestMapping("/api/cierre-mes")
public class CierreMesController {
    
    @Autowired
    private CierreMesService cierreMesService;
    
    @PostMapping
    public ResponseEntity<String> ejecutarCierre(@RequestParam String usuario) {
        cierreMesService.procesarCierreMes(usuario);
        return ResponseEntity.ok("Cierre de mes ejecutado correctamente");
    }
}
