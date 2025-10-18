package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.PersonaDTO;
import java.util.List;

// Ya no extiende Repository<Object, Integer>
public interface SaldoCuentaCustomRepository {
    List<PersonaDTO> findCuentasByPersonaId(Integer idPersona);
}