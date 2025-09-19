package com.SystemAnalisys.Project.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario", schema = "proyectoanalisis")
public class Usuario {

    public Usuario() {
    }

    public Usuario(String idUsuario, String nombre, String apellido, Date fechaNacimiento, Integer idStatusUsuario,
            String password, Integer idGenero, Date ultimaFechaIngreso, Integer intentosDeAcceso,
            String sesionActual, Date ultimaFechaCambioPassword, String correoElectronico,
            Integer requiereCambiarPassword, byte[] fotografia, String telefonoMovil, Integer idSucursal,
            String pregunta, String respuesta, Integer idRole, Date fechaCreacion, String usuarioCreacion,
            Date fechaModificacion, String usuarioModificacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.idStatusUsuario = idStatusUsuario;
        this.password = password;
        this.idGenero = idGenero;
        this.ultimaFechaIngreso = ultimaFechaIngreso;
        this.intentosDeAcceso = intentosDeAcceso;
        this.sesionActual = sesionActual;
        this.ultimaFechaCambioPassword = ultimaFechaCambioPassword;
        this.correoElectronico = correoElectronico;
        this.requiereCambiarPassword = requiereCambiarPassword;
        this.fotografia = fotografia;
        this.telefonoMovil = telefonoMovil;
        this.idSucursal = idSucursal;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.idRole = idRole;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    @Id
    @Column(name = "idusuario", length = 100)
    private String idUsuario;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "apellido", length = 100)
    private String apellido;

    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;

    @Column(name = "idstatususuario")
    private Integer idStatusUsuario;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "idgenero")
    private Integer idGenero;

    @Column(name = "ultimafechaingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaIngreso;

    @Column(name = "intentosdeacceso")
    private Integer intentosDeAcceso;

    @Column(name = "sesionactual", length = 100)
    private String sesionActual;

    @Column(name = "ultimafechacambiopassword")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaCambioPassword;

    @Column(name = "correoelectronico", length = 100)
    private String correoElectronico;

    @Column(name = "requierecambiarpassword")
    private Integer requiereCambiarPassword;

    @Column(name = "fotografia")
    private byte[] fotografia;

    @Column(name = "telefonomovil", length = 30)
    private String telefonoMovil;

    @Column(name = "idsucursal")
    private Integer idSucursal;

    @Column(name = "pregunta", length = 200)
    private String pregunta;

    @Column(name = "respuesta", length = 200)
    private String respuesta;

    @Column(name = "idrole")
    private Integer idRole;

    @ManyToOne(fetch = FetchType.EAGER) // o LAZY si querés cargar solo cuando lo uses
    @JoinColumn(name = "idrole", insertable = false, updatable = false)
    private Role role;

    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "usuariocreacion", length = 100)
    private String usuarioCreacion;

    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "usuariomodificacion", length = 100)
    private String usuarioModificacion;
}
