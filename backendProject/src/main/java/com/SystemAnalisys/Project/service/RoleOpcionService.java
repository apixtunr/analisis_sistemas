package com.SystemAnalisys.Project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.dto.RoleOpcionDTO;
import com.SystemAnalisys.Project.entity.RoleOpcion;
import com.SystemAnalisys.Project.entity.RoleOpcionId;
import com.SystemAnalisys.Project.repository.RoleOpcionRepository;

@Service
public class RoleOpcionService {

    @Autowired
    private RoleOpcionRepository roleOpcionRepository;


    public List<RoleOpcion> getPermisosPorRol(Integer idRole) {
        return roleOpcionRepository.findById_IdRole(idRole);
    }

    public List<RoleOpcion> guardarRoleOpcion(List<RoleOpcionDTO> dtos) {
    if (dtos == null || dtos.isEmpty()) return java.util.Collections.emptyList();

    Integer idRole = dtos.get(0).getIdRole();
    // Elimina todos los permisos actuales del rol
    roleOpcionRepository.deleteAll(roleOpcionRepository.findById_IdRole(idRole));

    // Guarda los nuevos permisos
    List<RoleOpcion> guardados = new java.util.ArrayList<>();
    for (RoleOpcionDTO dto : dtos) {
        RoleOpcionId id = new RoleOpcionId(dto.getIdRole(), dto.getIdOpcion());
        RoleOpcion roleOpcion = new RoleOpcion(
            id,
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
        guardados.add(roleOpcionRepository.save(roleOpcion));
    }
    return guardados;
}

}
