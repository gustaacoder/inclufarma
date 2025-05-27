package com.inclufarma.dto;

import java.util.List;

public record CriarPedidoRequestDTO(List<ItensPedidoDTO> itensDto, EnderecoPedidoDTO enderecoPedidoDTO) {
}
