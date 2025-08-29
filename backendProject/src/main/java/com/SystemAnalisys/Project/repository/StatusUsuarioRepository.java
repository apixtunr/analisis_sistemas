package com.SystemAnalisys.Project.repository;
import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.StatusUsuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface StatusUsuarioRepository extends JpaRepository<StatusUsuario, Integer> {
    Optional<StatusUsuario> findByIdstatususuario(Integer idstatususuario);
    @Query("SELECT MAX(s.idstatususuario) FROM StatusUsuario s")
    Integer findMaxId();
}
