package com.inclufarma.repository;

import com.inclufarma.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CidadeRepository extends JpaRepository<Cidade, UUID> {


    Optional<Cidade> findByNomeIgnoreCase(String nome);
}
