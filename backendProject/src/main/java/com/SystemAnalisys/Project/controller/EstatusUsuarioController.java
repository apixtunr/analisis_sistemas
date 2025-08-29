package com.SystemAnalisys.Project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SystemAnalisys.Project.entity.StatusUsuario;
import com.SystemAnalisys.Project.service.StatusUsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.Date;



@RestController
public class EstatusUsuarioController {

    @Autowired
    private StatusUsuarioService StatusUsuarioService;

    // Obtiene la lista de todos los estatus de usuario
    @GetMapping("api/list_estatus_usuario")
    public List<StatusUsuario> getAllStatusUsuarios() {
        return StatusUsuarioService.getAllStatusUsuarios();
    }

    // Crea un nuevo estatus de usuario
    @PostMapping("api/create_estatus_usuario")
    public StatusUsuario createStatusUsuario(@RequestBody StatusUsuario statusUsuario) {
        StatusUsuario nuevo = new StatusUsuario();
        nuevo.setNombre(statusUsuario.getNombre());
        nuevo.setFechaCreacion(statusUsuario.getFechaCreacion());
        nuevo.setUsuarioCreacion(statusUsuario.getUsuarioCreacion());
        nuevo.setFechaModificacion(null);
        nuevo.setUsuarioModificacion(null);

        return StatusUsuarioService.save(nuevo);
    }

    // Actualiza un estatus de usuario existente
    @PutMapping("api/update_estatus_usuario/{idStatusUsuario}")
    public StatusUsuario updateStatusUsuario(@PathVariable("idStatusUsuario") Integer idStatusUsuario,
                                               @RequestBody StatusUsuario updatedStatusUsuario) {
        Optional<StatusUsuario> StatusUsuarioOptional = StatusUsuarioService.findByIdStatusUsuario(idStatusUsuario);
        if (StatusUsuarioOptional.isPresent()) {
            StatusUsuario StatusUsuario = StatusUsuarioOptional.get();
            StatusUsuario.setNombre(updatedStatusUsuario.getNombre());
            StatusUsuario.setFechaCreacion(updatedStatusUsuario.getFechaCreacion());
            StatusUsuario.setUsuarioCreacion(updatedStatusUsuario.getUsuarioCreacion());
            StatusUsuario.setFechaModificacion(new Date());
            StatusUsuario.setUsuarioModificacion(updatedStatusUsuario.getUsuarioModificacion());

            return StatusUsuarioService.save(StatusUsuario);
        } else {
            return null;
        }
    }

    // Elimina un estatus de usuario existente
    @DeleteMapping("api/delete_estatus_usuario/{idStatusUsuario}")
    public void deleteStatusUsuario(@PathVariable("idStatusUsuario") Integer idStatusUsuario) {
        Optional<StatusUsuario> StatusUsuarioOptional = StatusUsuarioService.findByIdStatusUsuario(idStatusUsuario);
        StatusUsuarioOptional.ifPresent(StatusUsuarioService::delete);
    }
}

