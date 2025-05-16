package com.inclufarma.dto;

import com.inclufarma.model.Usuario;

import java.time.LocalDateTime;

public record PedidosDTO(Usuario usuario, LocalDateTime data) {
}
