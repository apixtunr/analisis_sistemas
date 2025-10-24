package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.dto.DocumentoPersonaRequest;
import com.SystemAnalisys.Project.dto.DocumentoPersonaDto;
import com.SystemAnalisys.Project.entity.DocumentoPersona;
import com.SystemAnalisys.Project.entity.DocumentoPersonaId;
import com.SystemAnalisys.Project.entity.TipoDocumento;
import com.SystemAnalisys.Project.repository.DocumentoPersonaRepository;
import com.SystemAnalisys.Project.repository.TipoDocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentoPersonaService {

    private final DocumentoPersonaRepository repository;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    public DocumentoPersonaService(DocumentoPersonaRepository repository, TipoDocumentoRepository tipoDocumentoRepository) {
        this.repository = repository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public DocumentoPersona create(DocumentoPersonaRequest req) {
        DocumentoPersona d = new DocumentoPersona();
        d.setIdtipodocumento(req.getIdtipodocumento());
        d.setIdpersona(req.getIdpersona());
        d.setNodocumento(req.getNodocumento());
        d.setFechacreacion(req.getFechacreacion() == null ? new Date() : req.getFechacreacion());
        d.setUsuariocreacion(req.getUsuariocreacion());
        DocumentoPersonaId id = new DocumentoPersonaId();
        id.setIdtipodocumento(req.getIdtipodocumento());
        id.setIdpersona(req.getIdpersona());
        if (repository.existsById(id)) {
            return null; 
        }
        return repository.save(d);
    }

    public Optional<DocumentoPersona> getById(Integer idTipoDoc, Integer idPersona) {
        DocumentoPersonaId id = new DocumentoPersonaId();
        id.setIdtipodocumento(idTipoDoc);
        id.setIdpersona(idPersona);
        return repository.findById(id);
    }

    public List<DocumentoPersona> listByPersona(Integer idPersona) {
        return repository.findByIdpersona(idPersona);
    }

    public List<DocumentoPersonaDto> listByPersonaConTipo(Integer idPersona) {
        List<DocumentoPersona> documentos = repository.findByIdpersona(idPersona);
        
        return documentos.stream().map(doc -> {
            DocumentoPersonaDto dto = new DocumentoPersonaDto();
            dto.setIdtipodocumento(doc.getIdtipodocumento());
            dto.setIdpersona(doc.getIdpersona());
            dto.setNodocumento(doc.getNodocumento());
            dto.setFechacreacion(doc.getFechacreacion());
            dto.setUsuariocreacion(doc.getUsuariocreacion());
            dto.setFechamodificacion(doc.getFechamodificacion());
            dto.setUsuariomodificacion(doc.getUsuariomodificacion());
            
            // Obtener el nombre del tipo de documento
            TipoDocumento tipoDoc = tipoDocumentoRepository.findById(doc.getIdtipodocumento()).orElse(null);
            if (tipoDoc != null) {
                dto.setNombreTipoDocumento(tipoDoc.getNombre());
            } else {
                dto.setNombreTipoDocumento("Tipo desconocido");
            }
            
            return dto;
        }).collect(Collectors.toList());
    }

    public DocumentoPersona update(Integer idTipoDoc, Integer idPersona, DocumentoPersonaRequest req) {
        Optional<DocumentoPersona> existing = getById(idTipoDoc, idPersona);
        if (existing.isEmpty()) return null;
        DocumentoPersona d = existing.get();
        d.setNodocumento(req.getNodocumento());
        d.setFechamodificacion(new Date());
        d.setUsuariomodificacion(req.getUsuariocreacion());
        return repository.save(d);
    }

    public void delete(Integer idTipoDoc, Integer idPersona) {
        DocumentoPersonaId id = new DocumentoPersonaId();
        id.setIdtipodocumento(idTipoDoc);
        id.setIdpersona(idPersona);
        repository.deleteById(id);
    }
}
