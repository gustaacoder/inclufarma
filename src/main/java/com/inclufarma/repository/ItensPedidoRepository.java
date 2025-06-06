package com.inclufarma.repository;

import com.inclufarma.model.ItensPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItensPedidoRepository extends JpaRepository<ItensPedido, UUID> {
    List<ItensPedido> findByPedidosUsuarioId(UUID usuarioId);
}
