package com.inclufarma.dto;

import com.inclufarma.model.Cidade;

public record EnderecoDTO(String logradouro, Cidade cidade, Integer cep, String numero) {
}
