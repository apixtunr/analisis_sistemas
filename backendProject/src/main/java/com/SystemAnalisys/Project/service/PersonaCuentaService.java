package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.dto.PersonaDTO; // Para el primer query
import com.SystemAnalisys.Project.dto.PersonaSimpleDTO; // Para el segundo query
import com.SystemAnalisys.Project.repository.PersonaCustomRepository; // Para listar personas simples
import com.SystemAnalisys.Project.repository.SaldoCuentaCustomRepository; // Para listar cuentas por persona
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Anotación para que Spring lo detecte como un servicio
public class PersonaCuentaService { // Nuevo nombre del servicio

    private final SaldoCuentaCustomRepository saldoCuentaCustomRepository;
    private final PersonaCustomRepository personaCustomRepository;

    // Constructor que inyecta ambos repositorios
    public PersonaCuentaService(SaldoCuentaCustomRepository saldoCuentaCustomRepository,
                                PersonaCustomRepository personaCustomRepository) {
        this.saldoCuentaCustomRepository = saldoCuentaCustomRepository;
        this.personaCustomRepository = personaCustomRepository;
    }

    /**
     * Obtiene la lista de cuentas de saldo para una persona dada (primer query).
     *
     * @param idPersona El ID de la persona.
     * @return Una lista de DTOs PersonaDTO (antes CuentaPorPersonaResponse).
     */
    public List<PersonaDTO> getCuentasPorPersona(Integer idPersona) {
        return saldoCuentaCustomRepository.findCuentasByPersonaId(idPersona);
    }

    /**
     * Obtiene una lista simplificada de todas las personas (nombre completo e ID) (segundo query).
     *
     * @return Una lista de DTOs PersonaSimpleDTO.
     */
    public List<PersonaSimpleDTO> getAllPersonasSimple() {
        return personaCustomRepository.findAllPersonasSimple();
    }
}