package com.inclufarma.repository;

import com.inclufarma.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CidadeRepository extends JpaRepository<Cidade, UUID> {
}
