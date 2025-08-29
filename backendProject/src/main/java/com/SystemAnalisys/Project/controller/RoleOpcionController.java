package com.SystemAnalisys.Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.SystemAnalisys.Project.dto.RoleOpcionDTO;
import com.SystemAnalisys.Project.entity.RoleOpcion;
import com.SystemAnalisys.Project.service.RoleOpcionService;

@RestController
@RequestMapping("/roleopcion")
public class RoleOpcionController {

    @Autowired
    private RoleOpcionService roleOpcionService;

    @PostMapping("/guardar")
    public RoleOpcion guardarRoleOpcion(@RequestBody RoleOpcionDTO dto) {
        return roleOpcionService.guardarRoleOpcion(dto);
    }

    @GetMapping("/permisos/{idRole}")
public List<RoleOpcion> getPermisosPorRol(@PathVariable Integer idRole) {
    return roleOpcionService.getPermisosPorRol(idRole);
}
}