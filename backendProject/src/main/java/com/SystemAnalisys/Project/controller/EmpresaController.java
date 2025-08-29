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

import com.SystemAnalisys.Project.entity.Empresa;
import com.SystemAnalisys.Project.service.EmpresaService;

@RestController
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    // Obtiene la lista de todas las empresas
    @GetMapping("api/list_empresa")
    public List<Empresa> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }

    // Crea una nueva empresa
    @PostMapping("api/create_empresa")
    public Empresa createEmpresas(@RequestBody Empresa empresa) {
        return empresaService.save(empresa);
    }

    // Actualiza una empresa existente
    @PutMapping("api/update_empresa/{nit}")
    public Empresa updateEmpresas(@PathVariable("nit") String nit, @RequestBody Empresa updatedEmpresa) {
        Optional<Empresa> empresaOptional = empresaService.findByNit(nit);
        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            empresa.setNombre(updatedEmpresa.getNombre());
            empresa.setDireccion(updatedEmpresa.getDireccion());
            empresa.setNit(updatedEmpresa.getNit());
            empresa.setPasswordCantidadMayusculas(updatedEmpresa.getPasswordCantidadMayusculas());
            empresa.setPasswordCantidadMinusculas(updatedEmpresa.getPasswordCantidadMinusculas());
            empresa.setPasswordCantidadCaracteresEspeciales(updatedEmpresa.getPasswordCantidadCaracteresEspeciales());
            empresa.setPasswordCantidadCaducidadDias(updatedEmpresa.getPasswordCantidadCaducidadDias());
            empresa.setPasswordLargo(updatedEmpresa.getPasswordLargo());
            empresa.setPasswordIntentosAntesDeBloquear(updatedEmpresa.getPasswordIntentosAntesDeBloquear());
            empresa.setPasswordCantidadNumeros(updatedEmpresa.getPasswordCantidadNumeros());
            empresa.setPasswordCantidadPreguntasValidar(updatedEmpresa.getPasswordCantidadPreguntasValidar());
            empresa.setFechaCreacion(updatedEmpresa.getFechaCreacion());
            empresa.setUsuarioCreacion(updatedEmpresa.getUsuarioCreacion());
            empresa.setFechaModificacion(updatedEmpresa.getFechaModificacion());
            empresa.setUsuarioModificacion(updatedEmpresa.getUsuarioModificacion());
            return empresaService.save(empresa);
        } else {
            return null;
        }
    }

    // Elimina una empresa existente
    @DeleteMapping("api/delete_empresa/{nit}")
    public void deleteEmpresa(@PathVariable("nit") String nit) {
        Optional<Empresa> empresaOptional = empresaService.findByNit(nit);
        empresaOptional.ifPresent(empresaService::delete);
    }
}
