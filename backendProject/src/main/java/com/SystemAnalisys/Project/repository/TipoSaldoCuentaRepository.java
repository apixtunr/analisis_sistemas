package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.entity.TipoSaldoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSaldoCuentaRepository extends JpaRepository<TipoSaldoCuenta, Integer> {

}
