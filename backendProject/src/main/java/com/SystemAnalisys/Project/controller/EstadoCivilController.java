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

import com.SystemAnalisys.Project.entity.EstadoCivil;
import com.SystemAnalisys.Project.service.EstadoCivilService;

@RestController
public class EstadoCivilController {

    @Autowired
    private EstadoCivilService estadoCivilService;

    // Obtiene la lista de todos los estados civiles
    @GetMapping("api/list_estado_civil")
    public List<EstadoCivil> getAllEstadosCiviles() {
        return estadoCivilService.getAllEstadosCiviles();
    }

    // Crea un nuevo estado civil
    @PostMapping("api/create_estado_civil")
    public EstadoCivil createEstadoCivil(@RequestBody EstadoCivil estadoCivil) {
        return estadoCivilService.save(estadoCivil);
    }

    // Actualiza un estado civil existente
    @PutMapping("api/update_estado_civil/{id}")
    public EstadoCivil updateEstadoCivil(@PathVariable("id") Integer id, @RequestBody EstadoCivil updatedEstadoCivil) {
        Optional<EstadoCivil> estadoCivilOptional = estadoCivilService.findById(id);
        if (estadoCivilOptional.isPresent()) {
            EstadoCivil estadoCivil = estadoCivilOptional.get();
            estadoCivil.setNombre(updatedEstadoCivil.getNombre());
            estadoCivil.setFechaCreacion(updatedEstadoCivil.getFechaCreacion());
            estadoCivil.setUsuarioCreacion(updatedEstadoCivil.getUsuarioCreacion());
            estadoCivil.setFechaModificacion(updatedEstadoCivil.getFechaModificacion());
            estadoCivil.setUsuarioModificacion(updatedEstadoCivil.getUsuarioModificacion());
            return estadoCivilService.save(estadoCivil);
        } else {
            return null;
        }
    }

    // Elimina un estado civil existente
    @DeleteMapping("api/delete_estado_civil/{id}")
    public void deleteEstadoCivil(@PathVariable("id") Integer id) {
        Optional<EstadoCivil> estadoCivilOptional = estadoCivilService.findById(id);
        estadoCivilOptional.ifPresent(estadoCivilService::delete);
    }
}