package com.SystemAnalisys.Project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.dto.RoleOpcionDTO;
import com.SystemAnalisys.Project.entity.RoleOpcion;
import com.SystemAnalisys.Project.repository.RoleOpcionRepository;

@Service
public class RoleOpcionService {

    @Autowired
    private RoleOpcionRepository roleOpcionRepository;

    public RoleOpcion guardarRoleOpcion(RoleOpcionDTO dto) {
        RoleOpcion roleOpcion = new RoleOpcion(
            dto.getIdRole(),
            dto.getIdOpcion(),
            dto.getAlta(),
            dto.getBaja(),
            dto.getCambio(),
            dto.getImprimir(),
            dto.getExportar(),
            dto.getFechaCreacion(),
            dto.getUsuarioCreacion(),
            dto.getFechaModificacion(),
            dto.getUsuarioModificacion()
        );
        return roleOpcionRepository.save(roleOpcion);
    }

    public List<RoleOpcion> getPermisosPorRol(Integer idRole) {
    return roleOpcionRepository.findByIdRole(idRole);
}
}