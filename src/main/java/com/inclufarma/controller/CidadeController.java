package com.inclufarma.controller;


import com.inclufarma.dto.CidadeDTO;
import com.inclufarma.model.Cidade;
import com.inclufarma.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cidade")
@AllArgsConstructor

public class CidadeController {

    private final CidadeService cidadeService;

    @Operation(summary = "Criar uma cidade caso não exista")
    @PostMapping("/cadastrar")
    public ResponseEntity<Cidade> cadastrarCidade(@RequestBody CidadeDTO dto) {
        Cidade cidade = cidadeService.cadastrarCidade(dto.nome());
        return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
    }
}
