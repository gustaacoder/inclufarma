package com.inclufarma.service;

import com.inclufarma.dto.EnderecoDTO;
import com.inclufarma.model.Endereco;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.EnderecoRepository;
import com.inclufarma.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Endereco> findAllEnderecoUsuario(UUID usuarioId) {
        try{
            return enderecoRepository.findByUsuarioId(usuarioId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereços do usuário: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Endereco create(EnderecoDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        validarCamposParaCriacao(dto);

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.endereco().getLogradouro());
        endereco.setBairro(dto.endereco().getBairro());
        endereco.setNumero(dto.endereco().getNumero());
        endereco.setCidade(dto.endereco().getCidade());
        endereco.setCep(dto.endereco().getCep());
        endereco.setUsuario(usuario);

        return enderecoRepository.save(endereco);
    }

    @Transactional
    public Endereco update(EnderecoDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Endereco existente = enderecoRepository.findById(dto.endereco().getId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + dto.endereco().getId()));

        validarCamposParaAtualizacao(dto);

        existente.setLogradouro(dto.endereco().getLogradouro());
        existente.setBairro(dto.endereco().getBairro());
        existente.setNumero(dto.endereco().getNumero());
        existente.setCidade(dto.endereco().getCidade());
        existente.setCep(dto.endereco().getCep());
        existente.setUsuario(usuario);

        return enderecoRepository.save(existente);
    }

    @Transactional
    public void delete(UUID id) {
        Endereco existente = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + id));

        enderecoRepository.delete(existente);
    }

    private void validarCamposParaCriacao(EnderecoDTO dto) {

        if (dto.endereco().getCidade() == null) {
            throw new IllegalArgumentException("Cidade é obrigatória.");
        }

        if (dto.endereco().getCep() == null) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }

        if (dto.endereco().getNumero() == null || dto.endereco().getNumero().isBlank()) {
            throw new IllegalArgumentException("Número é obrigatório e não pode estar em branco.");
        }

        if (dto.endereco().getBairro() == null || dto.endereco().getBairro().isBlank()) {
            throw new IllegalArgumentException("Bairro é obrigatório e não pode estar em branco.");
        }

        if (dto.endereco().getLogradouro() == null || dto.endereco().getLogradouro().isBlank()) {
            throw new IllegalArgumentException("Logradouro é obrigatório e não pode estar em branco.");
        }
    }

    private void validarCamposParaAtualizacao(EnderecoDTO dto) {
        if (dto.endereco().getId() == null) {
            throw new IllegalArgumentException("Código do endereço é obrigatório para atualização.");
        }

        validarCamposParaCriacao(dto);
    }

}
