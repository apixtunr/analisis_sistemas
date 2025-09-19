package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.entity.Opcion;
import com.SystemAnalisys.Project.service.OpcionService;   
import com.SystemAnalisys.Project.dto.ModuloDTO;
import com.SystemAnalisys.Project.dto.OpcionRequestDTO;
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

    // Crear una nueva opción a partir de DTO
    @PostMapping("api/create_opcion_dto")
    public Opcion createOpcionFromDTO(@RequestBody OpcionRequestDTO opcionDTO) {
        Opcion opcion = new Opcion();
        opcion.setIdMenu(opcionDTO.getIdMenu());
        opcion.setNombre(opcionDTO.getNombre());
        opcion.setDescripcion(opcionDTO.getDescripcion());
        opcion.setUrl(opcionDTO.getUrl());
        opcion.setFechaCreacion(opcionDTO.getFechaCreacion());
        opcion.setUsuarioCreacion(opcionDTO.getUsuarioCreacion());
        opcion.setFechaModificacion(opcionDTO.getFechaModificacion());
        opcion.setUsuarioModificacion(opcionDTO.getUsuarioModificacion());
        opcion.setOrdenmenu(opcionDTO.getOrdenmenu());
        opcion.setPagina("No aplica"); // Valor por defecto
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
            opcion.setPagina(updatedOpcion.getPagina());
            return opcionService.save(opcion);
        } else {
            return null;
        }
    }

    // Actualizar una opción existente a partir de DTO
    @PutMapping("api/update_opcion_dto/{id}")
    public Opcion updateOpcionFromDTO(@PathVariable("id") Integer id, @RequestBody OpcionRequestDTO opcionDTO) {
        Optional<Opcion> opcionOptional = opcionService.findById(id);
        if (opcionOptional.isPresent()) {
            Opcion opcion = opcionOptional.get();
            opcion.setIdMenu(opcionDTO.getIdMenu());
            opcion.setNombre(opcionDTO.getNombre());
            opcion.setDescripcion(opcionDTO.getDescripcion());
            opcion.setUrl(opcionDTO.getUrl());
            opcion.setFechaCreacion(opcionDTO.getFechaCreacion());
            opcion.setUsuarioCreacion(opcionDTO.getUsuarioCreacion());
            opcion.setFechaModificacion(opcionDTO.getFechaModificacion());
            opcion.setUsuarioModificacion(opcionDTO.getUsuarioModificacion());
            opcion.setOrdenmenu(opcionDTO.getOrdenmenu());
            opcion.setPagina("No aplica"); // Valor por defecto
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
