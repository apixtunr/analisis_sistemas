package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.entity.Opcion;
import com.SystemAnalisys.Project.repository.OpcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.SystemAnalisys.Project.dto.ModuloDTO;
import com.SystemAnalisys.Project.dto.MenuDTO;
import com.SystemAnalisys.Project.dto.OpcionDTO;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import java.util.Optional;

@Service
public class OpcionService {

    @Autowired
    private OpcionRepository opcionRepository;

    public List<Opcion> getAllOpciones() {
        return opcionRepository.findAll();
    }

    public Opcion save(Opcion opcion) {
        return opcionRepository.save(opcion);
    }

    public Optional<Opcion> findById(Integer id) {
        return opcionRepository.findById(id);
    }

    public void delete(Opcion opcion) {
        opcionRepository.delete(opcion);
    }

    // Método para obtener la estructura anidada de módulos, menús y opciones
    public List<ModuloDTO> getEstructuraMenuCompleta() {
        List<Object[]> rows = opcionRepository.obtenerEstructuraMenuCompleta();
        Map<Integer, ModuloDTO> moduloMap = new LinkedHashMap<>();
        Map<Integer, MenuDTO> menuMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            Integer idModulo = (Integer) row[0];
            String nombreModulo = (String) row[1];
            Integer idMenu = (Integer) row[2];
            String nombreMenu = (String) row[3];
            Integer idOpcion = (Integer) row[4];
            String nombreOpcion = (String) row[5];
            String url = (String) row[6];
            String descripcion = (String) row[7];

            // Modulo
            ModuloDTO modulo = moduloMap.get(idModulo);
            if (modulo == null) {
                modulo = new ModuloDTO(idModulo, nombreModulo, new ArrayList<>());
                moduloMap.put(idModulo, modulo);
            }

            // Menu
            MenuDTO menu = menuMap.get(idMenu);
            if (menu == null) {
                menu = new MenuDTO(idMenu, nombreMenu, new ArrayList<>());
                menuMap.put(idMenu, menu);
                modulo.getMenus().add(menu);
            }

            // Opcion
            OpcionDTO opcion = new OpcionDTO(idOpcion, nombreOpcion, url, descripcion);
            menu.getOpciones().add(opcion);
        }
        return new ArrayList<>(moduloMap.values());
    }
}