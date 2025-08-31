package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.entity.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Integer> {
    // Puedes agregar métodos personalizados aquí si necesitas filtrar por usuario, rol, etc.
   

    // Query nativo para traer la estructura completa de modulo, menu y opcion
    @Query(value = "SELECT mo.idmodulo, mo.nombre as modulo_nombre, me.idmenu, me.nombre as menu_nombre, op.idopcion, op.nombre as opcion_nombre, op.url, op.descripcion FROM proyectoanalisis.modulo mo JOIN proyectoanalisis.menu me ON mo.idmodulo = me.idmodulo JOIN proyectoanalisis.opcion op ON me.idmenu = op.idmenu ORDER BY mo.ordenmenu, me.ordenmenu, op.ordenmenu", nativeQuery = true)
    List<Object[]> obtenerEstructuraMenuCompleta();
}


