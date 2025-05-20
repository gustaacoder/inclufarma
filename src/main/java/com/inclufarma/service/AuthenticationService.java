package com.inclufarma.service;

import com.inclufarma.dto.RegistroDTO;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;

    public synchronized Usuario registrarUsuario(RegistroDTO dto){
        var usuario = usuarioRepository.findByEmail(dto.email());

        if(usuario != null){
            throw new RuntimeException("Usuário já cadastrado");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.senha());

        Usuario newUser = new Usuario();
        newUser.setNome(dto.nome());
        newUser.setSenha(encryptedPassword);
        newUser.setEmail(dto.email());
        newUser.setRole(dto.role());

        return usuarioRepository.save(newUser);
    }
}
