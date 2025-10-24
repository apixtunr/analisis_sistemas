package com.SystemAnalisys.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SystemAnalisys.Project.entity.StatusCuenta;

@Repository
public interface StatusCuentaRepository extends JpaRepository<StatusCuenta, Long> {


}
