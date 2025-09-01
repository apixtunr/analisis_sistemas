package com.SystemAnalisys.Project.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SystemAnalisys.Project.entity.BitacoraAcceso;
import com.SystemAnalisys.Project.entity.TipoAcceso;

public interface BitacoraAccesoRepository extends JpaRepository<BitacoraAcceso, Integer> {

    // Cuenta accesos filtrando por usuario, tipo de acceso (entidad) y fecha
    @Query("SELECT COUNT(ba) FROM BitacoraAcceso ba WHERE ba.idUsuario = :idUsuario AND ba.tipoAcceso = :tipoAcceso AND ba.fechaAcceso >= :fecha")
    Integer countByUserAndTypeAndDateAfter(@Param("idUsuario") String idUsuario, 
                                           @Param("tipoAcceso") TipoAcceso tipoAcceso, 
                                           @Param("fecha") Date fecha);

    // Listado de accesos de un usuario, ordenados por fecha descendente
    @Query("SELECT ba FROM BitacoraAcceso ba WHERE ba.idUsuario = :idUsuario ORDER BY ba.fechaAcceso DESC")
    List<BitacoraAcceso> findByUsuarioOrderByFechaDesc(@Param("idUsuario") String idUsuario);

}
