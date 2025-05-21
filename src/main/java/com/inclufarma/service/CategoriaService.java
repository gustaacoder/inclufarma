package com.inclufarma.service;

import com.inclufarma.model.Categoria;
import com.inclufarma.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

     public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }
}
