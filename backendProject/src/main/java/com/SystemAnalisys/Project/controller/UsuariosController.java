package com.SystemAnalisys.Project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
import com.SystemAnalisys.Project.entity.Sucursal;
import com.SystemAnalisys.Project.entity.Empresa;
import com.SystemAnalisys.Project.repository.SucursalRepository;
import com.SystemAnalisys.Project.repository.EmpresaRepository;

@RestController
public class UsuariosController {
    @Autowired
    private UsuarioService usuariosService;
    private SucursalRepository sucursalRepository;
    private EmpresaRepository empresaRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Obtiene la lista de todos los usuarios
    @GetMapping("api/list_usuario")
    public List<Usuario> getAllUsuarios() {
        return usuariosService.getAllUsuarios();
    }

    // Crea un nuevo usuario con contraseña hasheada
    @PostMapping("api/create_usuario")
    public ResponseEntity<?> createUsuarios(@RequestBody Usuario user) {
        // 1️⃣ Verificar si el usuario ya existe
        Optional<Usuario> existingUser = usuariosService.findById(user.getIdUsuario());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        // Traer la sucursal del usuario
        Sucursal sucursal = sucursalRepository.findById(user.getIdSucursal())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        // Traer la empresa asociada a la sucursal
        Empresa empresa = empresaRepository.findById(sucursal.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Validar la contraseña según los requisitos de la empresa
        if (!validarContrasena(user.getPassword(), empresa)) {
            return ResponseEntity.badRequest().body(
                    "La contraseña no cumple con los requisitos de la empresa: "
                            + "mínimo " + empresa.getPasswordCantidadMayusculas() + " mayúsculas, "
                            + empresa.getPasswordCantidadMinusculas() + " minúsculas, "
                            + empresa.getPasswordCantidadNumeros() + " números, "
                            + empresa.getPasswordCantidadCaracteresEspeciales() + " caracteres especiales, "
                            + "longitud mínima " + empresa.getPasswordLargo());
        }

        // 5️⃣ Hashear la contraseña antes de guardar
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);

        // 6️⃣ Guardar el usuario y devolverlo
        Usuario nuevoUsuario = usuariosService.save(user);
        return ResponseEntity.ok(nuevoUsuario);
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
    public void deleteUsuario(@PathVariable("id") String idUsuario) {
        Optional<Usuario> userOptional = usuariosService.findById(idUsuario);
        userOptional.ifPresent(usuariosService::delete);
    }

    // Login con seguridad (hash + ResponseEntity seguro)
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Usuario loginData,
            HttpServletRequest request) {

        LoginResult result = usuariosService.login(
                loginData.getIdUsuario(),
                loginData.getPassword(),
                request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", result.isSuccess());
        response.put("message", result.getMessage());

        if (result.isSuccess() && result.getUsuario() != null) {
            // Guardar en sesión
            request.getSession(true).setAttribute("usuario", result.getUsuario());

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", result.getUsuario().getIdUsuario());
            userData.put("nombre", result.getUsuario().getNombre());
            userData.put("apellido", result.getUsuario().getApellido());
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

    // Método privado dentro del controller
    private boolean validarContrasena(String contrasena, Empresa empresa) {
        long mayusculas = contrasena.chars().filter(Character::isUpperCase).count();
        long minusculas = contrasena.chars().filter(Character::isLowerCase).count();
        long numeros = contrasena.chars().filter(Character::isDigit).count();
        long especiales = contrasena.chars().filter(c -> !Character.isLetterOrDigit(c)).count();

        return mayusculas >= empresa.getPasswordCantidadMayusculas()
                && minusculas >= empresa.getPasswordCantidadMinusculas()
                && numeros >= empresa.getPasswordCantidadNumeros()
                && especiales >= empresa.getPasswordCantidadCaracteresEspeciales()
                && contrasena.length() >= empresa.getPasswordLargo();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // elimina sesión
        }
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

}
