package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.entity.Menu;
import com.SystemAnalisys.Project.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Optional;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("api/list_menu")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    // Crear un nuevo menú
    @PostMapping("api/create_menu")
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.save(menu);
    }

    // Actualizar un menú existente
    @PutMapping("api/update_menu/{id}")
    public Menu updateMenu(@PathVariable("id") Integer id, @RequestBody Menu updatedMenu) {
        Optional<Menu> menuOptional = menuService.findById(id);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            menu.setIdmodulo(updatedMenu.getIdmodulo());
            menu.setNombre(updatedMenu.getNombre());
            menu.setOrdenmenu(updatedMenu.getOrdenmenu());
            menu.setFechacreacion(updatedMenu.getFechacreacion());
            menu.setUsuariocreacion(updatedMenu.getUsuariocreacion());
            menu.setFechamodificacion(updatedMenu.getFechamodificacion());
            menu.setUsuariomodificacion(updatedMenu.getUsuariomodificacion());
            return menuService.save(menu);
        } else {
            return null;
        }
    }

    // Eliminar un menú existente
    @DeleteMapping("api/delete_menu/{id}")
    public void deleteMenu(@PathVariable("id") Integer id) {
        Optional<Menu> menuOptional = menuService.findById(id);
        menuOptional.ifPresent(menuService::delete);
    }
}
