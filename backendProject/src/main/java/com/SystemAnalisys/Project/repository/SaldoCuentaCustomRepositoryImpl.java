package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.PersonaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SaldoCuentaCustomRepositoryImpl implements SaldoCuentaCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked") // <-- Añade esta línea aquí
    public List<PersonaDTO> findCuentasByPersonaId(Integer idPersona) {
        String sql = "SELECT ts.nombre AS nombreTipoSaldo, sc.idsaldocuenta AS idSaldoCuenta " +
                     "FROM proyectoanalisis.saldo_cuenta sc " +
                     "JOIN proyectoanalisis.tipo_saldo_cuenta ts ON sc.idtiposaldocuenta = ts.idtiposaldocuenta " +
                     "WHERE sc.idpersona = :idPersona";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("idPersona", idPersona);

        List<Object[]> results = nativeQuery.getResultList(); // Aquí es donde se genera el warning
        return results.stream()
                .map(row -> new PersonaDTO(
                        (String) row[0],
                        (Integer) row[1]
                ))
                .collect(Collectors.toList());
    }
}