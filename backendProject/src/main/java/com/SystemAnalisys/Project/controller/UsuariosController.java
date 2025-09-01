package com.SystemAnalisys.Project.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SystemAnalisys.Project.dto.UsuarioDTO;
import com.SystemAnalisys.Project.entity.Usuario;
import com.SystemAnalisys.Project.service.UsuarioService;

@RestController
public class UsuariosController {
    @Autowired
    private UsuarioService usuariosService;

    // Obtiene la lista de todos los usuarios
    @GetMapping("api/list_usuario")
    public List<Usuario> getAllUsuarios() {
        return usuariosService.getAllUsuarios();
    }

    // Crea un nuevo usuario
    @PostMapping("api/create_usuario")
    public Usuario createUsuarios(@RequestBody Usuario user) {
        Optional<Usuario> existingUser = usuariosService.findById(user.getIdUsuario());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        return usuariosService.save(user);
    }

    // Actualiza un usuario existente
    @PutMapping("api/update_usuario/{id}")
    public Usuario updateUsuarios(@PathVariable("id") String idUsuario, @RequestBody Usuario updatedUsuario) {
        Optional<Usuario> userOptional = usuariosService.findById(idUsuario);
        if (userOptional.isPresent()) {
            Usuario user = userOptional.get();
            user.setNombre(updatedUsuario.getNombre());
            user.setApellido(updatedUsuario.getApellido());
            user.setFechaNacimiento(updatedUsuario.getFechaNacimiento());
            user.setIdStatusUsuario(updatedUsuario.getIdStatusUsuario());
            user.setPassword(updatedUsuario.getPassword());
            user.setIdGenero(updatedUsuario.getIdGenero());
            user.setUltimaFechaIngreso(updatedUsuario.getUltimaFechaIngreso());
            user.setIntentosDeAcceso(updatedUsuario.getIntentosDeAcceso());
            user.setSesionActual(updatedUsuario.getSesionActual());
            user.setUltimaFechaCambioPassword(updatedUsuario.getUltimaFechaCambioPassword());
            user.setCorreoElectronico(updatedUsuario.getCorreoElectronico());
            user.setRequiereCambiarPassword(updatedUsuario.getRequiereCambiarPassword());
            user.setFotografia(updatedUsuario.getFotografia());
            user.setTelefonoMovil(updatedUsuario.getTelefonoMovil());
            user.setIdSucursal(updatedUsuario.getIdSucursal());
            user.setPregunta(updatedUsuario.getPregunta());
            user.setRespuesta(updatedUsuario.getRespuesta());
            user.setIdRole(updatedUsuario.getIdRole());
            user.setFechaCreacion(updatedUsuario.getFechaCreacion());
            user.setUsuarioCreacion(updatedUsuario.getUsuarioCreacion());
            user.setFechaModificacion(new Date());
            user.setUsuarioModificacion(null);
            return usuariosService.save(user);
        } else {                                                                                                                                        
            return null;
        }
    }

    // Elimina un usuario existente
    @DeleteMapping("api/delete_usuario/{id}")
    public void deleteUsuario(@PathVariable("id") String userId) {
        Optional<Usuario> userOptional = usuariosService.findById(userId);
        userOptional.ifPresent(usuariosService::delete);
    }

    // Inicia sesión de un usuario buscando por correo electrónico y contraseña
    @PostMapping("user/login")
    public Usuario login(@RequestBody Usuario usuarios) {
        
        return usuariosService.login(usuarios);
    }

    /* Busca un usuario por su ID y actualiza su rol */
    @PutMapping("/usuario/{id}/rol")
    public void actualizarRolUsuario(@PathVariable("id") String idUsuario, @RequestBody Integer idRole) {
        usuariosService.actualizarRolUsuario(idUsuario, idRole);
    }

    /* obtiene una lista de todos los usuarios existentes */
    @GetMapping("/api/listusuarios")
    public List<UsuarioDTO> getUsuarios() {
        return usuariosService.getUsuarios();
    }

    /* obtiene una lista de los usuarios asociados a un rol */
    @GetMapping("/api/usuarios/{idRole}")
    public List<UsuarioDTO> getUsuariosPorRol(@PathVariable Integer idRole) {
    return usuariosService.getUsuariosPorRol(idRole);
    }
}