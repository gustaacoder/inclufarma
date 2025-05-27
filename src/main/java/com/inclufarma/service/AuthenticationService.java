package com.inclufarma.service;

import com.inclufarma.dto.RegistroDTO;
import com.inclufarma.enums.UserRole;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;

    public synchronized Usuario registrarUsuario(RegistroDTO dto){
        var usuarioEmail = usuarioRepository.findByEmailIgnoreCase(dto.email());

        if(usuarioEmail.isPresent()){
            throw new RuntimeException("Usuário já cadastrado com este e-mail.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.senha());

        Usuario newUser = new Usuario();
        newUser.setNome(dto.nome());
        newUser.setSenha(encryptedPassword);
        newUser.setEmail(dto.email());
        newUser.setRole(UserRole.valueOf("USER"));

        return usuarioRepository.save(newUser);
    }

    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        String email = authentication.getName();

        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
