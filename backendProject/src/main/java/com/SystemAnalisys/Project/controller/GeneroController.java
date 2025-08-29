package com.SystemAnalisys.Project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import com.SystemAnalisys.Project.entity.Genero;
import com.SystemAnalisys.Project.service.GeneroService;

@RestController
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    // Obtiene la lista de todos los géneros
    @GetMapping("api/list_genero")
    public List<Genero> getAllGeneros() {
        return generoService.getAllGeneros();
    }

    // Crea un nuevo género
    @PostMapping("api/create_genero")
    public Genero createGenero(@RequestBody Genero genero) {
        return generoService.save(genero);
    }

    // Actualiza un género existente
    @PutMapping("api/update_genero/{idGenero}")
    public Genero updateGenero(@PathVariable("idGenero") Integer idGenero,
                              @RequestBody Genero updatedGenero) {
        Optional<Genero> generoOptional = generoService.findByIdgenero(idGenero);
        if (generoOptional.isPresent()) {
            Genero genero = generoOptional.get();
            genero.setNombre(updatedGenero.getNombre());
            genero.setFechaCreacion(updatedGenero.getFechaCreacion());
            genero.setUsuarioCreacion(updatedGenero.getUsuarioCreacion());
            genero.setFechaModificacion(new Date());
            genero.setUsuarioModificacion(updatedGenero.getUsuarioModificacion());

            return generoService.save(genero);
        } else {
            return null;
        }
    }

    // Elimina un género existente
    @DeleteMapping("api/delete_genero/{idGenero}")
    public void deleteGenero(@PathVariable("idGenero") Integer idGenero) {
        Optional<Genero> generoOptional = generoService.findByIdgenero(idGenero);
        generoOptional.ifPresent(generoService::delete);
    }
}
