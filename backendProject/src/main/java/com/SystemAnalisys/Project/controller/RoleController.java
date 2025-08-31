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

import com.SystemAnalisys.Project.entity.Role;
import com.SystemAnalisys.Project.service.RoleService;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Obtiene todos los roles
    @GetMapping("api/list_role")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Crea un nuevo rol
    @PostMapping("api/create_role")
    public Role createRole(@RequestBody Role role) {
    role.setIdRole(null);
    return roleService.save(role);
    }

   // Actualiza un rol existente
@PutMapping("api/update_role/{idrole}")
public Role updateRole(@PathVariable("idrole") Integer idrole, @RequestBody Role updatedRole) {
    Optional<Role> roleOptional = roleService.findById(idrole);
    if (roleOptional.isPresent()) {
        Role role = roleOptional.get();
        role.setNombre(updatedRole.getNombre());
        role.setFechacreacion(updatedRole.getFechacreacion());
        role.setUsuariocreacion(updatedRole.getUsuariocreacion());
        role.setFechamodificacion(updatedRole.getFechamodificacion());
        role.setUsuariomodificacion(updatedRole.getUsuariomodificacion());
        return roleService.save(role);
    } else {
        return null;
    }
}

// Elimina un rol existente
@DeleteMapping("api/delete_role/{idrole}")
public void deleteRole(@PathVariable("idrole") Integer idrole) {
    Optional<Role> roleOptional = roleService.findById(idrole);
    roleOptional.ifPresent(roleService::delete);
}
}

