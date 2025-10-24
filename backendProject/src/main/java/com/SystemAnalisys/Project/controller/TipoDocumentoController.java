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

import com.SystemAnalisys.Project.entity.TipoDocumento;
import com.SystemAnalisys.Project.service.TipoDocumentoService;

@RestController
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    // Obtiene la lista de todos los tipos de documento
    @GetMapping("api/list_tipo_documento")
    public List<TipoDocumento> getAllTiposDocumento() {
        return tipoDocumentoService.getAllTiposDocumento();
    }

    // Crea un nuevo tipo de documento
    @PostMapping("api/create_tipo_documento")
    public TipoDocumento createTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
        return tipoDocumentoService.save(tipoDocumento);
    }

    // Actualiza un tipo de documento existente
    @PutMapping("api/update_tipo_documento/{id}")
    public TipoDocumento updateTipoDocumento(@PathVariable("id") Integer id, @RequestBody TipoDocumento updatedTipoDocumento) {
        Optional<TipoDocumento> tipoDocumentoOptional = tipoDocumentoService.findById(id);
        if (tipoDocumentoOptional.isPresent()) {
            TipoDocumento tipoDocumento = tipoDocumentoOptional.get();
            tipoDocumento.setNombre(updatedTipoDocumento.getNombre());
            tipoDocumento.setFechaCreacion(updatedTipoDocumento.getFechaCreacion());
            tipoDocumento.setUsuarioCreacion(updatedTipoDocumento.getUsuarioCreacion());
            tipoDocumento.setFechaModificacion(updatedTipoDocumento.getFechaModificacion());
            tipoDocumento.setUsuarioModificacion(updatedTipoDocumento.getUsuarioModificacion());
            return tipoDocumentoService.save(tipoDocumento);
        } else {
            return null;
        }
    }

    // Elimina un tipo de documento existente
    @DeleteMapping("api/delete_tipo_documento/{id}")
    public void deleteTipoDocumento(@PathVariable("id") Integer id) {
        Optional<TipoDocumento> tipoDocumentoOptional = tipoDocumentoService.findById(id);
        tipoDocumentoOptional.ifPresent(tipoDocumentoService::delete);
    }
}