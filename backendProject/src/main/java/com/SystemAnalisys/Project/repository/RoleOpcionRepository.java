package com.SystemAnalisys.Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SystemAnalisys.Project.entity.RoleOpcion;
import com.SystemAnalisys.Project.entity.RoleOpcionId;

public interface RoleOpcionRepository extends JpaRepository<RoleOpcion, RoleOpcionId> {
    
    List<RoleOpcion> findById_IdRole(Integer idRole);
}
