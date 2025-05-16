package com.inclufarma.dto;

import com.inclufarma.model.Categoria;

public record MedicamentoDTO(String nome, String principio_ativo, Categoria categoria, String descricao, Float preco, Integer estoque, String imagem) {
}
