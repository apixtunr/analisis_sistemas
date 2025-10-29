package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.entity.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Integer> {

    @Query(value = "SELECT \n" +
        "  mo.idmodulo, \n" +
        "  mo.nombre AS modulo_nombre, \n" +
        "  me.idmenu, \n" +
        "  me.nombre AS menu_nombre, \n" +
        "  op.idopcion, \n" +
        "  op.nombre AS opcion_nombre, \n" +
        "  op.url, \n" +
        "  op.descripcion \n" +
        "FROM proyectoanalisis.modulo mo \n" +
        "JOIN proyectoanalisis.menu me ON mo.idmodulo = me.idmodulo \n" +
        "LEFT JOIN proyectoanalisis.opcion op ON me.idmenu = op.idmenu \n" +
        "ORDER BY mo.ordenmenu, me.ordenmenu, op.ordenmenu", nativeQuery = true)
    List<Object[]> obtenerEstructuraMenuCompleta();
}


