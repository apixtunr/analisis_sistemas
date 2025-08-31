package com.SystemAnalisys.Project.dto;

import java.util.List;

public class MenuDTO {
    private Integer idMenu;
    private String nombre;
    private List<OpcionDTO> opciones;

    public MenuDTO(Integer idMenu, String nombre, List<OpcionDTO> opciones) {
        this.idMenu = idMenu;
        this.nombre = nombre;
        this.opciones = opciones;
    }

    public Integer getIdMenu() { return idMenu; }
    public String getNombre() { return nombre; }
    public List<OpcionDTO> getOpciones() { return opciones; }
}
