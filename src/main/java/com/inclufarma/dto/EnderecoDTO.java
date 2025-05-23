package com.inclufarma.dto;

import com.inclufarma.model.Endereco;

public record EnderecoDTO(String logradouro, String cidade, Integer cep, String numero, String bairro) {
}
