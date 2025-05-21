package com.inclufarma.service;

import com.inclufarma.dto.CategoriaDTO;
import com.inclufarma.model.Categoria;
import com.inclufarma.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

     public List<CategoriaDTO> listarCategorias() {
         return categoriaRepository.findAll()
                 .stream()
                 .map(c -> new CategoriaDTO(c.getId(), c.getNome()))
                 .toList();
    }
}
