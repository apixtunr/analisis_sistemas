package com.SystemAnalisys.Project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class CierreMesRepository {
    
    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public void ejecutarCierreMes(String usuario) {
        entityManager.createNativeQuery("SELECT proyectoanalisis.cierre_mes(:usuario)")
            .setParameter("usuario", usuario)
            .getSingleResult();
    }
}