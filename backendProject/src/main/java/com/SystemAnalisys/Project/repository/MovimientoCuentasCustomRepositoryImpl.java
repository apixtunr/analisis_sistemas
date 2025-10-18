package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

// Los imports de BigDecimal y LocalDateTime se necesitan en RegistroMovimientoRequest,
// pero no directamente en este archivo si solo se usan para el mapeo de parámetros.
// Los dejaré si el IDE los sugiere, pero pueden no ser estrictamente necesarios aquí.
// import java.math.BigDecimal;
// import java.time.LocalDateTime;

@Repository
public class MovimientoCuentasCustomRepositoryImpl implements MovimientoCuentasCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RegistroMovimientoResponse ejecutarRegistroMovimientos(RegistroMovimientoRequest request) {
        try {
            // Llama a la función usando SELECT.
            // Aunque devuelve VOID, SELECT es la forma idiomática en PostgreSQL para funciones.
            // No usamos getSingleResult() porque no hay un valor de retorno que capturar.
            String sql = "SELECT proyectoanalisis.Registro_Movimientos(" +
                         ":p_idPersona, :p_idSldCuenta, :p_id_TM, :p_Fecha, " +
                         ":p_Monto, :p_Descripcion, :p_user)";

            Query nativeQuery = entityManager.createNativeQuery(sql);

            // Establecer valores de los parámetros
            nativeQuery.setParameter("p_idPersona", request.getIdPersona());
            nativeQuery.setParameter("p_idSldCuenta", request.getIdSldCuenta());
            nativeQuery.setParameter("p_id_TM", request.getIdTM());
            nativeQuery.setParameter("p_Fecha", request.getFecha());
            nativeQuery.setParameter("p_Monto", request.getMonto());
            nativeQuery.setParameter("p_Descripcion", request.getDescripcion());
            nativeQuery.setParameter("p_user", request.getUser());

            // Ejecutar la query y desechar el resultado (que es VOID en este caso).
            // getResultList() en este contexto ejecutará la query y devolverá una lista vacía
            // si la función devuelve VOID y no se configura para devolver un set de registros.
            // Para funciones que devuelven VOID, esta es una forma común de ejecutarla con JPA Native Query.
            nativeQuery.getResultList();

            // Si la ejecución llega aquí sin excepción, asumimos éxito.
            return new RegistroMovimientoResponse("Movimiento registrado con éxito.", request.getIdSldCuenta(), true);

        } catch (Exception e) {
            e.printStackTrace();
            // Si hay alguna excepción durante la ejecución de la función o la base de datos,
            // se capturará aquí.
            return new RegistroMovimientoResponse("Error al registrar movimiento: " + e.getMessage(), request.getIdSldCuenta(), false);
        }
    }
}