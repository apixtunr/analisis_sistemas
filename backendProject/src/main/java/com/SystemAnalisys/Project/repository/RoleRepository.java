package com.SystemAnalisys.Project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SystemAnalisys.Project.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @SuppressWarnings("null")
    Optional<Role> findById(Integer idRole);
}