package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.TipoDocumento;
import com.SystemAnalisys.Project.repository.TipoDocumentoRepository;

@Service
public class TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    // Obtener todos los tipos de documento
    public List<TipoDocumento> getAllTiposDocumento() {
        return tipoDocumentoRepository.findAll();
    }

    // Buscar tipo de documento por ID
    public Optional<TipoDocumento> findById(Integer idTipoDocumento) {
        return tipoDocumentoRepository.findById(idTipoDocumento);
    }

    // Guardar o actualizar tipo de documento
    public TipoDocumento save(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    // Eliminar tipo de documento
    public void delete(TipoDocumento tipoDocumento) {
        tipoDocumentoRepository.delete(tipoDocumento);
    }
}