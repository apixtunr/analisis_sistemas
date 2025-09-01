package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.entity.Menu;
import com.SystemAnalisys.Project.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public Optional<Menu> findById(Integer id) {
        return menuRepository.findById(id);
    }

    public void delete(Menu menu) {
        menuRepository.delete(menu);
    }
}
