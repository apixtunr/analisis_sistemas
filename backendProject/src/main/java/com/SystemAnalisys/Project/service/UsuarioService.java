package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.dto.UsuarioDTO;
import com.SystemAnalisys.Project.entity.Usuario;
import com.SystemAnalisys.Project.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.SystemAnalisys.Project.controller.LoginResult;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BitacoraAccesoService bitacoraAccesoService;
    private final PasswordService passwordService = new PasswordService();

    public UsuarioService(UsuarioRepository usuarioRepository, BitacoraAccesoService bitacoraAccesoService) {
        this.usuarioRepository = usuarioRepository;
        this.bitacoraAccesoService = bitacoraAccesoService;
    }

    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String par_id) {
        return usuarioRepository.findById(par_id);
    }

    public Usuario save(Usuario par_usuario) {
        return usuarioRepository.save(par_usuario);
    }

    public void delete(Usuario par_usuario) {
        usuarioRepository.delete(par_usuario);
    }

    public LoginResult login(String idUsuario, String password, HttpServletRequest request) {
        // 1. Buscar el usuario en la BD
        Optional<Usuario> userOptional = usuarioRepository.findById(idUsuario);

        if (!userOptional.isPresent()) {
            // 2. Registrar intento fallido: usuario no existe
            bitacoraAccesoService.registrarAcceso(
                    idUsuario, // Usuario ingresado
                    "Usuario ingresado no existe", // Tipo de acceso (de BD)
                    "LOGIN", // Acción
                    request, // Request para IP y User-Agent
                    null // Sesión (aún no se usa)
            );

            // Retornar resultado del login
            return new LoginResult(false, "Usuario no encontrado", null, "USER_NOT_FOUND");
        }

        // 3. Usuario encontrado
        Usuario usuario = userOptional.get();

        // 4. Verificar contraseña
        if (!passwordService.verifyPassword(password, usuario.getPassword())) {
            // Registrar intento fallido: password incorrecta
            bitacoraAccesoService.registrarAcceso(
                    usuario.getIdUsuario(),
                    "Bloqueado - Password incorrecto/Numero de intentos excedidos",
                    "LOGIN",
                    request,
                    null);

            return new LoginResult(false, "Contraseña incorrecta", null, "INVALID_PASSWORD");
        }

        // 5. Login exitoso
        bitacoraAccesoService.registrarAcceso(
                usuario.getIdUsuario(),
                "Acceso Concedido",
                "LOGIN",
                request,
                null);

        // 6. Retornar resultado exitoso
        return new LoginResult(true, "Login exitoso", usuario, "LOGIN_OK");
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public void actualizarRolUsuario(String idUsuario, Integer idRole) {
        usuarioRepository.actualizarRolUsuario(idUsuario, idRole);
    }

    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> lista = new java.util.ArrayList<>();
        for (Usuario u : usuarios) {
            lista.add(new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido()));
        }
        return lista;
    }

    /* Devuelve una lista de Usuarios que pertenecen a un rol específico */
    public List<UsuarioDTO> getUsuariosPorRol(Integer idRole) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> lista = new java.util.ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getIdRole() != null && u.getIdRole().equals(idRole)) {
                lista.add(new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido()));
            }
        }
        return lista;
    }
}
