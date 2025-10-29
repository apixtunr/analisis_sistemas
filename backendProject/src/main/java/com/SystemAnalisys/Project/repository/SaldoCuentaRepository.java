package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.entity.SaldoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaldoCuentaRepository extends JpaRepository<SaldoCuenta, Integer> {
    List<SaldoCuenta> findByIdpersona(Integer idpersona);
}
