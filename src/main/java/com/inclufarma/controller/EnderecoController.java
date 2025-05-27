package com.inclufarma.controller;

import com.inclufarma.dto.EnderecoDTO;
import com.inclufarma.model.Endereco;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.EnderecoRepository;
import com.inclufarma.service.AuthenticationService;
import com.inclufarma.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/endereco")
@AllArgsConstructor

public class EnderecoController {

    private final AuthenticationService authenticationService;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;

    @Operation(summary = "Listar endereços do usuário logado")
    @GetMapping("/listarEnderecos")
    public List<Endereco> listarEnderecosDoUsuarioLogado() {
        Usuario usuarioLogado = authenticationService.getUsuarioLogado();
        return enderecoRepository.findByUsuarioId(usuarioLogado.getId());
    }

    @Operation(summary = "Cadastrar novo endereço")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody EnderecoDTO dto) {
        try {
            Endereco novo = enderecoService.create(dto);
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Atualizar endereço")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> atualizarEndereco(@PathVariable UUID id, @RequestBody EnderecoDTO dto) {
        try {
            Endereco atualizado = enderecoService.update(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Deletar endereço")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEndereco(@PathVariable UUID id) {
        try {
            enderecoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
