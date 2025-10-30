package com.SystemAnalisys.Project.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SystemAnalisys.Project.entity.TipoCuenta;
import com.SystemAnalisys.Project.service.TipoCuentaService;

@RestController
@RequestMapping("/api")
public class TipoCuentaController {

    @Autowired
    private TipoCuentaService tipoCuentaService;

    // Obtener todos los tipos de cuenta
    @GetMapping("/list_tipo_cuenta")
    public List<TipoCuenta> getAllTipoCuenta() {
        return tipoCuentaService.getAllTipoCuenta();
    }

    // Crear nuevo tipo de cuenta
    @PostMapping("/create_tipo_cuenta")
    public TipoCuenta createTipoCuenta(@RequestBody TipoCuenta tipoCuenta) {
        return tipoCuentaService.save(tipoCuenta);
    }

    // Actualizar tipo de cuenta existente
    @PutMapping("/update_tipo_cuenta/{idTipoCuenta}")
    public TipoCuenta updateTipoCuenta(@PathVariable("idTipoCuenta") Integer idTipoCuenta,
                                       @RequestBody TipoCuenta updatedTipoCuenta) {
        Optional<TipoCuenta> optionalTipoCuenta = tipoCuentaService.findById(idTipoCuenta);
        if (optionalTipoCuenta.isPresent()) {
            TipoCuenta tipoCuenta = optionalTipoCuenta.get();
            tipoCuenta.setNombre(updatedTipoCuenta.getNombre());
            tipoCuenta.setFechaCreacion(updatedTipoCuenta.getFechaCreacion());
            tipoCuenta.setUsuarioCreacion(updatedTipoCuenta.getUsuarioCreacion());
            tipoCuenta.setFechaModificacion(updatedTipoCuenta.getFechaModificacion());
            tipoCuenta.setUsuarioModificacion(updatedTipoCuenta.getUsuarioModificacion());
            return tipoCuentaService.save(tipoCuenta);
        } else {
            return null;
        }
    }

    // Eliminar tipo de cuenta
    @DeleteMapping("/delete_tipo_cuenta/{idTipoCuenta}")
    public void deleteTipoCuenta(@PathVariable("idTipoCuenta") Integer idTipoCuenta) {
        Optional<TipoCuenta> optionalTipoCuenta = tipoCuentaService.findById(idTipoCuenta);
        optionalTipoCuenta.ifPresent(tipoCuentaService::delete);
    }
}