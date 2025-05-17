package com.inclufarma.dto;

import com.inclufarma.model.Medicamento;
import com.inclufarma.model.Pedidos;

public record ItensPedidoDTO(Pedidos pedidos, Medicamento medicamento, Integer quantidade, Float precoUnitario) {
}
