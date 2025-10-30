package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.TipoMovimientoCxcResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TipoMovimientoCxcCustomRepositoryImpl implements TipoMovimientoCxcCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<TipoMovimientoCxcResponse> findAllTipoMovimientoCxc() {
        String sql = "SELECT t.nombre, t.idtipomovimientocxc " +
                     "FROM proyectoanalisis.tipo_movimiento_cxc t";

        Query nativeQuery = entityManager.createNativeQuery(sql);

        List<Object[]> results = nativeQuery.getResultList();
        return results.stream()
                .map(row -> new TipoMovimientoCxcResponse(
                        (String) row[0],
                        ((Long) row[1]).intValue()
                ))
                .collect(Collectors.toList());
    }
}