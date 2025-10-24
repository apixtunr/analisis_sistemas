package com.SystemAnalisys.Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SystemAnalisys.Project.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    // Obtener todas las personas ordenadas por ID
    @Query("SELECT p FROM Persona p ORDER BY p.idPersona ASC")
    List<Persona> findAllOrderedById();

    @Query("SELECT CONCAT(p.nombre, ' ', p.apellido) FROM Persona p WHERE p.idPersona = :idPersona")
    String obtenerNombreCompletoPorId(@Param("idPersona") Long idPersona);

    // Método para buscar personas por nombre y apellido (búsqueda parcial, case-insensitive)
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND LOWER(p.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    java.util.List<Persona> findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(@Param("nombre") String nombre, @Param("apellido") String apellido);

}