package com.inclufarma.controller;


import com.inclufarma.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/endereco")
@AllArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Lista todos os endereços")
    @GetMapping("/listar")
}
