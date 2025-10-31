package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.PersonaSimpleDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository // Anotación para que Spring lo detecte como componente
public class PersonaCustomRepositoryImpl implements PersonaCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked") // Para suprimir el warning de tipo de List
    public List<PersonaSimpleDTO> findAllPersonasSimple() {
        String sql = "SELECT (concat(p.nombre, ' ', p.apellido)) AS nombreCompleto, p.idpersona AS idPersona " +
                     "FROM proyectoanalisis.persona p";

        Query nativeQuery = entityManager.createNativeQuery(sql);

        List<Object[]> results = nativeQuery.getResultList();
        return results.stream()
                .map(row -> new PersonaSimpleDTO(
                        (String) row[0], // Corresponde a nombreCompleto
                        ((Long) row[1]).intValue()  // Corresponde a idPersona
                ))
                .collect(Collectors.toList());
    }
}