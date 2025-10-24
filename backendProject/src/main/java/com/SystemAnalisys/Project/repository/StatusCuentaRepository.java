package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.entity.StatusCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusCuentaRepository extends JpaRepository<StatusCuenta, Long> {

}