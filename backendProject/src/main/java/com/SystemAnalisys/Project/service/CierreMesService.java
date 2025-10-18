package com.SystemAnalisys.Project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.repository.CierreMesRepository;

@Service
public class CierreMesService {
    
    @Autowired
    private CierreMesRepository cierreMesRepository;
    
    public void procesarCierreMes(String usuario) {
        cierreMesRepository.ejecutarCierreMes(usuario);
    }
}
