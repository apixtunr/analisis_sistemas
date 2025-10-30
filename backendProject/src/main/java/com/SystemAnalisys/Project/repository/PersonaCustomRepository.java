package com.SystemAnalisys.Project.repository;

import com.SystemAnalisys.Project.dto.PersonaSimpleDTO;
import java.util.List;

public interface PersonaCustomRepository {
    List<PersonaSimpleDTO> findAllPersonasSimple();
}