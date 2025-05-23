package com.inclufarma.repository;

import com.inclufarma.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    Optional<Categoria> findByNomeIgnoreCase(String nome);
}
