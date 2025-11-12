package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.EstadoDeCuentaDTO;
import com.SystemAnalisys.Project.dto.RegistroMovimientoRequest;
import com.SystemAnalisys.Project.dto.RegistroMovimientoResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // Aunque devuelve VOID, SELECT es la forma idiomática en PostgreSQL para
            // funciones.
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
            // getResultList() en este contexto ejecutará la query y devolverá una lista
            // vacía
            // si la función devuelve VOID y no se configura para devolver un set de
            // registros.
            // Para funciones que devuelven VOID, esta es una forma común de ejecutarla con
            // JPA Native Query.
            nativeQuery.getResultList();

            // Si la ejecución llega aquí sin excepción, asumimos éxito.
            return new RegistroMovimientoResponse("Movimiento registrado con éxito.", request.getIdSldCuenta(), true);

        } catch (Exception e) {
            e.printStackTrace();
            // Si hay alguna excepción durante la ejecución de la función o la base de
            // datos,
            // se capturará aquí.
            return new RegistroMovimientoResponse("Error al registrar movimiento: " + e.getMessage(),
                    request.getIdSldCuenta(), false);
        }
    }

    @Override
    public List<EstadoDeCuentaDTO> obtenerMovimientosPorCuentaYFechas(
            Integer idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        try {
            String sql = "SELECT " +
                    "m.idmovimientocuenta AS idMovimientoCuenta, " +
                    "m.idsaldocuenta AS idSaldoCuenta, " +
                    "m.idtipomovimientocxc AS idTipoMovimientoCXC, " +
                    "m.fechamovimiento AS fechaMovimiento, " +
                    "m.valormovimiento AS valorMovimiento, " +
                    "m.valormovimientopagado AS valorMovimientoPagado, " +
                    "m.generadoautomaticamente AS generadoAutomaticamente, " +
                    "m.descripcion AS descripcion, " +
                    "m.fechacreacion AS fechaCreacion, " +
                    "m.usuariocreacion AS usuarioCreacion, " +
                    "m.fechamodificacion AS fechaModificacion, " +
                    "m.usuariomodificacion AS usuarioModificacion " +
                    "FROM proyectoanalisis.movimiento_cuenta m " +
                    "WHERE m.idsaldocuenta = :idCuenta " +
                    "AND m.fechamovimiento >= :fechaInicio " +
                    "AND m.fechamovimiento <= :fechaFin " +
                    "ORDER BY m.fechamovimiento DESC";

            Query query = entityManager.createNativeQuery(sql, "EstadoDeCuentaMapping");
            query.setParameter("idCuenta", idCuenta);
            query.setParameter("fechaInicio", java.sql.Timestamp.valueOf(fechaInicio));
            query.setParameter("fechaFin", java.sql.Timestamp.valueOf(fechaFin));

            @SuppressWarnings("unchecked")
            List<EstadoDeCuentaDTO> result = query.getResultList();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Map<String, Object> obtenerEstadoCuentaConSaldos(
            Integer idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        try {
            // Query con JOIN para obtener operacionCuentaCorriente
            String sql = "SELECT " +
                    "m.idmovimientocuenta, " +
                    "m.idsaldocuenta, " +
                    "m.idtipomovimientocxc, " +
                    "t.operacioncuentacorriente, " + // 1=DÉBITO, 2=CRÉDITO
                    "m.fechamovimiento, " +
                    "m.valormovimiento, " +
                    "m.valormovimientopagado, " +
                    "m.generadoautomaticamente, " +
                    "m.descripcion, " +
                    "m.fechacreacion, " +
                    "m.usuariocreacion, " +
                    "m.fechamodificacion, " +
                    "m.usuariomodificacion " +
                    "FROM proyectoanalisis.movimiento_cuenta m " +
                    "INNER JOIN proyectoanalisis.tipo_movimiento_cxc t " +
                    "ON m.idtipomovimientocxc = t.idtipomovimientocxc " +
                    "WHERE m.idsaldocuenta = :idCuenta " +
                    "AND m.fechamovimiento >= :fechaInicio " +
                    "AND m.fechamovimiento <= :fechaFin " +
                    "ORDER BY m.fechamovimiento ASC";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("idCuenta", idCuenta);
            query.setParameter("fechaInicio", java.sql.Timestamp.valueOf(fechaInicio));
            query.setParameter("fechaFin", java.sql.Timestamp.valueOf(fechaFin));

            @SuppressWarnings("unchecked")
            List<Object[]> resultados = query.getResultList();

            // Calcular saldos dinámicamente
            BigDecimal saldo = BigDecimal.ZERO;
            BigDecimal totalAbonos = BigDecimal.ZERO;
            BigDecimal totalCargos = BigDecimal.ZERO;
            List<Map<String, Object>> movimientos = new ArrayList<>();

            for (Object[] row : resultados) {
                Integer operacion = row[3] != null ? ((Number) row[3]).intValue() : null;
                BigDecimal valor = row[5] != null ? (BigDecimal) row[5] : BigDecimal.ZERO;

                // Calcular según operación
                if (operacion != null) {
                    if (operacion == 1) {
                        // DÉBITO - resta
                        totalCargos = totalCargos.add(valor);
                        saldo = saldo.subtract(valor);
                    } else if (operacion == 2) {
                        // CRÉDITO - suma
                        totalAbonos = totalAbonos.add(valor);
                        saldo = saldo.add(valor);
                    }
                }

                // Construir mapa del movimiento
                Map<String, Object> mov = new HashMap<>();
                mov.put("idMovimientoCuenta", row[0]);
                mov.put("idSaldoCuenta", row[1]);
                mov.put("idTipoMovimientoCXC", row[2]);
                mov.put("operacionCuentaCorriente", operacion);
                mov.put("fechaMovimiento", row[4]);
                mov.put("valorMovimiento", valor);
                mov.put("valorMovimientoPagado", row[6]);
                mov.put("generadoAutomaticamente", row[7]);
                mov.put("descripcion", row[8]);
                mov.put("saldo", saldo); // Saldo acumulado
                mov.put("fechaCreacion", row[9]);
                mov.put("usuarioCreacion", row[10]);
                mov.put("fechaModificacion", row[11]);
                mov.put("usuarioModificacion", row[12]);

                movimientos.add(mov);
            }

            // Construir respuesta
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("movimientos", movimientos);
            resultado.put("totalAbonos", totalAbonos);
            resultado.put("totalCargos", totalCargos);
            resultado.put("saldoFinal", saldo.negate());

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", e.getMessage());
        }

    }
}