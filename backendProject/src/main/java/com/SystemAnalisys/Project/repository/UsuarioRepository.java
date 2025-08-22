package com.SystemAnalisys.Project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCorreoElectronicoAndPassword(String correoElectronico, String password);
}