package com.inclufarma.controller;

import com.inclufarma.model.Categoria;
import com.inclufarma.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Listar todas as categorias")
    @GetMapping("/listarCategoria")
    public List<Categoria> listarTodos() {
        return categoriaService.listarCategorias();
    }
}