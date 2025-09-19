package com.SystemAnalisys.Project.controller;

import java.time.Duration;
import java.time.Instant;
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
import com.SystemAnalisys.Project.entity.Sucursal;
import com.SystemAnalisys.Project.entity.Empresa;
import com.SystemAnalisys.Project.repository.SucursalRepository;
import com.SystemAnalisys.Project.repository.EmpresaRepository;

@RestController
public class UsuariosController {
    @Autowired
    private UsuarioService usuariosService;
    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
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
        try {
            // 1️⃣ Verificar si el usuario ya existe
            Optional<Usuario> existingUser = usuariosService.findById(user.getIdUsuario());
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("El usuario ya existe");
            }

            // 2️⃣ Traer la sucursal del usuario
            Sucursal sucursal = sucursalRepository.findById(user.getIdSucursal())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            // 3️⃣ Traer la empresa asociada a la sucursal
            Empresa empresa = empresaRepository.findById(sucursal.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            // 4️⃣ Validar la contraseña según los requisitos de la empresa
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

            // 6️⃣ Guardar el usuario
            Usuario nuevoUsuario = usuariosService.save(user);

            // 7️⃣ Ocultar la contraseña antes de devolver la respuesta
            nuevoUsuario.setPassword(null);

            return ResponseEntity.ok(nuevoUsuario);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al crear usuario: " + e.getMessage());
        }
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
            user.setIntentosDeAcceso(0); // Reiniciar intentos de acceso al actualizar
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
            Usuario user = result.getUsuario();

            boolean expirada = false;
            try {
                Sucursal sucursal = sucursalRepository.findById(user.getIdSucursal()).orElse(null);
                if (sucursal != null) {
                    Empresa empresa = empresaRepository.findById(sucursal.getIdEmpresa()).orElse(null);
                    if (empresa != null) {
                        Integer diasCaducidad = empresa.getPasswordCantidadCaducidadDias();
                        if (diasCaducidad != null && diasCaducidad > 0) {
                            Date lastChange = user.getUltimaFechaCambioPassword();
                            if (lastChange != null) {
                                long days = Duration.between(lastChange.toInstant(), Instant.now()).toDays();
                                expirada = days >= diasCaducidad;
                            } else {
                                expirada = false;
                            }
                            user.setRequiereCambiarPassword(expirada ? 1 : 0);
                            user.setUltimaFechaIngreso(new Date());
                            user.setSesionActual("Activa");
                            usuariosService.save(user);
                        }
                    }
                }
            } catch (Exception ignored) {
            }

            if (expirada) {
                response.put("success", false);
                response.put("message", "La contraseña ha expirado. Debe cambiarla antes de iniciar sesión.");
                return ResponseEntity.ok(response);
            }

            // Solo si no está expirada guardamos en sesión
            request.getSession(true).setAttribute("usuario", user);

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getIdUsuario());
            userData.put("nombre", user.getNombre());
            userData.put("apellido", user.getApellido());
            userData.put("rol", user.getIdRole());
            userData.put("requiereCambiarPassword", user.getRequiereCambiarPassword());
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

    // Método para validar contraseña
    private boolean validarContrasena(String contrasena, Empresa empresa) {
        int mayusculas = 0, minusculas = 0, numeros = 0, especiales = 0;
        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c))
                mayusculas++;
            else if (Character.isLowerCase(c))
                minusculas++;
            else if (Character.isDigit(c))
                numeros++;
            else
                especiales++;
        }
        return mayusculas >= empresa.getPasswordCantidadMayusculas()
                && minusculas >= empresa.getPasswordCantidadMinusculas()
                && numeros >= empresa.getPasswordCantidadNumeros()
                && especiales >= empresa.getPasswordCantidadCaracteresEspeciales()
                && contrasena.length() >= empresa.getPasswordLargo();
    }

    // Método para obtener la pregunta de seguridad de un usuario
    @GetMapping("api/preguntaseguridad/{idUsuario}")
    public ResponseEntity<?> getPregunta(@PathVariable String idUsuario) {
        Optional<Usuario> user = usuariosService.findByIdUsuario(idUsuario);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        // Acá devolvemos solamente la pregunta, no la respuesta
        return ResponseEntity.ok(Map.of("preguntaSeguridad", user.get().getPregunta()));
    }

    // Método para cambiar la contraseña después de validar la respuesta
    @PostMapping("api/cambiarcontrasena")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> payload) {
        String idUsuario = payload.get("idUsuario");
        String respuesta = payload.get("respuestaSeguridad");
        String nuevaPassword = payload.get("nuevaPassword");

        Optional<Usuario> userOpt = usuariosService.findByIdUsuario(idUsuario);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        Usuario user = userOpt.get();

        // Validar respuesta
        if (!user.getRespuesta().equalsIgnoreCase(respuesta)) {
            return ResponseEntity.badRequest().body("Respuesta incorrecta");
        }

        // Validar contraseña según empresa
        Sucursal sucursal = sucursalRepository.findById(user.getIdSucursal())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        Empresa empresa = empresaRepository.findById(sucursal.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        if (!validarContrasena(nuevaPassword, empresa)) {
            return ResponseEntity.badRequest().body("La nueva contraseña no cumple con los requisitos.");
        }

        // Guardar contraseña hasheada
        user.setPassword(passwordEncoder.encode(nuevaPassword));
        user.setUltimaFechaCambioPassword(new Date());
        usuariosService.save(user);

        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente"));

    }

}
