package com.inclufarma.service;

import com.inclufarma.dto.CidadeDTO;
import com.inclufarma.model.Cidade;
import com.inclufarma.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public Cidade cadastrarCidade(String nome) {

        return cidadeRepository
                .findByNomeIgnoreCase(nome)
                .orElseGet(() -> {
                    Cidade nova = new Cidade();
                    nova.setNome(nome);

                    return cidadeRepository.save(nova);
                });
    }
}
