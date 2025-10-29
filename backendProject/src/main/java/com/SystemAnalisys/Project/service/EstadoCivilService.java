package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.EstadoCivil;
import com.SystemAnalisys.Project.repository.EstadoCivilRepository;

@Service
public class EstadoCivilService {
    private final EstadoCivilRepository estadoCivilRepository;

    public EstadoCivilService(EstadoCivilRepository estadoCivilRepository) {
        this.estadoCivilRepository = estadoCivilRepository;
    }

    public List<EstadoCivil> getAllEstadosCiviles() {
        return estadoCivilRepository.findAll();
    }

    public Optional<EstadoCivil> findById(Integer idEstadoCivil) {
        return estadoCivilRepository.findById(idEstadoCivil);
    }

    public EstadoCivil save(EstadoCivil estadoCivil) {
        return estadoCivilRepository.save(estadoCivil);
    }

    public void delete(EstadoCivil estadoCivil) {
        estadoCivilRepository.delete(estadoCivil);
    }
}