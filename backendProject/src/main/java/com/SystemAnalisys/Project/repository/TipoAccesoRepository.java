package com.SystemAnalisys.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SystemAnalisys.Project.entity.TipoAcceso;

public interface TipoAccesoRepository extends JpaRepository<TipoAcceso, Integer> {
    Optional<TipoAcceso> findByNombre(String nombre);

}
