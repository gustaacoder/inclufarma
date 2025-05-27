package com.inclufarma.controller;

import com.inclufarma.dto.CategoriaDTO;
import com.inclufarma.model.Categoria;
import com.inclufarma.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor

public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Listar todas as categorias")
    @GetMapping("/listarCategoria")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }
}