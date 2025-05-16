package com.inclufarma.repository;

import com.inclufarma.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidosRepository extends JpaRepository<Pedidos, UUID> {
}
