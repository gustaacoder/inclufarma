package com.inclufarma.service;

import com.inclufarma.dto.MedicamentoDTO;
import com.inclufarma.model.Categoria;
import com.inclufarma.model.Medicamento;
import com.inclufarma.repository.CategoriaRepository;
import com.inclufarma.repository.MedicamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Medicamento> findAllMedicamentos() {
        return medicamentoRepository.findAll();
    }

    @Transactional
    public Medicamento criarMedicamento(MedicamentoDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.categoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + dto.categoria()));

        validarCampos(dto);

        Medicamento medicamento = new Medicamento();
        medicamento.setNome(dto.nome());
        medicamento.setPrincipio_ativo(dto.principio_ativo());
        medicamento.setCategoria(categoria);
        medicamento.setDescricao(dto.descricao());
        medicamento.setPreco(dto.preco());
        medicamento.setEstoque(dto.estoque());
        medicamento.setImagem(dto.imagem());

        return medicamentoRepository.save(medicamento);
    }

    @Transactional
    public Medicamento atualizarMedicamento(MedicamentoDTO dto) {

        Medicamento medicamentoExistente = medicamentoRepository.findById(dto.categoria())
                .orElseThrow(() -> new EntityNotFoundException("Medicamento não encontrado com id: " + dto.categoria()));

        Categoria categoria = categoriaRepository.findById(dto.categoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + dto.categoria()));

        validarCampos(dto);

        medicamentoExistente.setNome(dto.nome());
        medicamentoExistente.setPrincipio_ativo(dto.principio_ativo());
        medicamentoExistente.setCategoria(categoria);
        medicamentoExistente.setDescricao(dto.descricao());
        medicamentoExistente.setPreco(dto.preco());
        medicamentoExistente.setEstoque(dto.estoque());
        medicamentoExistente.setImagem(dto.imagem());

        return medicamentoRepository.save(medicamentoExistente);
    }

    @Transactional
    public void deletarMedicamento(UUID id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento não encontrado com id: " + id));

        medicamentoRepository.delete(medicamento);
    }

    private void validarCampos(MedicamentoDTO dto) {

        if (dto.nome() == null || dto.nome().isEmpty()) {
            throw new IllegalArgumentException("Nome do medicamento não pode ser nulo ou vazio.");
        }
        if (dto.principio_ativo() == null || dto.principio_ativo().isEmpty()) {
            throw new IllegalArgumentException("Princípio ativo não pode ser nulo ou vazio.");
        }
        if (dto.categoria() == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula.");
        }
        if (dto.preco() == null || dto.preco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        if (dto.estoque() == null || dto.estoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
    }



}
