package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.entity.DocumentoPersona;
import com.SystemAnalisys.Project.entity.DocumentoPersonaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoPersonaRepository extends JpaRepository<DocumentoPersona, DocumentoPersonaId> {
    List<DocumentoPersona> findByIdpersona(Integer idpersona);
}

