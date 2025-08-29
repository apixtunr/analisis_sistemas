package com.SystemAnalisys.Project.service;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.entity.Genero;
import com.SystemAnalisys.Project.repository.GeneroRepository;

@Service
public class GeneroService {
    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public List<Genero> getAllGeneros() {
        return (List<Genero>) generoRepository.findAll();
    }

    public Optional<Genero> findByIdgenero(Integer idgenero) {
        return generoRepository.findByIdgenero(idgenero);
    }

    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    public void delete(Genero genero) {
        generoRepository.delete(genero);
    }
}
