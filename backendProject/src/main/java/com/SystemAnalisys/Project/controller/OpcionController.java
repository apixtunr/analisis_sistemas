package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.entity.Opcion;
import com.SystemAnalisys.Project.service.OpcionService;   
import com.SystemAnalisys.Project.dto.ModuloDTO;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class OpcionController {

    @Autowired
    private OpcionService opcionService;

    // Obtiene la lista de todas las opciones
    @GetMapping("api/list_opcion")
    public List<Opcion> getAllOpciones() {
        return opcionService.getAllOpciones();
    }

    // Crear una nueva opción
    @PostMapping("api/create_opcion")
    public Opcion createOpcion(@RequestBody Opcion opcion) {
        return opcionService.save(opcion);
    }

    // Actualizar una opción existente
    @PutMapping("api/update_opcion/{id}")
    public Opcion updateOpcion(@PathVariable("id") Integer id, @RequestBody Opcion updatedOpcion) {
        Optional<Opcion> opcionOptional = opcionService.findById(id);
        if (opcionOptional.isPresent()) {
            Opcion opcion = opcionOptional.get();
            opcion.setIdMenu(updatedOpcion.getIdMenu());
            opcion.setNombre(updatedOpcion.getNombre());
            opcion.setDescripcion(updatedOpcion.getDescripcion());
            opcion.setUrl(updatedOpcion.getUrl());
            opcion.setFechaCreacion(updatedOpcion.getFechaCreacion());
            opcion.setUsuarioCreacion(updatedOpcion.getUsuarioCreacion());
            opcion.setFechaModificacion(updatedOpcion.getFechaModificacion());
            opcion.setUsuarioModificacion(updatedOpcion.getUsuarioModificacion());
            return opcionService.save(opcion);
        } else {
            return null;
        }
    }

    // Eliminar una opción existente
    @DeleteMapping("api/delete_opcion/{id}")
    public void deleteOpcion(@PathVariable("id") Integer id) {
        Optional<Opcion> opcionOptional = opcionService.findById(id);
        opcionOptional.ifPresent(opcionService::delete);
    }



    // Endpoint para obtener la estructura anidada de menú
    @GetMapping("api/estructura_menu")
    public List<ModuloDTO> getEstructuraMenuCompleta() {
        return opcionService.getEstructuraMenuCompleta();
    }



}
