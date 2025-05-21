package com.inclufarma.dto;

import com.inclufarma.model.Categoria;

import java.util.UUID;

public record MedicamentoDTO(String nome, String principio_ativo, UUID categoria, String descricao, Float preco, Integer estoque, String imagem) {
}
