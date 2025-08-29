package com.SystemAnalisys.Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SystemAnalisys.Project.entity.RoleOpcion;
import com.SystemAnalisys.Project.entity.RoleOpcionId;

public interface RoleOpcionRepository extends JpaRepository<RoleOpcion, RoleOpcionId> {
    
    @Query("SELECT r FROM RoleOpcion r WHERE r.idRole = :idRole")
    List<RoleOpcion> findByIdRole(@Param("idRole") Integer idRole);
}
