package com.SystemAnalisys.Project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Obtiene la lista de todos los usuarios
    @GetMapping("api/list_usuario")
    public List<Usuario> getAllUsuarios() {
        return usuariosService.getAllUsuarios();
    }

    // Crea un nuevo usuario con contraseña hasheada
    @PostMapping("api/create_usuario")
    public Usuario createUsuarios(@RequestBody Usuario user) {
        Optional<Usuario> existingUser = usuariosService.findById(user.getIdUsuario());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El ID de usuario ya existe");
        }

        // Hashear la contraseña antes de guardar
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);

        return usuariosService.save(user);
    }

    // Actualiza un usuario existente (manteniendo hash de contraseña)
    @PutMapping("api/update_usuario/{id}")
    public Usuario updateUsuarios(@PathVariable("id") String idUsuario, @RequestBody Usuario updatedUsuario) {
        Optional<Usuario> userOptional = usuariosService.findById(idUsuario);
        if (userOptional.isPresent()) {
            Usuario user = userOptional.get();
            user.setNombre(updatedUsuario.getNombre());
            user.setApellido(updatedUsuario.getApellido());
            user.setFechaNacimiento(updatedUsuario.getFechaNacimiento());
            user.setIdStatusUsuario(updatedUsuario.getIdStatusUsuario());

            // Rehashear si viene una nueva contraseña
            if (updatedUsuario.getPassword() != null && !updatedUsuario.getPassword().isEmpty()) {
                String hash = passwordEncoder.encode(updatedUsuario.getPassword());
                user.setPassword(hash);
            }

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

    // Login con seguridad (hash + ResponseEntity seguro)
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Usuario loginData,
            HttpServletRequest request) {

        LoginResult result = usuariosService.login(
                loginData.getCorreoElectronico(),
                loginData.getPassword(),
                request
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", result.isSuccess());
        response.put("message", result.getMessage());

        if (result.isSuccess() && result.getUsuario() != null) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", result.getUsuario().getIdUsuario());
            userData.put("nombre", result.getUsuario().getNombre());
            userData.put("apellido", result.getUsuario().getApellido());
            userData.put("correo", result.getUsuario().getCorreoElectronico());
            userData.put("rol", result.getUsuario().getIdRole());
            response.put("usuario", userData);
        }

        return ResponseEntity.ok(response);
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
