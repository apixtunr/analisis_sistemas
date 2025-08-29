package com.SystemAnalisys.Project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    @SuppressWarnings("null")
    Optional<Modulo> findById(Integer idModulo);
}
