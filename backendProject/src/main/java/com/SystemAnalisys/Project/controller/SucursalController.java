package com.SystemAnalisys.Project.controller;

import java.util.Date;
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

import com.SystemAnalisys.Project.entity.Sucursal;
import com.SystemAnalisys.Project.service.SucursalService;

@RestController
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    // Obtiene la lista de todas las sucursales
    @GetMapping("api/list_sucursal")
    public List<Sucursal> getAllSucursales() {
        return sucursalService.getAllSucursales();
    }

    // Crea una nueva sucursal
    @PostMapping("api/create_sucursal")
    public Sucursal createSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.save(sucursal);
    }

    // Actualiza una sucursal existente
    @PutMapping("api/update_sucursal/{idSucursal}")
    public Sucursal updateSucursal(@PathVariable("idSucursal") Integer idSucursal,
            @RequestBody Sucursal updatedSucursal) {
        Optional<Sucursal> sucursalOptional = sucursalService.findByIdSucursal(idSucursal);
        if (sucursalOptional.isPresent()) {
            Sucursal sucursal = sucursalOptional.get();
            sucursal.setNombre(updatedSucursal.getNombre());
            sucursal.setDireccion(updatedSucursal.getDireccion());
            sucursal.setIdEmpresa(updatedSucursal.getIdEmpresa());
            sucursal.setFechaModificacion(new Date());
            sucursal.setUsuarioModificacion(updatedSucursal.getUsuarioModificacion());

            return sucursalService.save(sucursal);
        } else {
            return null;
        }
    }

    // Elimina una sucursal existente
    @DeleteMapping("api/delete_sucursal/{idSucursal}")
    public void deleteSucursal(@PathVariable("idSucursal") Integer idSucursal) {
        Optional<Sucursal> sucursalOptional = sucursalService.findByIdSucursal(idSucursal);
        sucursalOptional.ifPresent(sucursalService::delete);
    }

}
