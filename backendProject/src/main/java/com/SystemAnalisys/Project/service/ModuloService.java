package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.Modulo;
import com.SystemAnalisys.Project.repository.ModuloRepository;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;

    public ModuloService(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    // Obtener todos los módulos
    public List<Modulo> getAllModulos() {
        return (List<Modulo>) moduloRepository.findAll();
    }

    // Buscar módulo por ID
    public Optional<Modulo> findById(Integer idModulo) {
        return moduloRepository.findById(idModulo);
    }

    // Guardar o actualizar módulo
    public Modulo save(Modulo modulo) {
        return moduloRepository.save(modulo);
    }

    // Eliminar módulo
    public void delete(Modulo modulo) {
        moduloRepository.delete(modulo);
    }
}
