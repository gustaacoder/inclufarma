package com.inclufarma.controller;

import com.inclufarma.dto.AutenticacaoDTO;
import com.inclufarma.dto.LoginRespostaDTO;
import com.inclufarma.dto.RegistroDTO;
import com.inclufarma.model.Usuario;
import com.inclufarma.service.AuthenticationService;
import com.inclufarma.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager; // Classe própria do Spring Security que gerencia autenticação
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @Operation(summary = "Realiza o login do usuário")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AutenticacaoDTO dto) {
        var emailPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()); // Cria um token de autenticação
        var auth = this.authenticationManager.authenticate(emailPassword); // Autentica o usuário

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginRespostaDTO(token, ((Usuario) auth.getPrincipal()).getNome()));
    }

    @Operation(summary = "Realiza o cadastro do usuário")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistroDTO dto) {
        try {
            Usuario novoUsuario = authenticationService.registrarUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
