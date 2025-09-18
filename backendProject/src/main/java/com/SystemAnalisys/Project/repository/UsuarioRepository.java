package com.SystemAnalisys.Project.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SystemAnalisys.Project.dto.UsuarioDTO;
import com.SystemAnalisys.Project.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
   
    @SuppressWarnings("null")
    Optional<Usuario> findById(String idUsuario);
    Optional<Usuario> findByIdUsuario(String idUsuario);


    @Modifying
    @Query("UPDATE Usuario u SET u.idRole = :idRole WHERE u.idUsuario = :idUsuario")
    void actualizarRolUsuario(@Param("idUsuario") String idUsuario, @Param("idRole") Integer idRole);

    @Query("SELECT new com.SystemAnalisys.Project.dto.UsuarioDTO(u.idUsuario, u.nombre, u.apellido, u.idSucursal) FROM Usuario u")
    List<UsuarioDTO> getUsuarios();

    //Busca un usuario activo por su correo electrónico.    
    Optional<Usuario> findActiveUserByCorreoElectronico(String correoElectronico);

}