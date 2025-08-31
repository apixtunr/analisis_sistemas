package com.SystemAnalisys.Project.dto;

import java.util.List;

public class ModuloDTO {
    private Integer idModulo;
    private String nombre;
    private List<MenuDTO> menus;

    public ModuloDTO(Integer idModulo, String nombre, List<MenuDTO> menus) {
        this.idModulo = idModulo;
        this.nombre = nombre;
        this.menus = menus;
    }

    public Integer getIdModulo() { return idModulo; }
    public String getNombre() { return nombre; }
    public List<MenuDTO> getMenus() { return menus; }
}
