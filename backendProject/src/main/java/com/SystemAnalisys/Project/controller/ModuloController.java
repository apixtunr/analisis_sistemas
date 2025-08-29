package com.SystemAnalisys.Project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SystemAnalisys.Project.entity.Modulo;
import com.SystemAnalisys.Project.service.ModuloService;

@RestController
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    // Obtiene todos los módulos
    @GetMapping("api/list_modulo")
    public List<Modulo> getAllModulos() {
        return moduloService.getAllModulos();
    }

    // Crea un nuevo módulo
    @PostMapping("api/create_modulo")
    public Modulo createModulo(@RequestBody Modulo modulo) {
        return moduloService.save(modulo);
    }

    // Actualiza un módulo existente
    @PutMapping("api/update_modulo/{idmodulo}")
    public Modulo updateModulo(@PathVariable("idmodulo") Integer idmodulo, @RequestBody Modulo updatedModulo) {
        Optional<Modulo> moduloOptional = moduloService.findById(idmodulo);
        if (moduloOptional.isPresent()) {
            Modulo modulo = moduloOptional.get();
            modulo.setNombre(updatedModulo.getNombre());
            modulo.setOrdenmenu(updatedModulo.getOrdenmenu());
            modulo.setFechacreacion(updatedModulo.getFechacreacion());
            modulo.setUsuariocreacion(updatedModulo.getUsuariocreacion());
            modulo.setFechamodificacion(updatedModulo.getFechamodificacion());
            modulo.setUsuariomodificacion(updatedModulo.getUsuariomodificacion());
            return moduloService.save(modulo);
        } else {
            return null;
        }
    }

    // Elimina un módulo existente
    @DeleteMapping("api/delete_modulo/{idmodulo}")
    public void deleteModulo(@PathVariable("idmodulo") Integer idmodulo) {
        Optional<Modulo> moduloOptional = moduloService.findById(idmodulo);
        moduloOptional.ifPresent(moduloService::delete);
    }
}
