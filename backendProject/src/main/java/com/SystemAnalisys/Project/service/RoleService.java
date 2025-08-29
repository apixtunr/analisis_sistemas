package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.Role;
import com.SystemAnalisys.Project.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Obtener todos los roles
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    // Buscar rol por ID
    public Optional<Role> findById(Integer idRole) {
        return roleRepository.findById(idRole);
    }

    // Guardar o actualizar rol
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    // Eliminar rol
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
