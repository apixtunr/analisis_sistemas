package com.SystemAnalisys.Project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByNit(String nit);
    @SuppressWarnings("null")
    Optional<Empresa> findById(Integer idEmpresa);
}
