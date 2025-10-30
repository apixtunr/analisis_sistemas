package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.dto.UsuarioDTO;
import com.SystemAnalisys.Project.entity.Empresa;
import com.SystemAnalisys.Project.entity.Sucursal;
import com.SystemAnalisys.Project.entity.Usuario;
import com.SystemAnalisys.Project.repository.UsuarioRepository;
import com.SystemAnalisys.Project.repository.EmpresaRepository;
import com.SystemAnalisys.Project.repository.SucursalRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.SystemAnalisys.Project.controller.LoginResult;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final SucursalRepository sucursalRepository;
    private final BitacoraAccesoService bitacoraAccesoService;
    private final PasswordService passwordService = new PasswordService();

    public UsuarioService(UsuarioRepository usuarioRepository, BitacoraAccesoService bitacoraAccesoService, EmpresaRepository empresaRepository, SucursalRepository sucursalRepository) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.sucursalRepository = sucursalRepository;
        this.bitacoraAccesoService = bitacoraAccesoService;
    }

    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String par_id) {
        return usuarioRepository.findById(par_id);
    }

    public Optional<Usuario> findByIdUsuario(String par_id) {
        return usuarioRepository.findByIdUsuario(par_id);
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
        // Registrar intento fallido: usuario no existe
        bitacoraAccesoService.registrarAcceso(
                idUsuario,
                "Usuario ingresado no existe",
                "LOGIN",
                request,
                null
        );
        return new LoginResult(false, "Usuario no encontrado", null, "USER_NOT_FOUND");
    }

    Usuario usuario = userOptional.get();

    // Verificar si el usuario está inactivo
    if (usuario.getIdStatusUsuario() != null && usuario.getIdStatusUsuario() == 3) {
        return new LoginResult(false, "Usuario inactivo, contacte al administrador", null, "USER_INACTIVE");
    }

    // 2. Verificar contraseña
    if (!passwordService.verifyPassword(password, usuario.getPassword())) {
        // Incrementar intentos de acceso
        int intentos = usuario.getIntentosDeAcceso() != null ? usuario.getIntentosDeAcceso() : 0;
        usuario.setIntentosDeAcceso(intentos + 1);

        // Obtener empresa desde la sucursal
        Sucursal sucursal = sucursalRepository.findById(usuario.getIdSucursal())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        Empresa empresa = empresaRepository.findById(sucursal.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Si supera el máximo de intentos, bloquear usuario
        if (usuario.getIntentosDeAcceso() >= empresa.getPasswordIntentosAntesDeBloquear()) {
            usuario.setIdStatusUsuario(3); // 3 = bloqueado
        }

        // Guardar cambios en la BD
        usuarioRepository.save(usuario);

        // Registrar intento fallido
        bitacoraAccesoService.registrarAcceso(
                usuario.getIdUsuario(),
                "Contraseña incorrecta",
                "LOGIN",
                request,
                null
        );

        String message = usuario.getIdStatusUsuario() == 3
                ? "Usuario bloqueado por demasiados intentos fallidos"
                : "Contraseña incorrecta";

        return new LoginResult(false, message, null, "INVALID_PASSWORD");
    }

    // 3. Login exitoso → resetear intentos
    usuario.setIntentosDeAcceso(0);
    usuarioRepository.save(usuario);

    // Registrar acceso exitoso
    bitacoraAccesoService.registrarAcceso(
            usuario.getIdUsuario(),
            "Acceso Concedido",
            "LOGIN",
            request,
            null
    );

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
            lista.add(new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getIdSucursal()));
        }
        return lista;
    }

    /* Devuelve una lista de Usuarios que pertenecen a un rol específico */
    public List<UsuarioDTO> getUsuariosPorRol(Integer idRole) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> lista = new java.util.ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getIdRole() != null && u.getIdRole().equals(idRole)) {
                lista.add(new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getIdSucursal()));
            }
        }
        return lista;
    }
}