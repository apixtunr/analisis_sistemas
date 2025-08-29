package com.SystemAnalisys.Project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SystemAnalisys.Project.entity.Role;
import com.SystemAnalisys.Project.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }
}