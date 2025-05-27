package com.inclufarma.service;

import com.inclufarma.dto.EnderecoDTO;
import com.inclufarma.model.Cidade;
import com.inclufarma.model.Endereco;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.CidadeRepository;
import com.inclufarma.repository.EnderecoRepository;
import com.inclufarma.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CidadeRepository cidadeRepository;

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
        usuarioRepository.flush(); // Garante que operações pendentes sejam sincronizadas
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<Cidade> cidade = cidadeRepository.findByNomeIgnoreCase(dto.cidade());
        if (cidade.isEmpty()) {
            throw new IllegalArgumentException("Cidade não encontrada.");
        }


        validarCampos(dto);

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setBairro(dto.bairro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidade.get());
        endereco.setCep(dto.cep());
        endereco.setUsuario(usuario);

        return enderecoRepository.save(endereco);
    }

    @Transactional
    public Endereco update(UUID id, EnderecoDTO dto) {
        Endereco existente = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + id));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<Cidade> cidade = cidadeRepository.findByNomeIgnoreCase(dto.cidade());
        if (cidade.isEmpty()) {
            throw new IllegalArgumentException("Cidade não encontrada.");
        }

        validarCampos(dto);

        existente.setLogradouro(dto.logradouro());
        existente.setBairro(dto.bairro());
        existente.setNumero(dto.numero());
        existente.setCep(dto.cep());
        existente.setCidade(cidade.get());
        existente.setUsuario(usuario);

        return enderecoRepository.save(existente);
    }

    @Transactional
    public void delete(UUID id) {
        Endereco existente = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + id));

        enderecoRepository.delete(existente);
    }

    private void validarCampos(EnderecoDTO dto) {

        if (dto.cep() == null) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }

        if (dto.numero() == null || dto.numero().isBlank()) {
            throw new IllegalArgumentException("Número é obrigatório e não pode estar em branco.");
        }

        if (dto.bairro() == null || dto.bairro().isBlank()) {
            throw new IllegalArgumentException("Bairro é obrigatório e não pode estar em branco.");
        }

        if (dto.logradouro() == null || dto.logradouro().isBlank()) {
            throw new IllegalArgumentException("Logradouro é obrigatório e não pode estar em branco.");
        }
    }


}
