package com.inclufarma.service;

import com.inclufarma.dto.UsuarioDTO;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(UsuarioDTO dto){
        var user = usuarioRepository.findByEmail(dto.email());
        if(user.isPresent()){
            throw new RuntimeException("Usuário já cadastrado");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setEmail(dto.email());
        newUsuario.setNome(dto.nome());
        newUsuario.setSenha(dto.senha());

        return usuarioRepository.save(newUsuario);
    }

}
