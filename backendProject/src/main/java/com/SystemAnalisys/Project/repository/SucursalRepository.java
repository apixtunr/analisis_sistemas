package com.SystemAnalisys.Project.repository;

import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.Sucursal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    Optional<Sucursal> findByIdSucursal(Integer idSucursal);
}
