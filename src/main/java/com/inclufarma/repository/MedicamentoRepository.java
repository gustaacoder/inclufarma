package com.inclufarma.repository;

import com.inclufarma.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicamentoRepository extends JpaRepository<Medicamento, UUID> {
}
