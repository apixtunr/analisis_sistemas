package com.SystemAnalisys.Project.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RoleOpcionId implements Serializable {

    @Column(name = "idrole")
    private Integer idRole;

    @Column(name = "idopcion")
    private Integer idOpcion;

    public RoleOpcionId() {}

    public RoleOpcionId(Integer idRole, Integer idOpcion) {
        this.idRole = idRole;
        this.idOpcion = idOpcion;
    }
}
