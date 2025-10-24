package com.SystemAnalisys.Project.service; // Nuevo paquete

import com.SystemAnalisys.Project.dto.PersonaDTO; // Importa el nuevo DTO
import com.SystemAnalisys.Project.repository.SaldoCuentaCustomRepository; // Importa el nuevo Repositorio
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaldoCuentaService {

    private final SaldoCuentaCustomRepository saldoCuentaCustomRepository;

    public SaldoCuentaService(SaldoCuentaCustomRepository saldoCuentaCustomRepository) {
        this.saldoCuentaCustomRepository = saldoCuentaCustomRepository;
    }

    public List<PersonaDTO> getCuentasPorPersona(Integer idPersona) {
        return saldoCuentaCustomRepository.findCuentasByPersonaId(idPersona);
    }
}