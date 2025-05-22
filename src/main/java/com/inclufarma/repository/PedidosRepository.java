package com.inclufarma.repository;

import com.inclufarma.model.ItensPedido;
import com.inclufarma.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PedidosRepository extends JpaRepository<Pedidos, UUID> {
}
