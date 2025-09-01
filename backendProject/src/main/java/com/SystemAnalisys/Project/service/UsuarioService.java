package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.dto.UsuarioDTO;
import com.SystemAnalisys.Project.entity.Usuario;
import com.SystemAnalisys.Project.repository.UsuarioRepository;
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

    public LoginResult login(String correo, String password, jakarta.servlet.http.HttpServletRequest request) {
    Optional<Usuario> userOptional = usuarioRepository.findActiveUserByCorreoElectronico(correo);

    if (!userOptional.isPresent()) {
        // Registrar intento fallido: usuario no existe
        bitacoraAccesoService.registrarAcceso(
            correo,
            "Usuario ingresado no existe", // Nombre exacto en la tabla tipo_acceso
            "LOGIN",
            request,
            null
        );
        return new LoginResult(false, "Usuario no encontrado", null, "USER_NOT_FOUND");
    }

    Usuario usuario = userOptional.get();

    if (!passwordService.verifyPassword(password, usuario.getPassword())) {
        // Registrar intento fallido: contraseña incorrecta / bloqueado
        bitacoraAccesoService.registrarAcceso(
            usuario.getIdUsuario(),
            "Bloqueado - Password incorrecto/Numero de intentos exedidos", // Nombre exacto
            "LOGIN",
            request,
            null
        );
        return new LoginResult(false, "Contraseña incorrecta", null, "INVALID_PASSWORD");
    }

    // Registrar login exitoso
    bitacoraAccesoService.registrarAcceso(
        usuario.getIdUsuario(),
        "Acceso Concedido", // Nombre exacto
        "LOGIN",
        request,
        null
    );

    return new LoginResult(true, "Login exitoso", usuario, "LOGIN_OK");


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

    /*Devuelve una lista de Usuarios que pertenecen a un rol específico*/
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
 