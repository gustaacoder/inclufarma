package com.inclufarma.config;

import com.inclufarma.model.Categoria;
import com.inclufarma.repository.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final CategoriaRepository categoriaRepository;

    @PostConstruct
    public void init(){

        List<String> categoriaMedicamentos = List.of(
                "Analgésicos",
                "Antibióticos",
                "Anti-inflamatórios",
                "Antialérgicos",
                "Ansiolíticos",
                "Antidepressivos"
        );

        for (String categoria : categoriaMedicamentos) {
            String categoriaFormatado = categoria.trim();

            boolean existe = categoriaRepository
                    .findByNomeIgnoreCase(categoriaFormatado)
                    .isPresent();

            if (!existe) {
                Categoria novaCategoria = Categoria.builder()
                        .nome(categoriaFormatado)
                        .build();

                categoriaRepository.save(novaCategoria);
            }
        }

    }
}
