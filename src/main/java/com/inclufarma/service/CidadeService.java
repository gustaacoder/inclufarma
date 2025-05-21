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

    public Cidade buscarOuCriar(CidadeDTO dto) {

        return cidadeRepository
                .findByNomeIgnoreCase(dto.nome())
                .orElseGet(() -> {
                    Cidade nova = new Cidade();
                    nova.setNome(dto.nome());

                    return cidadeRepository.save(nova);
                });
    }
}
