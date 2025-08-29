package com.SystemAnalisys.Project.repository;
import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.Genero;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    Optional<Genero> findByIdgenero(Integer idGenero);
}
