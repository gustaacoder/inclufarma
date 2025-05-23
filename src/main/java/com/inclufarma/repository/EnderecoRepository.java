package com.inclufarma.repository;

import com.inclufarma.model.Cidade;
import com.inclufarma.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

    @Query("""
    SELECT e FROM Endereco e
    WHERE e.numero = :numero
    AND e.cep = :cep
    AND e.logradouro = :nomeLogradouro
    AND e.cidade.nome = :nomeCidade
    """)
    Optional<Endereco> findByCampos(
            @Param("numero") String numero,
            @Param("cep") String cep,
            @Param("nomeLogradouro") String nomeLogradouro,
            @Param("cidade") Cidade nomeCidade
    );

    List<Endereco> findByUsuarioId(UUID usuarioId);
}
