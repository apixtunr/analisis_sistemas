package com.SystemAnalisys.Project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.TipoCuenta;

@Repository
public interface TipoCuentaRepository extends JpaRepository<TipoCuenta, Integer> {
    @SuppressWarnings("null")
    Optional<TipoCuenta> findById(Integer idTipoCuenta);
}
