package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.Usuario;
import com.SystemAnalisys.Project.repository.UsuarioRepository;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository)
    {
        this.usuarioRepository=usuarioRepository;
    }
    public List<Usuario> getAllUsuarios()
    {
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

        public Usuario login(Usuario usuario){
            Optional<Usuario> listUsuario = usuarioRepository.findByCorreoElectronicoAndNombre(usuario.getCorreoElectronico(),usuario.getNombre());
            Usuario usuarioReturn = null;
    
            if(listUsuario.isPresent()){
                usuarioReturn = listUsuario.get();
            }
            return usuarioReturn;
        }
}
