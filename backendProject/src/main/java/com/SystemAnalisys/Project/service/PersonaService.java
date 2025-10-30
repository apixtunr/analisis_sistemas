package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.dto.DocumentoPersonaDto;

import com.SystemAnalisys.Project.entity.DocumentoPersona;
import com.SystemAnalisys.Project.entity.Persona;
import com.SystemAnalisys.Project.entity.TipoDocumento;
import com.SystemAnalisys.Project.repository.DocumentoPersonaRepository;


import com.SystemAnalisys.Project.repository.PersonaRepository;
import com.SystemAnalisys.Project.repository.TipoDocumentoRepository;



@Service
public class PersonaService {


    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private DocumentoPersonaRepository documentoPersonaRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    // Obtener todas las personas
    public List<Persona> getAllPersonas() {
        return personaRepository.findAllOrderedById();
    }

    // Buscar persona por ID
    public Optional<Persona> findById(Integer idPersona) {
        return personaRepository.findById(idPersona);
    }

    // Guardar o actualizar persona
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    // Eliminar persona
    public void delete(Persona persona) {
        personaRepository.delete(persona);
    }

     public String obtenerNombreCompletoPorId(Long idPersona) {
        String nombre = personaRepository.obtenerNombreCompletoPorId(idPersona);
        if (nombre == null) {
            return "Persona no encontrada";
        }
        return nombre;
    }

    public List<DocumentoPersonaDto> obtenerDocumentosPorPersonaId(Long idPersona) {
        List<DocumentoPersona> documentos = documentoPersonaRepository.findByIdpersona(idPersona.intValue());
        
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


}